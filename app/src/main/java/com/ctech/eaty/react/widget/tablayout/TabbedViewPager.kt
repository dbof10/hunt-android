package com.ctech.eaty.react.widget.tablayout

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.facebook.react.bridge.ReactContext


class TabbedViewPager : LinearLayout {
    private lateinit var reactViewPager: ReactViewPager
    private lateinit var tabLayout: TabLayout

    val viewCountInAdapter: Int
        get() = this.reactViewPager.viewCountInAdapter

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    internal fun setup(reactContext: ReactContext) {
        this.orientation = LinearLayout.VERTICAL
        this.reactViewPager = ReactViewPager(reactContext)
        this.reactViewPager.setParentIdCallback(object : ReactViewPager.ParentIdCallback {
            override val parentId: Int
                get() = id
        })
        this.tabLayout = TabLayout(reactContext)
        this.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        val viewPagerParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                1f)

        val tabParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.addView(tabLayout, tabParams)
        this.addView(reactViewPager, viewPagerParams)
        tabLayout.setupWithViewPager(reactViewPager)
    }

    fun handleViewDropped() {

    }

    fun setScrollEnabled(value: Boolean) {
        this.reactViewPager.setScrollEnabled(value)
    }

    fun setCurrentItemFromJs(anInt: Int, b: Boolean) {
        this.reactViewPager.setCurrentItemFromJs(anInt, b)
    }

    fun addViewToAdapter(child: View, index: Int) {
        this.reactViewPager.addViewToAdapter(child, index)
    }

    fun getViewFromAdapter(index: Int): View {
        return this.reactViewPager.getViewFromAdapter(index)
    }

    fun removeViewFromAdapter(index: Int) {
        this.reactViewPager.removeViewFromAdapter(index)
    }

    fun setPageMargin(i: Int) {
        this.reactViewPager.pageMargin = i
    }

    fun setTabMode(tabMode: String) {
        if ("scrollable".equals(tabMode, ignoreCase = true)) {
            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        } else {
            tabLayout.tabMode = TabLayout.MODE_FIXED
        }
    }

    fun setTabGravity(tabGravity: String) {
        if ("center".equals(tabGravity, ignoreCase = true)) {
            tabLayout.tabMode = TabLayout.GRAVITY_CENTER
        } else {
            tabLayout.tabMode = TabLayout.GRAVITY_FILL
        }
    }

    fun setTabBackgroundColor(tabBackgroundColor: Int) {
        tabLayout.setBackgroundColor(tabBackgroundColor)
    }

    fun setTabIndicatorColor(tabIndicatorColor: Int) {
        tabLayout.setSelectedTabIndicatorColor(tabIndicatorColor)
    }

    fun setTabIndicatorHeight(height: Float) {
        tabLayout.setSelectedTabIndicatorHeight(height.toInt())
    }

    fun setTabSelectedTextColor(tabSelectedTextColor: Int) {
        val stateList = tabLayout.tabTextColors
        val normalColor = stateList!!.getColorForState(View.EMPTY_STATE_SET, tabSelectedTextColor)
        tabLayout.setTabTextColors(normalColor, tabSelectedTextColor)
    }

    fun setTabTextColor(tabTextColor: Int) {
        val stateList = tabLayout.tabTextColors
        val selectedColor = stateList!!.getColorForState(View.SELECTED_STATE_SET, tabTextColor)
        tabLayout.setTabTextColors(tabTextColor, selectedColor)
    }

    fun setTabNames(names: List<String>) {
        reactViewPager.setPageNames(names)
    }

    fun setTabElevation(elevation: Float) {
        ViewCompat.setElevation(tabLayout, elevation)
    }
}