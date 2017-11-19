package com.ctech.eaty.react.widget.tablayout

import android.view.View
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.common.MapBuilder
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.PixelUtil
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp


@ReactModule(name = TabbedViewPagerManager.REACT_CLASS)
class TabbedViewPagerManager : ViewGroupManager<TabbedViewPager>() {

    companion object {
        const val REACT_CLASS = "TabbedViewPager"
    }

    private val COMMAND_SET_PAGE = 1
    private val COMMAND_SET_PAGE_WITHOUT_ANIMATION = 2

    override fun getName(): String {
        return REACT_CLASS
    }

    override fun createViewInstance(reactContext: ThemedReactContext): TabbedViewPager {
        val viewPager = TabbedViewPager(reactContext)
        viewPager.setup(reactContext)
        return viewPager
    }

    override fun onDropViewInstance(view: TabbedViewPager) {
        super.onDropViewInstance(view)
        view.handleViewDropped()
    }

    //tab properties -- start
    @ReactProp(name = "tabMode")
    fun setTabProperties(viewPager: TabbedViewPager,
                         tabMode: String) {
        viewPager.setTabMode(tabMode)
    }

    @ReactProp(name = "tabGravity")
    fun setTabGravity(viewPager: TabbedViewPager, tabGravity: String) {
        viewPager.setTabGravity(tabGravity)
    }

    @ReactProp(name = "tabBackground")
    fun setTabBackground(viewPager: TabbedViewPager, tabBackgroundColor: Int) {
        viewPager.setTabBackgroundColor(tabBackgroundColor)
    }

    @ReactProp(name = "tabIndicatorColor")
    fun setTabIndicatorColor(viewPager: TabbedViewPager, tabIndicatorColor: Int) {
        viewPager.setTabIndicatorColor(tabIndicatorColor)
    }

    @ReactProp(name = "tabSelectedTextColor")
    fun setTabSelectedTextColor(viewPager: TabbedViewPager, tabSelectedTextColor: Int) {
        viewPager.setTabSelectedTextColor(tabSelectedTextColor)
    }

    @ReactProp(name = "tabTextColor")
    fun setTabTextColor(viewPager: TabbedViewPager, tabTextColor: Int) {
        viewPager.setTabTextColor(tabTextColor)
    }

    @ReactProp(name = "tabIndicatorHeight")
    fun setTabIndicatorHeight(viewPager: TabbedViewPager, tabIndicatorHeight: Float) {
        viewPager.setTabIndicatorHeight(PixelUtil.toPixelFromDIP(tabIndicatorHeight))
    }

    @ReactProp(name = "tabElevation")
    fun setTabElevation(viewPager: TabbedViewPager, tabElevation: Float) {
        viewPager.setTabElevation(PixelUtil.toPixelFromDIP(tabElevation))
    }

    @ReactProp(name = "tabNames")
    fun setTabNames(viewPager: TabbedViewPager, readableArray: ReadableArray) {

        val names = ArrayList<String>(readableArray.size())
        (0 until readableArray.size()).mapTo(names) {
            readableArray.getString(it)
        }
        viewPager.setTabNames(names)
    }

    //tab properties -- end

    @ReactProp(name = "scrollEnabled", defaultBoolean = true)
    fun setScrollEnabled(viewPager: TabbedViewPager, value: Boolean) {
        viewPager.setScrollEnabled(value)
    }

    override fun needsCustomLayoutForChildren(): Boolean {
        return true
    }

    override fun getExportedCustomDirectEventTypeConstants(): Map<String, *> {
        return MapBuilder.of(PageScrollEvent.EVENT_NAME,
                MapBuilder.of("registrationName", "onPageScroll"),
                PageScrollStateChangedEvent.EVENT_NAME,
                MapBuilder.of("registrationName", "onPageScrollStateChanged"), PageSelectedEvent.EVENT_NAME,
                MapBuilder.of("registrationName", "onPageSelected"))
    }

    override fun getCommandsMap(): Map<String, Int>? {
        return MapBuilder.of("setPage", COMMAND_SET_PAGE, "setPageWithoutAnimation",
                COMMAND_SET_PAGE_WITHOUT_ANIMATION)
    }

    override fun receiveCommand(viewPager: TabbedViewPager, commandType: Int,
                                args: ReadableArray?) {
        when (commandType) {
            COMMAND_SET_PAGE -> {
                viewPager.setCurrentItemFromJs(args!!.getInt(0), true)
                return
            }
            COMMAND_SET_PAGE_WITHOUT_ANIMATION -> {
                viewPager.setCurrentItemFromJs(args!!.getInt(0), false)
                return
            }
            else -> throw IllegalArgumentException(
                    String.format("Unsupported command %d received by %s.", commandType,
                            javaClass.simpleName))
        }
    }

    override fun addView(parent: TabbedViewPager, child: View, index: Int) {
        parent.addViewToAdapter(child, index)
    }

    override fun getChildCount(parent: TabbedViewPager): Int {
        return parent.viewCountInAdapter
    }

    override fun getChildAt(parent: TabbedViewPager, index: Int): View {
        return parent.getViewFromAdapter(index)
    }

    override fun removeViewAt(parent: TabbedViewPager, index: Int) {
        parent.removeViewFromAdapter(index)
    }

    @ReactProp(name = "pageMargin", defaultFloat = 0f)
    fun setPageMargin(pager: TabbedViewPager, margin: Float) {
        pager.setPageMargin(PixelUtil.toPixelFromDIP(margin).toInt())
    }

}