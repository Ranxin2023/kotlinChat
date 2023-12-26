package com.example.socketdemo

import android.provider.ContactsContract.CommonDataKinds.Nickname

enum class TYPE {
    MSG_TEXT_SEND,
    MSG_TEXT_RECEIVE,
    MSG_DATE,
    MSG_IMAGE_SEND,
    MSG_IMAGE_RECEIVE,
    MSG_CONTACT_SEND,
    MSG_CONTACT_RECEIVE,
    MSG_MAP_SEND,
    MSG_MAP_RECEIVE,
    MSG_NO_CONTENT,
    MSG_SYS_INFO,
    MSG_SYS_MEMBER,
    MSG_JOB_SEND,
    MSG_JOB_RECEIVE,
}
class User{
    constructor(sid:String, nickname: String){
        this.sid=sid
        this.nickname=nickname
    }
    var sid:String
        set(id){
            field=id
        }
        get()=field
    var nickname:String
        set(name){
            field=name
        }
        get()=field
}
class Message {
    var message: String
        set(msg){
            field=msg
        }
        get()=field
    var sender: User
    var createdAt: Long = 0
        set(time){
            field=time
        }
        get()=field
    constructor(message: String, date:Long, user:User){
        this.message=message
        this.createdAt=date
        this.sender=user
    }



}