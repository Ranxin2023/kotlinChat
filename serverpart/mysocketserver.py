import asyncio

import socketio
from aiohttp import web

sio = socketio.AsyncServer(async_mode="aiohttp")
app = web.Application()
sio.attach(app)


@sio.event
async def connect(sid, environ):
    print("connection established")


@sio.on("send message")
async def my_message(sid, data):
    print(f"message received with {data}")
    await sio.emit("my response", "I received your message, that is {}.".format(data))


@sio.event
async def disconnect(sid):
    print("disconnected from server")


if __name__ == "__main__":
    web.run_app(app, host="192.168.108.1", port=5000)
