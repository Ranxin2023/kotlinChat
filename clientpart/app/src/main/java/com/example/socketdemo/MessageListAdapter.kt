package com.example.socketdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageListAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder> {

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }
    private val mContext:Context
    private val mMessageList:ArrayList<Message>

    constructor(context: Context, messageList: ArrayList<Message>){
        this.mContext=context
        this.mMessageList=messageList

    }

    override fun getItemCount(): Int {
        return this.mMessageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val message=mMessageList[position]
        if(message.sender.sid== Profile.sid){
            return VIEW_TYPE_MESSAGE_SENT
        }
        return VIEW_TYPE_MESSAGE_RECEIVED

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message=this.mMessageList[position]
        when(holder.itemViewType){
            VIEW_TYPE_MESSAGE_SENT->(holder as SendMessageHolder).bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED->(holder as ReceiverMessageHolder).bind(message)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view=when(viewType){
            VIEW_TYPE_MESSAGE_RECEIVED->LayoutInflater.from(parent.context).inflate(R.layout.message_receiver_item, parent, false)
            VIEW_TYPE_MESSAGE_SENT->LayoutInflater.from(parent.context).inflate(R.layout.message_sender_item, parent, false)
            else ->null
        }
        return when(viewType){
            VIEW_TYPE_MESSAGE_SENT->SendMessageHolder(view!!)
            VIEW_TYPE_MESSAGE_RECEIVED->ReceiverMessageHolder(view!!)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    private inner class SendMessageHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val messageText :TextView=itemView.findViewById(R.id.text_message)
        private val timeText:TextView=itemView.findViewById(R.id.time_stamp)
        private val utils:Utils=Utils()

        fun bind(message:Message) {
            messageText.text=message.message
            // Format the stored timestamp into a readable String using method.
            timeText.text=this.utils.formatDateTime(message.createdAt, "America/Los_Angeles")
        }
    }
    private inner class ReceiverMessageHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val messageText: TextView=itemView.findViewById(R.id.receiver_message)
        private val timeText:TextView=itemView.findViewById(R.id.receiver_time)
        private val nameText:TextView=itemView.findViewById(R.id.receiver_name)
        private val utils:Utils=Utils()
        fun bind(message: Message){
            messageText.text=message.message
            timeText.text=this.utils.formatDateTime(message.createdAt,"America/Los_Angeles")
            nameText.text=message.sender.nickname
        }
    }
}