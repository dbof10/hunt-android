package com.ctech.eaty.ui.home.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity.START
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.ui.home.navigation.HomeNavigation
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_home_content.*
import javax.inject.Inject


class HomeActivity : BaseActivity(), HasSupportFragmentInjector, CustomTabActivityHelper.ConnectionCallback {


    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var homeNavigation: HomeNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
        setupChromeService()
        setupToolbar()
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

    private fun setupViewPager() {
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.updateFragments(listOf(HomeFragment.newInstance()))
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onStart() {
        super.onStart()
        customTabActivityHelper.bindCustomTabsService(this)
    }

    override fun onStop() {
        super.onStop()
        customTabActivityHelper.unbindCustomTabsService(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onCustomTabsConnected() {

    }

    override fun onCustomTabsDisconnected() {

    }
}
