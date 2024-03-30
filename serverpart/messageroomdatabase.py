import pymysql


class MessageRoomDatabase:
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

    def upload_message(self, sender_id: str, sender_name: str, msg: str):
        cmd = "INSERT INTO {} message, sender VALUES (%s, %s, %s)".format(
            self.table_name
        )
        try:
            self._cursor.execute(cmd, (sender_id, sender_name, msg))
            self._conn.commit()
            return True, None
        except pymysql.Error as e:
            print("Error sending order to the database:", e)
            self._conn.rollback()
            return False, str(e)
