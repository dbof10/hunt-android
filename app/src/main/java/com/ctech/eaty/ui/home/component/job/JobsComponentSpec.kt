package com.ctech.eaty.ui.home.component.job

import android.graphics.Color
import android.widget.LinearLayout
import com.ctech.eaty.R
import com.ctech.eaty.entity.Job
import com.ctech.eaty.ui.home.model.Jobs
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
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.sections.widget.SectionBinderTarget
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.RenderInfo
import com.facebook.litho.widget.SolidColor
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge

@LayoutSpec
object JobsComponentSpec {

    private val LIST_CONFIGURATION: ListRecyclerConfiguration<SectionBinderTarget> =
            ListRecyclerConfiguration(LinearLayout.HORIZONTAL, false, ListRecyclerConfiguration.SNAP_NONE)

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop jobs: Jobs): Component {
        return Column.create(c)
                .child(
                        Text.create(c, 0, R.style.Text_Body2)
                                .text(jobs.label)
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
                                        DataDiffSection.create<Job>(SectionContext(c))
                                                .data(jobs.jobs)
                                                .renderEventHandler(
                                                        JobsComponent.render(c))
                                                .onCheckIsSameItemEventHandler(
                                                        JobsComponent.isSameItem(SectionContext(c)))
                                                .onCheckIsSameContentEventHandler(JobsComponent.isSameContent(SectionContext(c)))
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
            context: ComponentContext, @FromEvent model: Job): RenderInfo {
        return ComponentRenderInfo.create()
                .component(
                        JobComponent.create(context)
                                .viewModel(model)
                                .build()
                )
                .build()
    }

    @OnEvent(OnCheckIsSameItemEvent::class)
    fun isSameItem(
            c: ComponentContext,
            @FromEvent previousItem: Job,
            @FromEvent nextItem: Job): Boolean {
        return previousItem.id == nextItem.id
    }

    @OnEvent(OnCheckIsSameContentEvent::class)
    fun isSameContent(
            c: ComponentContext,
            @FromEvent previousItem: Job,
            @FromEvent nextItem: Job): Boolean {
        return previousItem == nextItem
    }

}
