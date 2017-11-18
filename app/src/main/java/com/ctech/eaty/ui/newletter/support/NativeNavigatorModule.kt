package com.ctech.eaty.ui.newletter.support

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule

class NativeNavigatorModule(context: ReactApplicationContext,
                            private val hostContract: NativeHostContract) : ReactContextBaseJavaModule(context) {

    override fun getName() = "NewLetterBridge"

}