package com.ctech.eaty.react.widget.tablayout

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext


class TabbedViewPagerAndroidPackage : ReactPackage {

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        return listOf(TabbedViewPagerAndroidModule(reactContext))
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<TabbedViewPagerManager> {
        return listOf(TabbedViewPagerManager())
    }
}