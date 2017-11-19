package com.ctech.eaty.react.widget.tablayout

import android.annotation.SuppressLint
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.facebook.react.bridge.ReactContext
import com.facebook.react.uimanager.UIManagerModule
import com.facebook.react.uimanager.events.EventDispatcher
import com.facebook.react.uimanager.events.NativeGestureUtil


@SuppressLint("ViewConstructor")
class ReactViewPager(reactContext: ReactContext) : ViewPager(reactContext) {

    private var pageNames: List<String> = emptyList()

    private var parentIdCallback: ParentIdCallback? = null

    private val eventDispatcher: EventDispatcher = reactContext.getNativeModule(UIManagerModule::class.java).eventDispatcher

    private var isCurrentItemFromJs: Boolean = false
    private var scrollEnabled = true

    internal val viewCountInAdapter: Int
        get() = adapter.count

    interface ParentIdCallback {
        val parentId: Int
    }

    fun setParentIdCallback(parentIdCallback: ParentIdCallback) {
        this.parentIdCallback = parentIdCallback
    }

    fun setPageNames(names: List<String>) {
        this.pageNames = names
    }

    inner class Adapter : PagerAdapter() {

        private var views = ArrayList<View>()
        private var mIsViewPagerInIntentionallyInconsistentState = false

        override fun getCount() = views.size

        internal fun addView(child: View, index: Int) {
            views.add(index, child)
            notifyDataSetChanged()
            // This will prevent view pager from detaching views for pages that are not currently selected
            // We need to do that since {@link ViewPager} relies on layout passes to position those views
            // in a right way (also thanks to {@link ReactViewPagerManager#needsCustomLayoutForChildren}
            // returning {@code true}). Currently we only call {@link View#measure} and
            // {@link View#layout} after CSSLayout step.

            // TODO(7323049): Remove this workaround once we figure out a way to re-layout some views on
            // request
            offscreenPageLimit = views.size
        }

        internal fun removeViewAt(index: Int) {
            views.removeAt(index)
            notifyDataSetChanged()

            // TODO(7323049): Remove this workaround once we figure out a way to re-layout some views on
            // request
            offscreenPageLimit = views.size
        }

        internal fun getViewAt(index: Int): View {
            return views[index]
        }

        override fun getItemPosition(obj: Any): Int {
            // if we've removed all views, we want to return POSITION_NONE intentionally
            return if (mIsViewPagerInIntentionallyInconsistentState || !views.contains(obj))
                POSITION_NONE
            else
                views.indexOf(obj)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = views[position]
            container.addView(view, 0, generateDefaultLayoutParams())
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun getPageTitle(position: Int): CharSequence {
            return if (pageNames.size > position) {
                pageNames[position]
            } else "Position: " + position
        }
    }

    private inner class PageChangeListener : OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            eventDispatcher.dispatchEvent(PageScrollEvent(parentIdCallback!!.parentId, position, positionOffset))
        }

        override fun onPageSelected(position: Int) {
            if (!isCurrentItemFromJs) {
                eventDispatcher.dispatchEvent(PageSelectedEvent(parentIdCallback!!.parentId, position))
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            val pageScrollState: String = when (state) {
                ViewPager.SCROLL_STATE_IDLE -> "idle"
                ViewPager.SCROLL_STATE_DRAGGING -> "dragging"
                ViewPager.SCROLL_STATE_SETTLING -> "settling"
                else -> throw IllegalStateException("Unsupported pageScrollState")
            }
            eventDispatcher.dispatchEvent(PageScrollStateChangedEvent(parentIdCallback!!.parentId, pageScrollState))
        }
    }

    init {
        isCurrentItemFromJs = false
        addOnPageChangeListener(PageChangeListener())
        adapter = Adapter()
    }

    override fun getAdapter(): Adapter {
        return super.getAdapter() as Adapter
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!scrollEnabled) {
            return false
        }

        if (super.onInterceptTouchEvent(ev)) {
            NativeGestureUtil.notifyNativeGestureStarted(this, ev)
            return true
        }
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (!scrollEnabled) {
            false
        } else super.onTouchEvent(ev)

    }

    fun setCurrentItemFromJs(item: Int, animated: Boolean) {
        isCurrentItemFromJs = true
        setCurrentItem(item, animated)
        isCurrentItemFromJs = false
    }

    fun setScrollEnabled(scrollEnabled: Boolean) {
        this.scrollEnabled = scrollEnabled
    }

    internal fun addViewToAdapter(child: View, index: Int) {
        adapter.addView(child, index)
    }

    internal fun removeViewFromAdapter(index: Int) {
        adapter.removeViewAt(index)
    }

    internal fun getViewFromAdapter(index: Int): View {
        return adapter.getViewAt(index)
    }

}