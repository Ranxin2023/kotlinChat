package com.example.socketdemo

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SendMessageHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val messageText : TextView =itemView.findViewById(R.id.text_message)
    private val dateText: TextView =itemView.findViewById(R.id.send_date)
    private val timeText: TextView =itemView.findViewById(R.id.time_stamp)
    private val utils:Utils=Utils()

    fun bind(message:Message) {
        messageText.text=message.message
        // Format the stored timestamp into a readable String using method.
        dateText.text=this.utils.formatDate(message.createdAt, "America/Los_Angeles")
        timeText.text=this.utils.formatTime(message.createdAt, "America/Los_Angeles")
    }
}