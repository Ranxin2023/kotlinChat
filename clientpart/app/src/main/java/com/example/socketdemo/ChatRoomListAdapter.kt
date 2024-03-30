package com.example.socketdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatRoomListAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val context:Context
    private val roomList:ArrayList<ChatRoom>
    constructor(context: Context, roomList:ArrayList<ChatRoom>){
        this.context=context
        this.roomList=roomList
    }

    override fun getItemCount(): Int {
        return this.roomList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatRoomInfo=this.roomList[position]
        (holder as ChatRoomViewHolder).bind(chatRoomInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.chat_room_item, parent, false)
        return ChatRoomViewHolder(view!!)
    }
}