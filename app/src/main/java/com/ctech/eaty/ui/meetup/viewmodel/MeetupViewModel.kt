package com.ctech.eaty.ui.meetup.viewmodel

import android.support.customtabs.CustomTabsSession
import com.ctech.eaty.entity.MeetupEvent
import com.ctech.eaty.ui.meetup.navigator.MeetupNavigator
import com.ctech.eaty.util.rx.Functions
import timber.log.Timber
import javax.inject.Inject

class MeetupViewModel @Inject constructor(private val navigator: MeetupNavigator) {

    fun navigateEventDetail(url: String, session: CustomTabsSession?) {
        navigator.toUrl(url, session)
                .subscribe(Functions.EMPTY, Timber::e)
    }

    fun shareEvent(url: String) {
        navigator.toShare(url)
                .subscribe(Functions.EMPTY, Timber::e)
    }

    fun addEventToCalendar(event: MeetupEvent) {
        navigator.toCalendar(event)
                .subscribe(Functions.EMPTY, Timber::e)
    }

}