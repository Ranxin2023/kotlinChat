package com.example.socketdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URISyntaxException

class MessageActivity:AppCompatActivity() {
    private lateinit var mMessageRecycler:RecyclerView
    private lateinit var mMessageAdapter:MessageListAdapter
//    private lateinit var serverHost:String
    private lateinit var mSocket: SocketClient
    private lateinit var messageList:ArrayList<Message>
    private lateinit var messageSent:EditText
    //    socket functions

    private fun sendMessage(message: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            if (mSocket.getSocket().connected()) {
                mSocket.getSocket().emit("send message", message)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_activity)
        this.mSocket= SocketClient()
//        this.serverHost = "http://192.168.108.1:5000"

        this.mSocket.connectIfNeeded()
        this.mSocket.getSocket().on("my response"){
                args ->
            runOnUiThread{
//                Toast.makeText(applicationContext, "Received a response from the server: ${args[0]}", Toast.LENGTH_SHORT)
                val jsonObjectFromServer = args[0] as JSONObject
                val message=jsonObjectFromServer.getString("message").toString()
                val date=jsonObjectFromServer.getString("date").toLong()
                val sid=jsonObjectFromServer.get("sid").toString()
                val photoCode=jsonObjectFromServer.get("photo encode").toString()
                val nickname=jsonObjectFromServer.getString("nickname").toString()
                messageList.add(Message(message, date, User(sid, nickname, photoCode)));
                mMessageAdapter.notifyDataSetChanged()
                messageSent.text.clear()
            }
        }
        this.mMessageRecycler=findViewById(R.id.message_recyclerview)
//        How to get the messageList from the server, we need to use the respond message
        this.messageList= arrayListOf<Message>()
        this.mMessageAdapter=MessageListAdapter(this, messageList)
        this.mMessageRecycler.layoutManager=LinearLayoutManager(this)
        this.mMessageRecycler.adapter=this.mMessageAdapter
        val sendButton=findViewById<Button>(R.id.send_button)
        this.messageSent=findViewById(R.id.text_message_sent)
        sendButton.setOnClickListener {
//            get the send time
//            get the sender name
//            get the message
            val message=messageSent.text
            val date= System.currentTimeMillis()//use system function to get the current time
            val name= Profile.nickname//use profile to get the nickname of the sender
            val messagePackage = JSONObject()
            messagePackage.put("sid", Profile.sid)
            messagePackage.put("message", message)
            messagePackage.put("date", date)
            messagePackage.put("nickname", name)
            messagePackage.put("photo encode", Profile.photoId)
//            append the json from server and store in message list
            this.sendMessage(messagePackage)
        }
        val exitButton=findViewById<Button>(R.id.exit_button)
        exitButton.setOnClickListener{
            this.mSocket.disconnectSocket()
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val enterRoomButton=findViewById<Button>(R.id.enter_room_button)
        enterRoomButton.setOnClickListener {
            val intent=Intent(this, PrivateRoomActivity::class.java)
            startActivity(intent)
        }
    }
}