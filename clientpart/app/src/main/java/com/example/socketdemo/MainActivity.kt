package com.example.socketdemo

import android.content.Intent
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
        val startButton=findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{
            val intent= Intent(this, MessageActivity::class.java)
            startActivity(intent)
        }
    }
}