package com.ctech.eaty.ui.home.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var fragments: List<Fragment> = emptyList()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    fun updateFragments(fragments: List<Fragment>) {
        this.fragments = fragments
        notifyDataSetChanged()
    }
}