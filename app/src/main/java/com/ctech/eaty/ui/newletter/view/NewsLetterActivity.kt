package com.ctech.eaty.ui.newletter.view

import android.app.Activity
import android.os.Bundle
import com.ctech.eaty.BuildConfig
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReactActivity
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.react.NativeInfraReactPackage
import com.ctech.eaty.react.widget.tablayout.TabbedViewPagerAndroidPackage
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.newletter.support.NativeHostContract
import com.ctech.eaty.ui.newletter.support.NewLetterReactPackage
import com.ctech.eaty.ui.newletter.viewmodel.NewsLetterViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.facebook.react.ReactRootView
import com.facebook.react.devsupport.RedBoxHandler
import com.facebook.react.shell.MainReactPackage
import com.facebook.react.uimanager.UIImplementationProvider
import kotlinx.android.synthetic.main.activity_job_list.*
import javax.inject.Inject

class NewsLetterActivity : BaseReactActivity(), Injectable, NativeHostContract {

    @Inject
    lateinit var appSettings: AppSettingsManager

    @Inject
    lateinit var viewModel: NewsLetterViewModel

    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_letter)
        setupToolbar()
        delegate.loadReact()
        trackingManager.trackScreenView(getScreenName())
    }

    override fun onStart() {
        super.onStart()
        customTabActivityHelper.bindCustomTabsService(this)
    }

    override fun onStop() {
        customTabActivityHelper.unbindCustomTabsService(this)
        super.onStop()
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun getJSMainModuleName() = "newsletter.android"

    override fun getUseDeveloperSupport() = BuildConfig.DEBUG

    override fun getRedBoxHandler(): RedBoxHandler? = null

    override fun getUIImplementationProvider() = UIImplementationProvider()

    override fun getJSBundleFile(): String? = null

    override fun getBundleAssetName(): String? = "react/newsletter/index.android.bundle"

    override fun getLaunchOptions(): Bundle? = null

    override fun getActivity(): Activity = this

    override fun getBaseApplication() = application

    override fun getPackages() =
            listOf(MainReactPackage(),
                    NativeInfraReactPackage(appSettings),
                    NewLetterReactPackage(this),
                    TabbedViewPagerAndroidPackage())

    override fun getMainComponentName() = "NewsLetters"

    override fun getScreenName() = "NewLetter"

    override fun mountReactView(view: ReactRootView) {
        reactContainer.removeAllViews()
        reactContainer.addView(view)
    }

}