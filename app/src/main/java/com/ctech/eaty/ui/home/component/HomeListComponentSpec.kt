package com.ctech.eaty.ui.home.component;

import com.ctech.eaty.R
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.model.HomeFeed
import com.ctech.eaty.ui.home.state.HomeState
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.sections.widget.RecyclerCollectionEventsController


@LayoutSpec
object HomeListComponentSpec {

    private val MAIN_SCREEN = "main_screen"

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext,
                       @Prop dataSource: List<HomeFeed>,
                       @Prop store: Store<HomeState>,
                       @Prop eventsController: RecyclerCollectionEventsController): ComponentLayout {
        return RecyclerCollectionComponent.create(c)
                .section(
                        HomeFeedSection.create(SectionContext(c))
                                .feeds(dataSource)
                                .store(store)
                                .build())
                .eventsController(eventsController)
                .refreshProgressBarColorRes(R.color.colorAccent)
                .disablePTR(false)
                .testKey(MAIN_SCREEN)
                .buildWithLayout()
    }

}