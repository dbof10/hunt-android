package com.ctech.eaty.react.widget.tablayout

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.events.Event
import com.facebook.react.uimanager.events.RCTEventEmitter


internal class PageSelectedEvent constructor(viewTag: Int, private val mPosition: Int) : Event<PageSelectedEvent>(viewTag) {

    companion object {
        const val EVENT_NAME = "topPageSelected"
    }

    override fun getEventName() = EVENT_NAME

    override fun dispatch(rctEventEmitter: RCTEventEmitter) {
        rctEventEmitter.receiveEvent(viewTag, eventName, serializeEventData())
    }

    private fun serializeEventData(): WritableMap {
        val eventData = Arguments.createMap()
        eventData.putInt("position", mPosition)
        return eventData
    }


}