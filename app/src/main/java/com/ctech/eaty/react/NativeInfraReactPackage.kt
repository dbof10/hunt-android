package com.ctech.eaty.react

import android.view.View
import com.ctech.eaty.repository.AppSettingsManager
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class NativeInfraReactPackage(private val appSettings: AppSettingsManager) : ReactPackage {

    override fun createNativeModules(context: ReactApplicationContext): List<NativeModule> =
            arrayListOf(AppSettingsModule(context, appSettings))

    override fun createViewManagers(context: ReactApplicationContext): List<ViewManager<View, ReactShadowNode>> = emptyList()

}