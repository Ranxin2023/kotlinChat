from enum import Enum

import pymysql


class SessionColumn(Enum):
    sid = 0
    nickname = 1
    login_time = 2
    logout_time = 3
    status = 4


class SessionDatabase:
    def __init__(self, host=None, user=None, password=None) -> None:
        self.host = host or "localhost"
        self.user = user
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
            user=self.user,
            password=self.password,
            db=self.cur_db_name,
        )

        self._cursor = self._conn.cursor()

    def store_session_info(self, args):
        sid = str(args[0])
        username = str(args[1])
        login_time = str(args[2])
        photo_encode = str(args[3])
        cmd = "INSERT INTO  {} \
            (sid, nickname, login_time, status, profile_photo) \
                VALUES(%s, %s, %s, %s, %s)".format(
            self.table_name
        )
        try:
            self._cursor.execute(
                cmd,
                (sid, username, login_time, "login", photo_encode),
            )
            self._conn.commit()
            return True, None
        except pymysql.Error as e:
            print("Error sending order to the database:", e)
            self._conn.rollback()
            return False, str(e)

    def logout_session_info(self, sid):
        pass

    def find_session_status(self, sid):
        cmd = "SELECT status, profile_photo FROM {} WHERE sid = %s".format(
            self.table_name
        )
        try:
            self._cursor.execute(cmd, (sid))
            output = self._cursor.fetchone()
            if not output:
                return False, "User does not exist"
            # print(output[0])
            if output[0] == "login":
                # print(output[0])
                return True, output[1]
            return False, "User does not online"

        except pymysql.Error as e:
            print("Error sending order to the database:", e)
            self._conn.rollback()
            return False, str(e)
