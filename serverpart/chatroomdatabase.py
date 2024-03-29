import pymysql

from userroom import UserRoom


class ChatRoomDatabase:
    def __init__(self, host=None, user=None, password=None) -> None:
        self.host = host or "localhost"
        self.user = user
        self.password = password

        self._conn = None
        self.cur_db_name = "chat_db"
        self.table_name = "chat_room"
        self._cursor = None
        self.connect_db()
        self.use_ssl = False
        self.ssl = {}

    def register_room(self, roomname: str, owner: str):
        cmd = "INSERT INTO {} chat_room_name, owner VALUES(%s, %s)".format(
            self.table_name
        )
        try:
            self._cursor.execute(cmd, (roomname, owner))
            self._conn.commit()
            return True, None
        except pymysql.Error as e:
            print("Error sending order to the database:", e)
            self._conn.rollback()
            return False, str(e)
