package com.ctech.eaty.ui.live.view

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.ctech.eaty.BuildConfig
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReactActivity
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.react.NativeInfraReactPackage
import com.ctech.eaty.repository.AppSettingsManager
import com.facebook.react.ReactPackage
import com.facebook.react.ReactRootView
import com.facebook.react.devsupport.RedBoxHandler
import com.facebook.react.shell.MainReactPackage
import com.facebook.react.uimanager.UIImplementationProvider
import kotlinx.android.synthetic.main.activity_live_event.*
import javax.inject.Inject

class LiveEventActivity : BaseReactActivity(), Injectable {


    @Inject
    lateinit var appSettings: AppSettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_event)
        setupToolbar()
        delegate.loadReact()
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finishAfterTransition()
        }
    }

    override fun getJSMainModuleName() = "live.android"

    override fun getUseDeveloperSupport(): Boolean = BuildConfig.DEBUG

    override fun getRedBoxHandler(): RedBoxHandler? = null

    override fun getUIImplementationProvider(): UIImplementationProvider = UIImplementationProvider()

    override fun getJSBundleFile(): String? = null

    override fun getBundleAssetName(): String? = "react/live/index.android.bundle"

    override fun getLaunchOptions(): Bundle? = null

    override fun getActivity(): Activity = this

    override fun getBaseApplication(): Application = application

    override fun getPackages(): List<ReactPackage> =
            listOf(MainReactPackage(), NativeInfraReactPackage(appSettings))

    override fun getMainComponentName() = "LiveEvent"

    override fun getScreenName() = "LiveEvent"

    override fun mountReactView(view: ReactRootView) {
        reactContainer.removeAllViews()
        reactContainer.addView(view)
    }
}