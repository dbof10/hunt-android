package com.ctech.eaty.react.widget.tablayout

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.events.Event
import com.facebook.react.uimanager.events.RCTEventEmitter


internal class PageScrollEvent constructor(viewTag: Int, private val mPosition: Int, offset: Float) : Event<PageScrollEvent>(viewTag) {

    companion object {
      const  val EVENT_NAME = "topPageScroll"
    }

    private val mOffset: Float = if (java.lang.Float.isInfinite(offset) || java.lang.Float.isNaN(offset))
        0F
    else
        offset

    override fun getEventName() = EVENT_NAME

   override fun dispatch(rctEventEmitter: RCTEventEmitter) {
        rctEventEmitter.receiveEvent(viewTag, eventName, serializeEventData())
    }

    private fun serializeEventData(): WritableMap {
        val eventData = Arguments.createMap()
        eventData.putInt("position", mPosition)
        eventData.putDouble("offset", mOffset.toDouble())
        return eventData
    }

}