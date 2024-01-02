package com.example.socketdemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayOutputStream

class ReceiverMessageHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val messageText: TextView =itemView.findViewById(R.id.receiver_message)
    private val dateText: TextView =itemView.findViewById(R.id.text_date_receiver)
    private val timeStamp: TextView =itemView.findViewById(R.id.time_stamp_receiver)
    private val nameText: TextView =itemView.findViewById(R.id.receiver_name)
    private val messagePhoto:ImageView=itemView.findViewById(R.id.receiver_profile)
    private val utils:Utils=Utils()
    private fun setBase64ToImageView(base64Str: String, imageView: ImageView) {
        // Decode Base64 string to Bitmap
        val imageBytes = Base64.decode(base64Str, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        // Set the Bitmap to ImageView
        imageView.setImageBitmap(decodedImage)
    }
    fun bind(message: Message){
        messageText.text=message.message
        dateText.text=this.utils.formatDate(message.createdAt, "America/Los_Angeles")
        timeStamp.text=this.utils.formatTime(message.createdAt,"America/Los_Angeles")
        nameText.text=message.sender.nickname
        this.setBase64ToImageView(message.sender.photoCode, this.messagePhoto)
    }
}