package com.ctech.eaty.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.facebook.react.ReactPackage
import com.facebook.react.ReactRootView
import com.facebook.react.devsupport.RedBoxHandler
import com.facebook.react.uimanager.UIImplementationProvider

interface ReactNativeHost {
    fun getJSMainModuleName(): String
    fun getUseDeveloperSupport(): Boolean
    fun getRedBoxHandler(): RedBoxHandler?
    fun getUIImplementationProvider(): UIImplementationProvider
    fun getJSBundleFile(): String?
    fun getBundleAssetName(): String?
    fun getLaunchOptions(): Bundle?
    fun getBaseApplication(): Application
    fun getActivity(): Activity
    fun getPackages(): List<ReactPackage>
    fun mountReactView(view: ReactRootView)

}