package com.example.socketdemo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReceivedMessageHolder:RecyclerView.ViewHolder {
    /*
    TextView messageText, timeText, nameText;
3	    ImageView profileImage;
super(itemView);
7	        messageText = (TextView) itemView.findViewById(R.id.text_gchat_user_other);
8	        timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_other);
9	        nameText = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
10	        profileImage = (ImageView) itemView.findViewById(R.id.image_gchat_profile_other);
11	    }
     */
    lateinit var messageText:TextView
    lateinit var timeText:TextView
    lateinit var nameText:TextView
    lateinit var profileImage:ImageView
    constructor(myReceiveView: View):super(myReceiveView){
        this.messageText=myReceiveView.findViewById(R.id.)
    }
}