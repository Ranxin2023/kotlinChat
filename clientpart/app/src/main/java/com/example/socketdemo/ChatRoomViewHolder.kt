package com.example.socketdemo

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatRoomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val chatRoomName:TextView=itemView.findViewById<TextView>(R.id.room_name)
    fun bind(room:ChatRoom){
        chatRoomName.text=room.roomName
    }

}