import secrets

from sessiondatabase import SessionDatabase


class Session:
    def __init__(self, sid) -> None:
        self.localhost = "127.0.0.1"
        self.db_username = "root"
        self.db_password = "R@ndyli94041424"
        self.db = SessionDatabase(self.localhost, self.db_username, self.db_password)
        if not sid or len(sid) == 0:
            self.sid = secrets.token_hex(32)
        else:
            self.sid = sid

    def store_session_info(self, args):
        return self.db.store_session_info([self.sid] + args)

    def find_session(self):
        success, status = self.db.find_session_status(self.sid)
        if success:
            # nickname = data[0]
            # status = data[0]
            if status == "login":
                return True, None
            else:
                return False, "user is not online"
        return False, status
