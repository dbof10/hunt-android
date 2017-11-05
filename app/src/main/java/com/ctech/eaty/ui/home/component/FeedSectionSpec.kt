package com.ctech.eaty.ui.home.component

import com.ctech.eaty.ui.home.viewmodel.HomeFeed
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.facebook.litho.annotations.FromEvent
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.*
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.RenderInfo


@GroupSectionSpec
object FeedSectionSpec {


    @OnCreateChildren
    fun onCreateChildren(c: SectionContext, @Prop feed: HomeFeed): Children {
        val builder = Children.create()
                .child(
                        SingleComponentSection.create(c)
                                .component(DateComponent.create(c)
                                        .viewModel(feed.date))
                                .sticky(true))
                .child(
                        DataDiffSection.create<ProductItemViewModel>(c)
                                .data(feed.products)
                                .renderEventHandler(
                                        FeedSection.render(c))
                                .onCheckIsSameItemEventHandler(
                                        FeedSection.isSameItem(c))
                                .onCheckIsSameContentEventHandler(FeedSection.isSameContent(c))
                )
        if (feed.horizontalAds.isNotEmpty()) {
            builder.child(
                    HorizontalAdsSection.create(c)
                            .dataSource(feed.horizontalAds)
            )
        }
        return builder.build()
    }

    @OnEvent(RenderEvent::class)
    fun render(
            context: SectionContext, @FromEvent model: ProductItemViewModel): RenderInfo {
        return ComponentRenderInfo.create().component(
                ProductComponent
                        .create(context)
                        .viewModel(model)
                        .key(model.id.toString())
                        .build())
                .build()
    }


    @OnEvent(OnCheckIsSameItemEvent::class)
    fun isSameItem(
            c: SectionContext,
            @FromEvent previousItem: ProductItemViewModel,
            @FromEvent nextItem: ProductItemViewModel): Boolean {
        return previousItem.id == nextItem.id
    }

    @OnEvent(OnCheckIsSameContentEvent::class)
    fun isSameContent(
            c: SectionContext,
            @FromEvent previousItem: ProductItemViewModel,
            @FromEvent nextItem: ProductItemViewModel): Boolean {
        return previousItem == nextItem
    }
}