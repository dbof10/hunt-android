package com.ctech.eaty.util

import android.content.Context
import net.danlew.android.joda.DateUtils
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


class DateUtils private constructor() {

    companion object {
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
            if (currentWeek == specificWeek) {
                return relativeDateFormatter.print(specific)
            } else {
                return friendlyDateFormatter.print(specific)
            }
        }

        fun getRelativeTime(time: DateTime): String {
            return relativeDateFormatter.print(time)
        }

        fun getFormattedDate(current: DateTime): String {
            return dateFormatter.print(current)
        }

        fun getFormattedPastDate(current: DateTime, dayAgo: Int): String {
            val pastDateTime = current.minusDays(dayAgo)
            return dateFormatter.print(pastDateTime)
        }

        fun getRelativeTimeSpan(context: Context, time: DateTime): String {
            return DateUtils.getRelativeTimeSpanString(context, time).toString()
        }
    }
}
