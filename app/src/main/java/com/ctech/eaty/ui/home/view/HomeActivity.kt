package com.ctech.eaty.ui.home.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity.START
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.home.navigation.HomeNavigation
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.util.GlideImageLoader
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_home_content.*
import javax.inject.Inject


class HomeActivity : BaseActivity(), HasSupportFragmentInjector, CustomTabActivityHelper.ConnectionCallback {
    override fun getScreenName() = "Home"

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var homeNavigation: HomeNavigation

    @Inject
    lateinit var imageLoader: GlideImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupChromeService()
        setupToolbar()
        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = HomeFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            drawer.openDrawer(START)
        }
        navigation.setNavigationItemSelectedListener {
            homeNavigation.delegate(it.itemId).subscribe()
            false
        }
    }

    private fun setupChromeService() {
        customTabActivityHelper.setConnectionCallback(this)
    }


    override fun onStart() {
        super.onStart()
        trackingManager.trackScreenView(getScreenName())
        customTabActivityHelper.bindCustomTabsService(this)
    }

    override fun onStop() {
        super.onStop()
        customTabActivityHelper.unbindCustomTabsService(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onLowMemory() {
        super.onLowMemory()
        imageLoader.clearMemory()
    }

    override fun onCustomTabsConnected() {

    }

    override fun onCustomTabsDisconnected() {

    }
}
