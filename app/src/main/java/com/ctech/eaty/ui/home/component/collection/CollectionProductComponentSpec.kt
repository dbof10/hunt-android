package com.ctech.eaty.ui.home.component.collection

import android.text.Layout
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.ctech.eaty.widget.drawable.CircleProgressBarDrawable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.fresco.FrescoImage
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaJustify

@LayoutSpec
object CollectionProductComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel): Component {

        val height = c.resources.getDimensionPixelSize(R.dimen.feed_job_size)
        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(ResizeImageUrlProvider.overrideUrl(viewModel.imageUrl, height / 2)))
                .build()

        return Row.create(c)
                .backgroundRes(R.color.white_100)
                .child(

                        FrescoImage
                                .create(c)
                                .progressBarImage(CircleProgressBarDrawable(c))
                                .controller(controller)
                                .actualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                                .heightRes(R.dimen.collection_product_height)
                                .widthRes(R.dimen.collection_product_width)
                )
                .child(
                        Column.create(c)
                                .child(
                                        Text.create(c, 0, R.style.Text_Title3)
                                                .text(viewModel.name)
                                                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                                                .maxLines(2)
                                                .minLines(2)
                                )
                                .child(
                                        Text.create(c, 0, R.style.Text_Body2)
                                                .text(viewModel.tagline)
                                                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                                                .maxLines(2)
                                                .minLines(2)

                                )
                                .justifyContent(YogaJustify.CENTER)
                                .paddingRes(YogaEdge.LEFT, R.dimen.space_medium)
                                .paddingRes(YogaEdge.RIGHT, R.dimen.space_medium)
                                .maxWidthRes(R.dimen.upcoming_product_width)
                )
                .clickHandler(CollectionProductComponent.onClick(c))
                .build()
    }


    @OnEvent(ClickEvent::class)
    fun onClick(
            c: ComponentContext,
            @Prop viewModel: ProductItemViewModel) {

        val intent = ProductDetailActivity.newIntent(c, viewModel.id)
        c.startActivity(intent)
    }
}