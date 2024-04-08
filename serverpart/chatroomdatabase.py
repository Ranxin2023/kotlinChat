import pymysql

from userroom import UserRoom


class ChatRoomDatabase:
    def __init__(self, host=None, user=None, password=None) -> None:
        self.host = host or "localhost"
        self.username_db = user
        self.password = password

        self._conn = None
        self.cur_db_name = "chat_db"
        self.table_name = "chat_room"
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

    def register_two_people_room(self, roomname: str, username: str):
        cmd = "INSERT INTO {} (roomname, owner) VALUES(%s, %s)".format(self.table_name)
        try:
            self._cursor.execute(cmd, (roomname, username))
            self._conn.commit()
            return True, None
        except pymysql.Error as e:
            print("Error sending order to the database in register two people room:", e)
            self._conn.rollback()
            return False, str(e)

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
