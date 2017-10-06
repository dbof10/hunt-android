package com.ctech.eaty.ui.home.component

import android.support.v7.widget.RecyclerView
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Recycler
import com.facebook.litho.widget.RecyclerBinder


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
                    .withLayout()
                    .testKey(MAIN_SCREEN)
                    .build()
        }
    }

}