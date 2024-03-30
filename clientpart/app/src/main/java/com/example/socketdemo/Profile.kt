package com.example.socketdemo

import android.content.Context
import android.content.SharedPreferences
import com.example.socketdemo.SocketClient

class ProfilePreference (var context: Context? = null){
    private val prefsFilename = "com.example.socketdemo.prefs"
    private var prefs: SharedPreferences? = null
    val baseUrl="http://192.168.108.1:8080"
    val socketBaseUrl="http://192.168.108.1:5000"
    init {
        this.prefs = context?.getSharedPreferences(prefsFilename, Context.MODE_PRIVATE)
    }
    var sid: String
        set(value) {
            this.prefs?.edit()?.putString("sid", value)?.apply()
        }
        get() = prefs?.getString("sid", "") ?: ""
    var photoId: String
        set(value) {
            this.prefs?.edit()?.putString("photoId", value)?.apply()
        }
        get() = prefs?.getString("photoId", "") ?: ""
    var nickname:String
        set(value) {
            this.prefs?.edit()?.putString("nickname", value)?.apply()
        }
        get() = prefs?.getString("nickname", "") ?: ""
    var userid:String
        set(value) {
            this.prefs?.edit()?.putString("userid", value)?.apply()
        }
        get() = prefs?.getString("userid", "") ?: ""
}