package com.example.socketdemo

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

internal class Message {
    var message: String? = null
    var sender: User? = null
    var createdAt: Long = 0
}

internal class User {
    var nickname: String? = null
    var profileUrl: String? = null
}

