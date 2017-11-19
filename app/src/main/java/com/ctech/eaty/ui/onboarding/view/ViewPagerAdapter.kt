package com.ctech.eaty.ui.onboarding.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var scenes: List<Fragment> = emptyList()

    override fun getCount(): Int {
        return scenes.size
    }

    override fun getItem(position: Int): Fragment {
        return scenes[position]
    }

    fun setData(dataSource: List<Fragment>) {
        this.scenes = dataSource
        notifyDataSetChanged()
    }

}