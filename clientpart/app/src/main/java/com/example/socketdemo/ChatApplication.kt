package com.example.socketdemo

import android.app.Application

val Profile: ProfilePreference by lazy {
    ChatApplication.prefs!!
}
class ChatApplication : Application(){
    companion object {
        var prefs: ProfilePreference? = null
    }
}