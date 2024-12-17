# Kotlin Chat
## Introduction
KotlinChat is a real-time chat application with client-server architecture built in Kotlin and Python. It allows multiple users to register, join rooms, and send messages in real time using socket programming.
## Overview
This project implements a complete chat room system, with:
- **Client-Side**: Written in Kotlin to handle user registration, message sending, and communication with the server.
- **Server-Side**: Written in Python to manage user sessions, chat rooms, and message broadcasting.

## Features
1. User Registration and Authentication:

- Users can register and log in securely.
- User credentials are stored and managed in a MySQL database.

2. Real-Time Communication:

- Supports real-time messaging using socket programming between clients and the server.
- Messages are broadcasted to all users in the active chat room.

3. Chat Rooms:

- Users can create, join, and participate in multiple chat rooms.
- Room details and user associations are persistently stored in the MySQL database.

4. Persistent Storage:

- The server connects to a MySQL database to store:
- User Data: Usernames, passwords, and profiles.
Chat Messages: Messages sent in rooms, including timestamps and sender details.
Chat Rooms: Information about room creation, room members, and activity.

5. Session Management:

- Active user sessions are tracked and validated by the server using session data stored in MySQL.

6. Private Messaging:

- Users can send private messages in designated private rooms.
- Messages are securely stored and retrieved for private conversations.

7. Error Handling and Reliability:

- Robust error handling for database operations and socket communication.
- Logs and retries ensure smooth operation during connection failures.

## Prerequisites
- Kotlin Development Environment (e.g., IntelliJ IDEA).
- Python 3.x installed.
- MySQL Server and MySQL Workbench installed.

## Steps to Run the Project
- **Server side:**
1. Set up the MySQL database:
Use MySQL Workbench to create the required tables (Users, ChatRooms, Messages, Sessions).
2. Update the database configuration in `mysocketserver.py`.
3. Run the main server file:
```sh
python mysocketserver.py

```
- **client side:**
1. Navigate to the `clientpart/` directory.

2. Use Gradle to build and run the client:
```sh
./gradlew build
./gradlew run

```
3. Connect to the server and start chatting.
## Project Structure
- client part:
```sh
clientpart/
│
├── app/                                # Main client application files
│   └── src/  
│       └── main/  
│           └── java/  
│               └── com/example/socketdemo/  
│                   ├── AddFriend.kt                    # Handles adding friends to the chat
│                   ├── ChatApplication.kt              # Application entry point
│                   ├── ChatRoomActivity.kt             # Activity for chat rooms
│                   ├── ChatRoomListAdapter.kt          # Adapter for chat room lists
│                   ├── ChatRoomViewHolder.kt           # View holder for chat room items
│                   ├── MainActivity.kt                 # Main activity for user interactions
│                   ├── MessageActivity.kt              # Activity for messaging functionality
│                   ├── MessageListAdapter.kt           # Adapter for displaying messages
│                   ├── Model.kt                        # Data models for chat messages
│                   ├── PrivateRoomActivity.kt          # Activity for private chat rooms
│                   ├── PrivateRoomMessageListAdapter.kt # Adapter for private room messages
│                   ├── Profile.kt                      # Handles user profile management
│                   ├── ReceiverMessageHolder.kt        # View holder for received messages
│                   ├── SendMessageHolder.kt            # View holder for sent messages
│                   ├── SocketClient.kt                 # Socket client for server communication
│                   └── Utils.kt                        # Utility functions for the client
│
├── gradle/                             # Gradle wrapper for build management
│
├── build.gradle                        # Gradle build configuration
├── gradlew                             # Gradle wrapper script
├── gradlew.bat                         # Gradle script for Windows
└── settings.gradle                     # Gradle project settings

```

- server part:
```sh
serverpart/
│
├── chatroom.py                         # Handles chat room creation and message flow
├── chatroomdatabase.py                 # Simulated database for chat room storage
├── handlerrequest.py                   # Processes incoming client requests
├── messageroom.py                      # Manages message broadcasting
├── messageroomdatabase.py              # Simulated message storage
├── mysocketserver.py                   # Main server script handling socket communication
├── session.py                          # Manages active user sessions
├── sessiondatabase.py                  # Simulated session data storage
├── userdatabase.py                     # Stores user credentials and information
├── userroom.py                         # Manages user actions in chat rooms
├── userroomdatabase.py                 # Simulated database for user-room mappings
└── users.py                            # Processes and verifies user credentials

```