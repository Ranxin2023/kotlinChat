class User:
    def __init__(self, userid: int, username: str) -> None:
        self.userid = userid
        self.username = username
        self.status = "online"
        self.friend_list = []

    def logout(self):
        self.status = "offline"
        # update database to make it offline

    def add_freind(self, friend_name: str):
        pass
