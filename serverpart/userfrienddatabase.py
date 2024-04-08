import pymysql


class UserFriendDatabase:
    def __init__(self, host=None, username_db=None, password=None) -> None:
        self.host = host or "localhost"
        self.username_db = username_db
        self.password = password

        self._conn = None
        self.cur_db_name = "chat_db"
        self.table_name = "user_friend"
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

    def add_friend(self, username, friend_name):
        cmd = "INSERT INTO {}(username, friend_name)VALUES(%s, %s)".format(
            self.table_name
        )
        try:
            self._cursor.execute(
                cmd,
                (username, friend_name),
            )
            self._conn.commit()
            return True, None
        except pymysql.Error as e:
            print("Error sending order to the database in add friend:", e)
            self._conn.rollback()
            return False, str(e)

    def whether_friend(self, username, friend_name):
        cmd = "SELECT friend_name FROM {} WHERE username=%s".format(self.table_name)
        try:
            self._cursor.execute(
                cmd,
                (username),
            )
            output = self._cursor.fetchall()
            for friend in output:
                if friend == friend_name:
                    return True, None
            return False, "Cannot find the friend"
        except pymysql.Error as e:
            print("Error sending order to the database in whether friend:", e)
            self._conn.rollback()
            return False, str(e)
