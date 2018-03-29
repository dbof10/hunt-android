package com.ctech.eaty.ui.home.component.upcoming

import android.graphics.Color
import android.widget.LinearLayout
import com.ctech.eaty.R
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.component.StickyLabelComponent
import com.ctech.eaty.ui.home.model.UpcomingProducts
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.UpcomingProductItemProps
import com.ctech.eaty.widget.recyclerview.HorizontalSpaceItemDecoration2
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.FromEvent
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.common.OnCheckIsSameContentEvent
import com.facebook.litho.sections.common.OnCheckIsSameItemEvent
import com.facebook.litho.sections.common.RenderEvent
import com.facebook.litho.sections.widget.ListRecyclerConfiguration
import com.facebook.litho.sections.widget.ListRecyclerConfiguration.SNAP_TO_CENTER
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.sections.widget.SectionBinderTarget
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.RenderInfo
import com.facebook.litho.widget.SolidColor
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge


@LayoutSpec
object UpcomingProductsComponentSpec {

    private val LIST_CONFIGURATION: ListRecyclerConfiguration<SectionBinderTarget> =
            ListRecyclerConfiguration(LinearLayout.HORIZONTAL, false, SNAP_TO_CENTER)

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop products: UpcomingProducts): Component {
        return Column.create(c)
                .child(
                        Text.create(c, 0, R.style.Text_Body2)
                                .text(products.label)
                                .paddingRes(YogaEdge.TOP, R.dimen.content_padding_vertical)
                                .paddingRes(YogaEdge.LEFT, R.dimen.content_padding_horizontal)
                                .paddingRes(YogaEdge.RIGHT, R.dimen.content_padding_horizontal)
                                .paddingRes(YogaEdge.BOTTOM, R.dimen.content_padding_vertical)

                )
                .child(

                        RecyclerCollectionComponent.create(c)
                                .disablePTR(true)
                                .recyclerConfiguration(LIST_CONFIGURATION)
                                .section(
                                        DataDiffSection.create<UpcomingProductItemProps>(SectionContext(c))
                                                .data(products.products)
                                                .renderEventHandler(UpcomingProductsComponent.render(c))
                                                .onCheckIsSameItemEventHandler(UpcomingProductsComponent.isSameItem(c))
                                                .onCheckIsSameContentEventHandler(UpcomingProductsComponent.isSameContent(c))
                                                .build()
                                )
                                .itemDecoration(HorizontalSpaceItemDecoration2(c.resources.getDimensionPixelOffset(R.dimen.divider_horizontal_space)))
                                .canMeasureRecycler(true)
                )
                .child(
                        SolidColor.create(c)
                                .color(Color.TRANSPARENT)
                                .heightRes(R.dimen.divider_space)
                )
                .build()
    }

    @OnEvent(RenderEvent::class)
    fun render(
            context: ComponentContext, @FromEvent model: UpcomingProductItemProps, @Prop store: Store<HomeState>): RenderInfo {
        return ComponentRenderInfo.create().component(
                UpcomingProductComponent.create(context)
                        .viewModel(model)
                        .store(store)
                        .key(model.id)
                        .build())
                .build()
    }


    @OnEvent(OnCheckIsSameItemEvent::class)
    fun isSameItem(
            c: ComponentContext,
            @FromEvent previousItem: UpcomingProductItemProps,
            @FromEvent nextItem: UpcomingProductItemProps): Boolean {
        return previousItem.id == nextItem.id
    }

    @OnEvent(OnCheckIsSameContentEvent::class)
    fun isSameContent(
            c: ComponentContext,
            @FromEvent previousItem: UpcomingProductItemProps,
            @FromEvent nextItem: UpcomingProductItemProps): Boolean {
        return previousItem == nextItem
    }
}