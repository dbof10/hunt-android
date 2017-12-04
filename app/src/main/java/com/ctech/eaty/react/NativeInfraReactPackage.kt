package com.ctech.eaty.react

import android.view.View
import com.ctech.eaty.repository.AppSettingsManager
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

class NativeInfraReactPackage(private val appSettings: AppSettingsManager) : ReactPackage {

    override fun createNativeModules(context: ReactApplicationContext) =
            arrayListOf(AppSettingsModule(context, appSettings))

    override fun createViewManagers(context: ReactApplicationContext) = emptyList<ViewManager<View,*>>()

}