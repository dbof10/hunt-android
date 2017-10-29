package com.ctech.eaty.ui.ask.view

import android.app.Activity
import android.os.Bundle
import com.ctech.eaty.BuildConfig
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReactActivity
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.react.NativeInfraReactPackage
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.ui.ask.support.AskBridgeReactPackage
import com.ctech.eaty.ui.ask.support.NativeHostContract
import com.ctech.eaty.ui.ask.viewmodel.AskViewModel
import com.facebook.react.ReactRootView
import com.facebook.react.devsupport.RedBoxHandler
import com.facebook.react.shell.MainReactPackage
import com.facebook.react.uimanager.UIImplementationProvider
import kotlinx.android.synthetic.main.activity_job_list.*
import javax.inject.Inject

class AskActivity : BaseReactActivity(), Injectable, NativeHostContract {

    @Inject
    lateinit var appSettings: AppSettingsManager

    @Inject
    lateinit var viewModel: AskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)
        setupToolbar()
        delegate.loadReact()
    }


    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finishAfterTransition()
        }
    }

    override fun getJSMainModuleName() = "ask.android"

    override fun getUseDeveloperSupport() = BuildConfig.DEBUG

    override fun getRedBoxHandler(): RedBoxHandler? = null

    override fun getUIImplementationProvider() = UIImplementationProvider()

    override fun getJSBundleFile(): String? = null

    override fun getBundleAssetName(): String? = "react/ask/index.android.bundle"

    override fun getLaunchOptions(): Bundle? = null

    override fun getActivity(): Activity = this

    override fun getBaseApplication() = application

    override fun getPackages() =
            listOf(MainReactPackage(), NativeInfraReactPackage(appSettings), AskBridgeReactPackage(this))

    override fun getMainComponentName() = "Ask"

    override fun getScreenName() = "Ask"

    override fun mountReactView(view: ReactRootView) {
        reactContainer.removeAllViews()
        reactContainer.addView(view)
    }

    override fun shareUrl(url: String) {
        viewModel.shareUrl(url)
    }


}