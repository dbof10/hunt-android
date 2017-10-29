package com.ctech.eaty.ui.ask.support

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class NativeNavigatorModule(context: ReactApplicationContext,
                            private val hostContract: NativeHostContract) : ReactContextBaseJavaModule(context) {

    override fun getName() = "AskBridge"

    @ReactMethod
    fun shareUrl(url: String){
        hostContract.shareUrl(url)
    }
}