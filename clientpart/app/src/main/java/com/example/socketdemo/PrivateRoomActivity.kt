package com.example.socketdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class PrivateRoomActivity:AppCompatActivity() {
    private lateinit var mSocket: SocketClient
    private lateinit var messageList:ArrayList<Message>
    private lateinit var mMessageRecycler: RecyclerView
    private lateinit var mMessageAdapter:MessageListAdapter
    private lateinit var messageSent: EditText
    private fun sendMessage(message: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            if (mSocket.getSocket().connected()) {
                mSocket.getSocket().emit("send message", message)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        this.mSocket= SocketClient()
        this.mSocket.getSocket().on("my response"){
                args ->
            runOnUiThread{
//                Toast.makeText(applicationContext, "Received a response from the server: ${args[0]}", Toast.LENGTH_SHORT)
                val jsonObjectFromServer = args[0] as JSONObject
                val message=jsonObjectFromServer.getString("message").toString()
                val date=jsonObjectFromServer.getString("date").toLong()
                val sid=jsonObjectFromServer.get("sid").toString()
                val nickname=jsonObjectFromServer.getString("nickname").toString()
                val photoCode=jsonObjectFromServer.get("photo code").toString()
                messageList.add(Message(message, date, User(sid, nickname, photoCode)));
                mMessageAdapter.notifyDataSetChanged()
                messageSent.text.clear()
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.private_room)
        mSocket.connectIfNeeded()
        val sendButton=findViewById<Button>(R.id.private_room_send_button)
        this.messageSent=findViewById<EditText>(R.id.private_room_text_message_sent)
        this.mMessageRecycler=findViewById(R.id.private_room_recyclerview)
//        How to get the messageList from the server, we need to use the respond message
        this.messageList= arrayListOf<Message>()
        this.mMessageAdapter=MessageListAdapter(this, messageList)
        this.mMessageRecycler.layoutManager= LinearLayoutManager(this)
        this.mMessageRecycler.adapter=this.mMessageAdapter
        sendButton.setOnClickListener {
            val message=messageSent.text
            val date= System.currentTimeMillis()//use system function to get the current time
            val name= Profile.nickname//use profile to get the nickname of the sender
            val messagePackage = JSONObject()
            messagePackage.put("sid", Profile.sid)
            messagePackage.put("message", message)
            messagePackage.put("date", date)
            messagePackage.put("nickname", name)
            this.sendMessage(messagePackage)
        }
        val exitButton=findViewById<Button>(R.id.private_room_exit_button)
        exitButton.setOnClickListener {
            val intent= Intent(this, MessageActivity::class.java)
            startActivity(intent)
        }
    }
}