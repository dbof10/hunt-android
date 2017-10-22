package com.ctech.eaty.ui.meetup.support

import com.ctech.eaty.entity.MeetupEvent

interface NativeHostContract {
    fun navigateEventDetail(url: String)
    fun shareEvent(url: String)
    fun addEventToCalendar(event: MeetupEvent)
}
