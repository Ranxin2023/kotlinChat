package com.example.socketdemo

import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URISyntaxException

class SocketClient(private val host: String) {
    private lateinit var mSocket: Socket

    init {
        try {
            mSocket = IO.socket(host)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            connectSocket()
            setupListeners()
        }
    }

    private suspend fun connectSocket() {
        withContext(Dispatchers.IO) {
            mSocket.connect()
        }
    }

    private fun setupListeners() {
        mSocket.on(Socket.EVENT_CONNECT) {
            println("Connected to the server")
        }.on("my response") { args ->
            println("Received a response from the server: ${args[0]}")
        }.on(Socket.EVENT_DISCONNECT) {
            println("Disconnected from the server")
        }
    }

    fun sendMessage(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (mSocket.connected()) {
                mSocket.emit("send message", message)
            }
        }
    }
}