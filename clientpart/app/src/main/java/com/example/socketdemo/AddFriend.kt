package com.example.socketdemo

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
    }
    private fun addFriends(){
        val addFriendButton=findViewById<Button>(R.id.add_friends_button)
        val friendName=findViewById<EditText>(R.id.friend_list).text.toString()

        addFriendButton.setOnClickListener {
            val friendInfoPackage=JSONObject()
            friendInfoPackage.put("adder", Profile.userid)
            friendInfoPackage.put("friend list", friendName)
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
}