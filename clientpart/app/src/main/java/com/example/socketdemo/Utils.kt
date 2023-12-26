package com.example.socketdemo

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
class Utils{
    fun formatTime(time: Long, timeZoneId: String): String {
        val date=Date(time)
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone(timeZoneId)
        return formatter.format(date)
    }
    fun formatDate(time: Long, timeZoneId: String): String {
        val date=Date(time)
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone(timeZoneId)
        return formatter.format(date)
    }
}

