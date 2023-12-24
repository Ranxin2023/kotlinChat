package com.example.socketdemo

import android.content.Context
import android.content.SharedPreferences

class ProfilePreference (var context: Context? = null){
    private val prefsFilename = "com.example.socketdemo.prefs"
    private var prefs: SharedPreferences? = null
    init {
        this.prefs = context?.getSharedPreferences(prefsFilename, Context.MODE_PRIVATE)
    }
    var sid: String
        set(value) {
            this.prefs?.edit()?.putString("sid", value)?.apply()
        }
        get() = prefs?.getString("sid", "") ?: ""
}