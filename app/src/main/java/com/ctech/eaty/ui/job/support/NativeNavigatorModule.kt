package com.ctech.eaty.ui.job.support

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class NativeNavigatorModule(context: ReactApplicationContext,
                            private val hostContract: NativeHostContract) : ReactContextBaseJavaModule(context) {

    override fun getName() = "JobBridge"

    @ReactMethod
    fun navigateJobDetail(url: String) {
        hostContract.navigateJobDetail(url)
    }

    @ReactMethod
    fun navigateUser(id: String) {
        hostContract.navigateUser(id)
    }
}