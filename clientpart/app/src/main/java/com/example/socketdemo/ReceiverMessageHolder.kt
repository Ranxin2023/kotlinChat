package com.example.socketdemo

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReceiverMessageHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val messageText: TextView =itemView.findViewById(R.id.receiver_message)
    private val dateText: TextView =itemView.findViewById(R.id.text_date_receiver)
    private val timeStamp: TextView =itemView.findViewById(R.id.time_stamp_receiver)
    private val nameText: TextView =itemView.findViewById(R.id.receiver_name)
    private val utils:Utils=Utils()
    fun bind(message: Message){
        messageText.text=message.message
        dateText.text=this.utils.formatDate(message.createdAt, "America/Los_Angeles")
        timeStamp.text=this.utils.formatTime(message.createdAt,"America/Los_Angeles")
        nameText.text=message.sender.nickname
    }
}