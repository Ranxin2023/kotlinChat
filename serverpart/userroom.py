from userroomdatabase import UserRoomDatabase


class UserRoom:
    def __init__(self) -> None:
        self.localhost = "127.0.0.1"
        self.db_username = "root"
        self.db_password = "R@ndyli94041424"
        self.db = UserRoomDatabase(self.localhost, self.db_username, self.db_password)

    def enter_user(self, username: str):
        self.dp.enter_room(username=username)
