package com.ctech.eaty.ui.job.view

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.ctech.eaty.BuildConfig
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReactActivity
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.react.NativeInfraReactPackage
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.ui.job.support.JobBridgeReactPackage
import com.ctech.eaty.ui.job.support.NativeHostContract
import com.ctech.eaty.ui.job.viewmodel.JobListViewModel
import com.ctech.eaty.ui.productdetail.action.ProductDetailAction
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.facebook.react.ReactPackage
import com.facebook.react.ReactRootView
import com.facebook.react.devsupport.RedBoxHandler
import com.facebook.react.shell.MainReactPackage
import com.facebook.react.uimanager.UIImplementationProvider
import kotlinx.android.synthetic.main.activity_job_list.*
import javax.inject.Inject

class JobListActivity : BaseReactActivity(), Injectable, NativeHostContract {

    @Inject
    lateinit var appSettings: AppSettingsManager

    @Inject
    lateinit var viewModel: JobListViewModel

    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list)
        setupToolbar()
        delegate.loadReact()
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

    override fun getJSMainModuleName() = "jobs.android"

    override fun getUseDeveloperSupport() = BuildConfig.DEBUG

    override fun getRedBoxHandler(): RedBoxHandler? = null

    override fun getUIImplementationProvider() = UIImplementationProvider()

    override fun getJSBundleFile(): String? = null

    override fun getBundleAssetName(): String? = "react/jobs/index.android.bundle"

    override fun getLaunchOptions(): Bundle? = null

    override fun getActivity(): Activity = this

    override fun getBaseApplication() = application

    override fun getPackages() =
            listOf(MainReactPackage(), NativeInfraReactPackage(appSettings), JobBridgeReactPackage(this))

    override fun getMainComponentName() = "Jobs"

    override fun getScreenName() = "Jobs"

    override fun mountReactView(view: ReactRootView) {
        reactContainer.removeAllViews()
        reactContainer.addView(view)
    }

    override fun navigateJobDetail(url: String) {
        viewModel.navigateJobDetail(url, customTabActivityHelper.session)
    }

    override fun navigateUser(id: String) {
        viewModel.navigateUser(id)
    }

}