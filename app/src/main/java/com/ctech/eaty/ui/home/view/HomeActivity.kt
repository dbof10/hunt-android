package com.ctech.eaty.ui.home.view

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class HomeActivity : BaseActivity(), HasSupportFragmentInjector, CustomTabActivityHelper.ConnectionCallback {


    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
        setupChromeService()
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
