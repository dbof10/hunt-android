package com.ctech.eaty.ui.ask.support

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

class AskBridgeReactPackage(private val hostContract: NativeHostContract) : ReactPackage {

    override fun createNativeModules(context: ReactApplicationContext) =
            arrayListOf(NativeNavigatorModule(context, hostContract))

    override fun createViewManagers(context: ReactApplicationContext) = emptyList<ViewManager<View, *>>()

}