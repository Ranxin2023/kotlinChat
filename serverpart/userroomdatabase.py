import pymysql


class UserRoomDatabase:
    def __init__(self, host=None, user=None, password=None) -> None:
        self.host = host or "localhost"
        self.user = user
        self.password = password

        self._conn = None
        self.cur_db_name = "chat_db"
        self.table_name = "message_room"
        self._cursor = None
        self.connect_db()
        self.use_ssl = False
        self.ssl = {}

    def enter_room(self, username: str, userid):
        userid_str = str(userid)
        cmd = "INSERT INTO {} VALUES()".format(self.table_name)
        try:
            self._cursor.execute(cmd, (username, userid_str))
        except pymysql.Error as e:
            print("Error sending order to the database:", e)
            self._conn.rollback()
            return False, str(e)
