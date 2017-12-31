package com.ctech.eaty.util

import android.content.Context
import net.danlew.android.joda.DateUtils
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


object DateUtils {

    private val dateFormatter: DateTimeFormatter by lazy {
        DateTimeFormat.forPattern("yyyy-MM-dd")
    }

    private val friendlyDateFormatter: DateTimeFormatter by lazy {
        DateTimeFormat.forPattern("dd-MM-YYYY")
    }

    private val relativeDateFormatter: DateTimeFormatter by lazy {
        DateTimeFormat.forPattern("EEEE")
    }

    fun getRelativeTime(current: DateTime, specific: DateTime): String {
        val currentWeek = current.weekOfWeekyear().get()
        val specificWeek = specific.weekOfWeekyear().get()
        return if (currentWeek == specificWeek) {
            relativeDateFormatter.print(specific)
        } else {
            friendlyDateFormatter.print(specific)
        }
    }

    fun getRelativeTime(time: DateTime): String {
        return relativeDateFormatter.print(time)
    }

    fun getFormattedPastDate(current: DateTime, dayAgo: Int): String {
        if (dayAgo == 0) {
            return dateFormatter.print(current)
        }
        val pastDateTime = current.minusDays(dayAgo)
        return dateFormatter.print(pastDateTime)
    }

    fun getRelativeTimeSpan(context: Context, time: DateTime): String {
        return DateUtils.getRelativeTimeSpanString(context, time).toString()
    }

    fun parseStringAsMiliSec(date: String): Long {
        return dateFormatter.parseMillis(date)
    }
}
