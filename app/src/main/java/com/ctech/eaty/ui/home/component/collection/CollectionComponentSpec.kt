package com.ctech.eaty.ui.home.component.collection

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.Layout
import android.widget.LinearLayout
import com.ctech.eaty.R
import com.ctech.eaty.ui.collectiondetail.view.CollectionDetailActivity
import com.ctech.eaty.ui.home.model.SuggestedCollection
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.ctech.eaty.widget.drawable.CircleProgressBarDrawable
import com.ctech.eaty.widget.recyclerview.HorizontalSpaceItemDecoration2
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.FromEvent
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.fresco.FrescoImage
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
import com.facebook.yoga.YogaPositionType

@LayoutSpec
object CollectionComponentSpec {

    private val LIST_CONFIGURATION: ListRecyclerConfiguration<SectionBinderTarget> =
            ListRecyclerConfiguration(LinearLayout.HORIZONTAL, false, ListRecyclerConfiguration.SNAP_NONE)

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop collection: SuggestedCollection): Component {

        val width = c.resources.getDimensionPixelSize(R.dimen.upcoming_product_width)
        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(ResizeImageUrlProvider.overrideUrl(collection.imageUrl, width / 2)))
                .build()

        return Column.create(c)
                .child(
                        Column.create(c)
                                .child(
                                        FrescoImage
                                                .create(c)
                                                .progressBarImage(CircleProgressBarDrawable(c))
                                                .controller(controller)
                                                .actualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                                                .foregroundRes(R.color.black_60)
                                                .heightRes(R.dimen.feed_job_size)
                                                .clickHandler(CollectionComponent.onClick(c))
                                )
                                .child(
                                        Text.create(c, 0, R.style.Text_Title2)
                                                .text(collection.name)
                                                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                                                .positionType(YogaPositionType.ABSOLUTE)
                                                .positionRes(YogaEdge.TOP, R.dimen.space_medium)
                                                .textColor(ContextCompat.getColor(c, R.color.white_100))
                                                .positionRes(YogaEdge.LEFT, R.dimen.content_padding_horizontal)
                                                .positionRes(YogaEdge.RIGHT, R.dimen.content_padding_horizontal)
                                )
                                .child(
                                        Text.create(c, 0, R.style.Text_Body)
                                                .text(collection.title)
                                                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                                                .positionType(YogaPositionType.ABSOLUTE)
                                                .positionRes(YogaEdge.TOP, R.dimen.space_xlarge)
                                                .textColor(ContextCompat.getColor(c, R.color.white_100))
                                                .positionRes(YogaEdge.LEFT, R.dimen.content_padding_horizontal)
                                                .positionRes(YogaEdge.RIGHT, R.dimen.content_padding_horizontal)
                                )
                )
                .child(
                        RecyclerCollectionComponent.create(c)
                                .disablePTR(true)
                                .recyclerConfiguration(LIST_CONFIGURATION)
                                .section(
                                        DataDiffSection.create<ProductItemViewModel>(SectionContext(c))
                                                .data(collection.products)
                                                .renderEventHandler(
                                                        CollectionComponent.render(c))
                                                .onCheckIsSameItemEventHandler(
                                                        CollectionComponent.isSameItem(SectionContext(c)))
                                                .onCheckIsSameContentEventHandler(CollectionComponent.isSameContent(SectionContext(c)))
                                )
                                .itemDecoration(HorizontalSpaceItemDecoration2(c.resources.getDimensionPixelOffset(R.dimen.divider_horizontal_space)))
                                .canMeasureRecycler(true)
                                .marginRes(YogaEdge.TOP, R.dimen.space_medium)
                )
                .child(
                        SolidColor.create(c)
                                .color(Color.TRANSPARENT)
                                .heightRes(R.dimen.divider_space)
                )
                .build()
    }

    @OnEvent(ClickEvent::class)
    fun onClick(
            c: ComponentContext,
            @Prop collection: SuggestedCollection) {

        val intent = CollectionDetailActivity.newIntent(c, collection.id.toInt())
        c.startActivity(intent)
    }

    @OnEvent(RenderEvent::class)
    fun render(
            context: ComponentContext, @FromEvent model: ProductItemViewModel): RenderInfo {
        return ComponentRenderInfo.create()
                .component(
                        CollectionProductComponent.create(context)
                                .viewModel(model)
                                .build()
                )
                .build()
    }

    @OnEvent(OnCheckIsSameItemEvent::class)
    fun isSameItem(
            c: ComponentContext,
            @FromEvent previousItem: ProductItemViewModel,
            @FromEvent nextItem: ProductItemViewModel): Boolean {
        return previousItem.id == nextItem.id
    }

    @OnEvent(OnCheckIsSameContentEvent::class)
    fun isSameContent(
            c: ComponentContext,
            @FromEvent previousItem: ProductItemViewModel,
            @FromEvent nextItem: ProductItemViewModel): Boolean {
        return previousItem == nextItem
    }

}
