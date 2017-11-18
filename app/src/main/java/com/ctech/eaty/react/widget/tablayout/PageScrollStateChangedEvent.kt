package com.ctech.eaty.react.widget.tablayout

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.events.Event
import com.facebook.react.uimanager.events.RCTEventEmitter


internal class PageScrollStateChangedEvent constructor(viewTag: Int, private val mPageScrollState: String) : Event<PageScrollStateChangedEvent>(viewTag) {

    companion object {
        const val EVENT_NAME = "topPageScrollStateChanged"
    }


    override fun getEventName() = EVENT_NAME

    override fun dispatch(rctEventEmitter: RCTEventEmitter) {
        rctEventEmitter.receiveEvent(viewTag, eventName, serializeEventData())
    }

    private fun serializeEventData(): WritableMap {
        val eventData = Arguments.createMap()
        eventData.putString("pageScrollState", mPageScrollState)
        return eventData
    }

}