package com.example.socketdemo

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException
import io.socket.emitter.Emitter;
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

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

class MainActivity : AppCompatActivity() {
    private lateinit var socketClient: SocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val serverHost = "http://192.168.108.1:5000"
        socketClient = SocketClient(serverHost).apply { start() }

        val buttonSend = findViewById<Button>(R.id.send_button)
        val editTextMessage = findViewById<EditText>(R.id.text_send)

        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            socketClient.sendMessage(message)
            val connectInfo=findViewById<TextView>(R.id.connectInfo)
            runOnUiThread{
                connectInfo.text="send successfully"
            }
        }
    }
}