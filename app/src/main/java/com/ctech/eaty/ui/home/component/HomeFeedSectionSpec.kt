package com.ctech.eaty.ui.home.component

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.HomeFeed
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.annotations.OnRefresh
import com.facebook.litho.sections.annotations.OnViewportChanged

@GroupSectionSpec
object HomeFeedSectionSpec {

    @OnCreateChildren
    fun onCreateChildren(c: SectionContext,
                         @Prop feeds: List<HomeFeed>): Children {
        val children = Children.create()

        feeds.forEach {
            children.child(
                    FeedSection.create(c)
                            .feed(it)
                            .key(it.date.date)
                            .build()
            )
        }

        return children.build()
    }

    @OnViewportChanged
    fun onViewportChanged(
            c: SectionContext,
            firstVisible: Int,
            lastVisible: Int,
            totalCount: Int,
            fistFullyVisible: Int,
            lastFullyVisible: Int,
            @Prop store: Store<HomeState>) {
        val threshold = 6
        if (totalCount - lastVisible < threshold) {
            store.dispatch(HomeAction.LOAD_MORE)
        }
    }

    @OnRefresh
    fun onRefresh(
            c: SectionContext,
            @Prop store: Store<HomeState>) {
        store.dispatch(HomeAction.REFRESH)
    }
}