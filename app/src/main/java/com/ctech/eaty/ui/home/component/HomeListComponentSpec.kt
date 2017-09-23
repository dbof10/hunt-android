package com.ctech.eaty.ui.home.component

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Recycler
import com.facebook.litho.widget.RecyclerBinder
import com.facebook.litho.annotations.FromEvent
import com.facebook.litho.ClickEvent
import com.facebook.litho.EventHandler
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.widget.PTRRefreshEvent
import com.facebook.react.views.swiperefresh.RefreshEvent


@LayoutSpec
class HomeListComponentSpec {


    companion object {
        private val MAIN_SCREEN = "main_screen"

        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext,
                           @Prop binder: RecyclerBinder,
                           @Prop scrollListener: RecyclerView.OnScrollListener): ComponentLayout {
            return Recycler.create(c)
                    .binder(binder)
                    .onScrollListener(scrollListener)
                    .pTRRefreshEventHandler(HomeListComponent.onRefresh(c))
                    .withLayout()
                    .testKey(MAIN_SCREEN)
                    .build()
        }

        @JvmStatic
        @OnEvent(PTRRefreshEvent::class)
        fun onRefresh(c: ComponentContext) {
            Log.e("ABCD","<JABDKDJB")
        }
    }

}