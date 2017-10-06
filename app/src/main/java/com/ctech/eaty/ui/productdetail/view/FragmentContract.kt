package com.ctech.eaty.ui.productdetail.view

import android.view.View

interface FragmentContract {
    fun onScrolled(headerView: View)
    fun onScrollStateChanged(newState: Int)
    fun onFling()
    fun onFinishFragmentInflate()
    fun onDataLoaded()
}