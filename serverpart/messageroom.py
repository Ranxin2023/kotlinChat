from messageroomdatabase import MessageRoomDatabase
from users import User


class MessageRoom:
    def __init__(self) -> None:
        self.localhost = "127.0.0.1"
        self.db_username = "root"
        self.db_password = "R@ndyli94041424"
        self.db = MessageRoomDatabase(
            self.localhost, self.db_username, self.db_password
        )

    def upload_message(self, msg: str, user: User):
        u_id = user.userid
        u_name = user.username
        success, e_msg = self.db.upload_message(u_id, u_name, msg)
        if not success:
            return False, e_msg
        return True, None
