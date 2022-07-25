package com.example.otpsender.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {

    private val SECOND_MILLIS = 1000
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private  val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private  val DAY_MILLIS = 24 * HOUR_MILLIS

    fun getTimeAgo(t: Long, ctx: Context?): String? {
        var time = t
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }
        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "one moment ago"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "one minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            (diff/MINUTE_MILLIS).toString() + " minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "one hour ago"
        } else if (diff < 24 * HOUR_MILLIS) {
            (diff / HOUR_MILLIS).toString() + " hours ago"
        } else {
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            val date = simpleDateFormat.format(time)
            "$date"
        }
    }
}