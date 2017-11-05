package com.ctech.eaty.ui.meetup.view

import android.app.Activity
import android.os.Bundle
import com.ctech.eaty.BuildConfig
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReactActivity
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.MeetupEvent
import com.ctech.eaty.react.NativeInfraReactPackage
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.meetup.support.MeetupBridgeReactPackage
import com.ctech.eaty.ui.meetup.support.NativeHostContract
import com.ctech.eaty.ui.meetup.viewmodel.MeetupViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.facebook.react.ReactRootView
import com.facebook.react.devsupport.RedBoxHandler
import com.facebook.react.shell.MainReactPackage
import com.facebook.react.uimanager.UIImplementationProvider
import kotlinx.android.synthetic.main.activity_job_list.*
import javax.inject.Inject

class MeetupActivity : BaseReactActivity(), Injectable, NativeHostContract {

    @Inject
    lateinit var appSettings: AppSettingsManager

    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    @Inject
    lateinit var viewModel: MeetupViewModel

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meetup)
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
            finishAfterTransition()
        }
    }

    override fun getJSMainModuleName() = "meetups.android"

    override fun getUseDeveloperSupport() = BuildConfig.DEBUG

    override fun getRedBoxHandler(): RedBoxHandler? = null

    override fun getUIImplementationProvider() = UIImplementationProvider()

    override fun getJSBundleFile(): String? = null

    override fun getBundleAssetName(): String? = "react/meetups/index.android.bundle"

    override fun getLaunchOptions(): Bundle? = null

    override fun getActivity(): Activity = this

    override fun getBaseApplication() = application

    override fun getPackages() =
            listOf(MainReactPackage(), NativeInfraReactPackage(appSettings),
                    MeetupBridgeReactPackage(this))

    override fun getMainComponentName() = "Meetups"

    override fun getScreenName() = "Meetups"

    override fun mountReactView(view: ReactRootView) {
        reactContainer.removeAllViews()
        reactContainer.addView(view)
    }

    override fun navigateEventDetail(url: String) {
        viewModel.navigateEventDetail(url, customTabActivityHelper.session)
    }

    override fun shareEvent(url: String) {
        viewModel.shareEvent(url)
    }

    override fun addEventToCalendar(event: MeetupEvent) {
        viewModel.addEventToCalendar(event)
    }

}