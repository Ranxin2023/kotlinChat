from userdatabase import UserDatabase


class User:
    def __init__(self, username: str) -> None:
        self.userid = None
        self.username = username
        self.friend_list = []
        self.localhost = "127.0.0.1"
        self.db_username = "root"
        self.db_password = "R@ndyli94041424"
        self.db = UserDatabase(self.localhost, self.db_username, self.db_password)

    def find_user(self, username: str):
        return self.db.find_user(username=username)

    def register_user(self, username: str, nickname: str, photoid: str):
        self.db.register_user(username, nickname, photoid)
        self.userid = self.db.find_id(username=username)

    def logout(self):
        self.status = "offline"
        # update database to make it offline

    def add_freind(self, friend_name: str):
        pass
