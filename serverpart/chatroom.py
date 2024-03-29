from chatroomdatabase import ChatRoomDatabase
from userroomdatabase import UserRoomDatabase


class ChatRoom:
    def __init__(self) -> None:
        self.localhost = "127.0.0.1"
        self.db_username = "root"
        self.db_password = "R@ndyli94041424"
        self.db = ChatRoomDatabase(self.localhost, self.db_username, self.db_password)

    def register_room(self, tables):
        self.db.register_room()
        if len(tables) != 1 and type(tables[0]) != UserRoomDatabase:
            return False, "illegal arguments"
        userroomdb = tables[0]
        userroomdb.enter_room()
