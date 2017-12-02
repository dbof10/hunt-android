package com.ctech.eaty.ui.home.component

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.HomeFeed
import com.ctech.eaty.ui.home.viewmodel.Type
import com.facebook.litho.ClickEvent
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.annotations.OnRefresh
import com.facebook.litho.sections.annotations.OnViewportChanged
import com.facebook.litho.sections.common.SingleComponentSection

@GroupSectionSpec
object HomeFeedSectionSpec {

    @OnCreateChildren
    fun onCreateChildren(c: SectionContext,
                         @Prop feeds: List<HomeFeed>, @Prop store: Store<HomeState>): Children {
        val builder = Children.create()

        feeds.forEach {
            builder.child(
                    FeedSection.create(c)
                            .feed(it)
                            .store(store)
                            .key(it.date.date)
                            .build()
            )
            if (it.horizontalAds.isNotEmpty()) {
                builder.child(
                        HorizontalAdsSection.create(c)
                                .dataSource(it.horizontalAds)
                                .key(it.horizontalAds.first().adId)
                )
            }
            if (it.feedFooter != null) {
                if (it.feedFooter.type == Type.LOADING) {
                    builder.child(
                            SingleComponentSection.create(c)
                                    .component(LoadingFooterComponent.create(c).build())
                    )
                } else if (it.feedFooter.type == Type.ERROR) {
                    builder.child(
                            SingleComponentSection
                                    .create(c)
                                    .component(
                                            ErrorFooterComponent.create(c)
                                                    .clickHandler(HomeFeedSection.onClick(c))
                                                    .build()
                                    )
                    )
                }
            }
        }

        return builder.build()
    }

    @OnEvent(ClickEvent::class)
    fun onClick(c: SectionContext, @Prop store: Store<HomeState>) {
        store.dispatch(HomeAction.LOAD_MORE)
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
        if (totalCount - lastVisible < threshold && store.getState().loadMoreError == null) {
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