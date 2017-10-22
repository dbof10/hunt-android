package com.ctech.eaty.ui.meetup.support

import com.ctech.eaty.entity.MeetupEvent
import com.ctech.eaty.util.DateUtils
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap

class NativeNavigatorModule(context: ReactApplicationContext,
                            private val hostContract: NativeHostContract) : ReactContextBaseJavaModule(context) {

    override fun getName() = "MeetupBridge"

    @ReactMethod
    fun navigateEventDetail(url: String) {
        hostContract.navigateEventDetail(url)
    }

    @ReactMethod
    fun shareEvent(url: String) {
        hostContract.shareEvent(url)
    }

    @ReactMethod
    fun addEventToCalendar(jsObject: ReadableMap) {
        val date = jsObject.getString("event_date")
        val location = jsObject.getString("title")
        val description = jsObject.getString("description")

        hostContract.addEventToCalendar(MeetupEvent(DateUtils.parseStringAsMiliSec(date), location, description))
    }
}