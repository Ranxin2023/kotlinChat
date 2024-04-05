import pymysql


class UserDatabase:
    def __init__(self, host=None, username_db=None, password=None) -> None:
        self.host = host or "localhost"
        self.username_db = username_db
        self.password = password

        self._conn = None
        self.cur_db_name = "chat_db"
        self.table_name = "users"
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

    def find_user(self, username: str):
        cmd = "SELECT * FROM {} WHERE username=%s".format(self.table_name)
        try:
            self._cursor.execute(cmd, (username))
            output = self._cursor.fetchone()
            if not output:
                return True, "User does not exist"
            return True, None
        except pymysql.Error as e:
            print("Error sending order to the database in find_user:", e)
            self._conn.rollback()
            return False, str(e)

    def register_user(self, username: str, nickname: str, photo_id: str):
        cmd = "INSERT INTO  {} \
            (username, nickname, profile_photo) \
                VALUES( %s, %s, %s)".format(
            self.table_name
        )
        try:
            self._cursor.execute(
                cmd,
                (username, nickname, photo_id),
            )
            self._conn.commit()
            return True, None
        except pymysql.Error as e:
            print("Error sending order to the database:", e)
            self._conn.rollback()
            return False, str(e)

    def find_id(self, username):
        cmd = "SELECT userid FROM {} WHERE username=%s".format(self.table_name)
        try:
            self._cursor.execute(cmd, (username))
            output = self._cursor.fetchone()
            if not output:
                return False, "User does not exist"
            else:
                return True, output[0]
        except pymysql.Error as e:
            print("Error sending order to the database in find_id:", e)
            self._conn.rollback()
            return False, str(e)
