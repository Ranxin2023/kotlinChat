import pymysql

# from users import User


class UserRoomDatabase:
    def __init__(self, host=None, user=None, password=None) -> None:
        self.host = host or "localhost"
        self.username_db = user
        self.password = password
        self._conn = None
        self.cur_db_name = "chat_db"
        self.table_name = "users_room"
        self._cursor = None
        self.connect_db()
        self.use_ssl = False
        self.ssl = {}

    def connect_db(self):
        self._conn = pymysql.connect(
            host=self.host,
            user=self.username_db,
            password=self.password,
            db=self.cur_db_name,
        )

        self._cursor = self._conn.cursor()

    def register_user_room(self, username: str, roomname: str):

        cmd = "INSERT INTO {} (username, roomname) VALUES(%s, %s)".format(
            self.table_name
        )
        try:
            self._cursor.execute(cmd, (username, roomname))
            self._conn.commit()
            return True, None
        except pymysql.Error as e:
            print("Error sending order to the database:", e)
            self._conn.rollback()
            return False, str(e)
