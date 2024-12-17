package com.example.socketdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class AddFriend:AppCompatActivity(){
    private lateinit var mSocket:SocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invite_friends)
        this.mSocket= SocketClient()
        this.addFriends()
        this.exit()
    }

    private fun addFriends(){
        this.mSocket.connectIfNeeded()
        val addFriendButton=findViewById<Button>(R.id.add_friends_button)

        addFriendButton.setOnClickListener {
            val friendNameEditText=findViewById<EditText>(R.id.friend_list)
            val friendName=friendNameEditText.text.toString()
            val friendInfoPackage=JSONObject()
            friendInfoPackage.put("username", Profile.username)
            friendInfoPackage.put("friend name", friendName)
            sendRoomInfo(friendInfoPackage)
        }
    }

    private fun sendRoomInfo(friendInfo: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            if (mSocket.getSocket().connected()) {
                mSocket.getSocket().emit("add friend", friendInfo)
            }
        }
    }

    private fun exit(){
        val exitButton=findViewById<Button>(R.id.back_to_room_button)
        exitButton.setOnClickListener {
            val intent=Intent(this, ChatRoomActivity::class.java)
            startActivity(intent)
        }
    }
}