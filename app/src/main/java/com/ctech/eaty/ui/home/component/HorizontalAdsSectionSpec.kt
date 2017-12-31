package com.ctech.eaty.ui.home.component

import com.ctech.eaty.ui.home.view.HorizontalAdsView
import com.ctech.eaty.ui.home.viewmodel.HorizontalAdsItemViewModel
import com.facebook.ads.NativeAdsManager
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
import com.facebook.litho.viewcompat.SimpleViewBinder
import com.facebook.litho.widget.RenderInfo
import com.facebook.litho.widget.ViewRenderInfo


@GroupSectionSpec
object HorizontalAdsSectionSpec {


    @OnCreateChildren
    fun onCreateChildren(
            c: SectionContext,
            @Prop dataSource: List<HorizontalAdsItemViewModel>): Children {
        return Children.create()
                .child(
                        DataDiffSection.create<HorizontalAdsItemViewModel>(c)
                                .data(dataSource)
                                .renderEventHandler(HorizontalAdsSection.render(c))
                                .onCheckIsSameItemEventHandler(HorizontalAdsSection.isSameItem(c))
                                .onCheckIsSameContentEventHandler(HorizontalAdsSection.isSameContent(c))
                )
                .build()
    }


    @OnEvent(RenderEvent::class)
    fun render(
            c: SectionContext,
            @FromEvent model: HorizontalAdsItemViewModel): RenderInfo {
        return ViewRenderInfo.create()
                .viewCreator { context ->
                    return@viewCreator HorizontalAdsView(context)
                }
                .viewBinder(object : SimpleViewBinder<HorizontalAdsView>() {
                    override fun bind(adsView: HorizontalAdsView) {
                        val adsManager = NativeAdsManager(adsView.context, model.adId, 5)
                        adsView.bind(adsManager)
                    }

                    override fun unbind(view: HorizontalAdsView) {
                        view.unbind()
                    }
                })
                .build()
    }

    @OnEvent(OnCheckIsSameItemEvent::class)
    fun isSameItem(
            c: SectionContext,
            @FromEvent previousItem: HorizontalAdsItemViewModel,
            @FromEvent nextItem: HorizontalAdsItemViewModel): Boolean {
        return previousItem.id == nextItem.id
    }

    @OnEvent(OnCheckIsSameContentEvent::class)
    fun isSameContent(
            c: SectionContext,
            @FromEvent previousItem: HorizontalAdsItemViewModel,
            @FromEvent nextItem: HorizontalAdsItemViewModel): Boolean {
        return previousItem == nextItem
    }
}