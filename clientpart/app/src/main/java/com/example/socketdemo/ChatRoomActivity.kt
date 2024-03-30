package com.example.socketdemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class ChatRoomActivity:AppCompatActivity() {
    private lateinit var mChatRoomRecycler: RecyclerView
    private lateinit var mChatRoomAdapter: ChatRoomListAdapter
    private lateinit var mChatRoomList:ArrayList<ChatRoom>
    private lateinit var mSocket:SocketClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_room)
        this.mChatRoomRecycler=findViewById(R.id.chat_room_recycler_view)
        this.mChatRoomList= arrayListOf()
        this.mChatRoomAdapter= ChatRoomListAdapter(this, this.mChatRoomList)
        this.mSocket= SocketClient()
        registerRoom()
        enterRoom()
    }
    private fun registerRoom(){

    }
    private fun enterRoom(){
        val enterButton=findViewById<Button>(R.id.enter_room_button)
        enterButton.setOnClickListener {
//            call enter room by socket
            val roomInfo=JSONObject()
            val roomName=findViewById<TextView>(R.id.room_name)
            roomInfo.put("room name", roomName)
            this.mSocket.getSocket().emit("enter room", roomInfo)
        }
    }

}