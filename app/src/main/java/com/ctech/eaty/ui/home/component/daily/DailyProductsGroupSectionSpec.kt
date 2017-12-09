package com.ctech.eaty.ui.home.component.daily

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.component.StickyLabelComponent
import com.ctech.eaty.ui.home.model.DailyProducts
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.facebook.litho.annotations.FromEvent
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.common.OnCheckIsSameContentEvent
import com.facebook.litho.sections.common.OnCheckIsSameItemEvent
import com.facebook.litho.sections.common.RenderEvent
import com.facebook.litho.sections.common.SingleComponentSection
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.RenderInfo


@GroupSectionSpec
object DailyProductsGroupSectionSpec {


    @OnCreateChildren
    fun onCreateChildren(c: SectionContext, @Prop products: DailyProducts): Children {
        val builder = Children.create()
                .child(
                        SingleComponentSection.create(c)
                                .component(
                                        StickyLabelComponent.create(c)
                                                .viewModel(products.sticky)
                                                .build())
                                .sticky(true))
                .child(
                        DataDiffSection.create<ProductItemViewModel>(c)
                                .data(products.products)
                                .renderEventHandler(
                                        DailyProductsGroupSection.render(c))
                                .onCheckIsSameItemEventHandler(
                                        DailyProductsGroupSection.isSameItem(c))
                                .onCheckIsSameContentEventHandler(DailyProductsGroupSection.isSameContent(c))
                )
        return builder.build()
    }

    @OnEvent(RenderEvent::class)
    fun render(
            context: SectionContext, @FromEvent model: ProductItemViewModel, @Prop store: Store<HomeState>): RenderInfo {
        return ComponentRenderInfo.create().component(
                ProductComponent.create(context)
                        .viewModel(model)
                        .store(store)
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