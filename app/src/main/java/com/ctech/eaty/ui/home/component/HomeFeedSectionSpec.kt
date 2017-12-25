package com.ctech.eaty.ui.home.component

import android.util.Log
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.component.daily.DailyProductsGroupSection
import com.ctech.eaty.ui.home.component.upcoming.UpcomingProductsGroupSection
import com.ctech.eaty.ui.home.model.DailyProducts
import com.ctech.eaty.ui.home.model.FeedFooter
import com.ctech.eaty.ui.home.model.HomeFeed
import com.ctech.eaty.ui.home.model.Type
import com.ctech.eaty.ui.home.model.UpcomingProducts
import com.ctech.eaty.ui.home.state.HomeState
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

    private val KEY_FOOTER_LOADING = "loading"
    private val KEY_FOOTER_ERROR = "error"

    @OnCreateChildren
    fun onCreateChildren(c: SectionContext,
                         @Prop feeds: List<HomeFeed>, @Prop store: Store<HomeState>): Children {
        val builder = Children.create()
        feeds.forEach {
            if (it is DailyProducts) {
                builder.child(
                        DailyProductsGroupSection.create(c)
                                .products(it)
                                .store(store)
                                .key(it.label)
                                .build()
                )
                if (it.horizontalAds.isNotEmpty()) {
                    builder.child(
                            HorizontalAdsSection.create(c)
                                    .dataSource(it.horizontalAds)
                                    .key(it.horizontalAds.first().adId)
                    )
                }
            } else if (it is UpcomingProducts) {
                builder.child(
                        UpcomingProductsGroupSection.create(c)
                                .products(it)
                                .store(store)
                                .key(it.label)
                                .build()
                )
            } else if (it is FeedFooter) {
                if (it.type == Type.LOADING) {
                    builder.child(
                            SingleComponentSection.create(c)
                                    .component(LoadingFooterComponent.create(c).build())
                                    .key(KEY_FOOTER_LOADING)
                    )
                } else if (it.type == Type.ERROR) {
                    builder.child(
                            SingleComponentSection
                                    .create(c)
                                    .component(
                                            ErrorFooterComponent.create(c)
                                                    .clickHandler(HomeFeedSection.onClick(c))
                                                    .build()
                                    )
                                    .key(KEY_FOOTER_ERROR)
                    )
                }
            }
        }

        return builder.build()
    }

    @OnEvent(ClickEvent::class)
    fun onClick(c: SectionContext, @Prop store: Store<HomeState>) {
        val state = store.getState()
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
        val state = store.getState()
        if (totalCount - lastVisible < threshold && state.loadMoreError == null) {
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