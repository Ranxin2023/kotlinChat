from chatroomdatabase import ChatRoomDatabase
from userdatabase import UserDatabase
from userfrienddatabase import UserFriendDatabase
from userroomdatabase import UserRoomDatabase


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
        ufdb = UserFriendDatabase(self.localhost, self.db_username, self.db_password)
        if ufdb.whether_friend(username=self.username, friend_name=friend_name):
            return None
        ufdb.add_friend(self.username, friend_name)

    def create_room(self, friend_name: str):
        crdb = ChatRoomDatabase(self.localhost, self.db_username, self.db_password)
        chat_room_name = self.username + "&" + friend_name
        crdb.register_two_people_room(roomname=chat_room_name, username=self.username)
        urdb = UserRoomDatabase(self.localhost, self.db_username, self.db_password)
        urdb.register_user_room(username=self.username, roomname=chat_room_name)
        urdb.register_user_room(username=friend_name, roomname=chat_room_name)
