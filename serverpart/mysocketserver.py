import asyncio

import socketio
from aiohttp import web

from session import Session

sio = socketio.AsyncServer(async_mode="aiohttp")
app = web.Application()
sio.attach(app)


@sio.event
async def connect(sid, environ):
    print("connection established")


@sio.on("send message")
async def my_message(socket_id, data):
    sid = data["sid"]
    print(f"message received with socketid:{socket_id}, sid: {sid}")
    # get the sid from the database
    # ---session code start
    if sid is None:
        await sio.emit("error sending", "doesn't build session")
        # return
    session = Session(sid=sid)
    success, sesssion_msg = session.find_session()

    if not success:
        print("error messsage", sesssion_msg)
        await sio.emit("error sending", sesssion_msg)
    else:
        # ---session code end
        print("successfully handle the socket server")
        await sio.emit("my response", data)


@sio.on("add friend")
async def add_friend(socket_id, data):
    friend_name = data["friend name"]


@sio.on("enter room")
async def chat_room(socket_id, data):
    sid = data["sid"]


@sio.event
async def disconnect(sid):
    print("disconnected from server")


if __name__ == "__main__":
    web.run_app(app, host="192.168.108.1", port=5000)
