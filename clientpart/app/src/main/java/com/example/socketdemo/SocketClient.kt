package com.example.socketdemo

import android.widget.Button
import android.widget.EditText
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URISyntaxException


class SocketClient() {
    private lateinit var mSocket: Socket

    init {
        try {
            mSocket = IO.socket(Profile.socketBaseUrl)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }
    fun connectIfNeeded(){
        if(!this.mSocket.connected()){
            start()
        }
    }
    private fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            connectSocket()
        }
    }

    private suspend fun connectSocket() {
        withContext(Dispatchers.IO) {
            mSocket.connect()
        }
    }

    fun disconnectSocket(){
        mSocket.disconnect()
    }

    fun getSocket():Socket{
        return this.mSocket
    }

}