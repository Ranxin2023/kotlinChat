package com.example.socketdemo

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
class Utils{
    public fun formatDateTime(time: Long, timeZoneId: String): String {
        val date=Date(time)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone(timeZoneId)
        return formatter.format(date)
    }
}

