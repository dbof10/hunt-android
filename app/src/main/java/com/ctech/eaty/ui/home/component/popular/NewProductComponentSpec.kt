package com.ctech.eaty.ui.home.component.popular

import com.ctech.eaty.R
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.component.DataSaverComponent
import com.ctech.eaty.ui.home.component.daily.DailyProductBodyComponent
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.ctech.eaty.widget.drawable.CircleProgressBarDrawable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.fresco.FrescoImage
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaJustify

@LayoutSpec
object NewProductComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext,
                       @Prop viewModel: ProductItemViewModel,
                       @Prop store: Store<HomeState>): ComponentLayout {

        val resources = c.resources
        val height = c.resources.getDimensionPixelSize(R.dimen.feed_horizontal_product_height)
        val controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(ResizeImageUrlProvider.overrideUrl(viewModel.imageUrl, c.resources.getDimensionPixelSize(R.dimen.thumbnail_preview_size))))
                .setImageRequest(ImageRequest.fromUri(ResizeImageUrlProvider.overrideUrl(viewModel.imageUrl, height / 2)))
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .build()


        val body = if (viewModel.saveMode) {
            DataSaverComponent.create(c)
                    .store(store)
        } else {
            FrescoImage
                    .create(c)
                    .progressBarImage(CircleProgressBarDrawable(c))
                    .backgroundRes(R.color.white_100)
                    .controller(controller)
                    .actualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                    .heightPx(height)
        }

        return Column.create(c)
                .child(
                        Text.create(c, 0, R.style.Text_Body)
                                .text(viewModel.tagline)
                                .paddingRes(YogaEdge.BOTTOM, R.dimen.content_padding_vertical)
                                .paddingRes(YogaEdge.TOP, R.dimen.content_padding_vertical)
                                .paddingRes(YogaEdge.LEFT, R.dimen.content_padding_horizontal)
                                .paddingRes(YogaEdge.RIGHT, R.dimen.content_padding_horizontal)
                                .backgroundRes(R.color.white_100)
                                .minLines(2)
                                .maxLines(2)
                )
                .child(
                        body
                )
                .child(
                        Row.create(c)
                                .child(
                                        Text.create(c, 0, R.style.Text_Body)
                                                .text(resources.getQuantityString(R.plurals.like, viewModel.votesCount, viewModel.votesCount))
                                                .marginRes(YogaEdge.START, R.dimen.space_small)
                                )
                                .child(
                                        Text.create(c, 0, R.style.Text_Body)
                                                .text("•")
                                                .marginRes(YogaEdge.START, R.dimen.space_small)
                                                .marginRes(YogaEdge.END, R.dimen.space_small)

                                )
                                .child(
                                        Text.create(c, 0, R.style.Text_Body)
                                                .text(resources.getQuantityString(R.plurals.comment, viewModel.commentsCount, viewModel.commentsCount))
                                )
                                .flex(1F)
                                .paddingRes(YogaEdge.TOP, R.dimen.content_padding_vertical)
                                .paddingRes(YogaEdge.BOTTOM, R.dimen.content_padding_vertical)
                                .justifyContent(YogaJustify.FLEX_START)
                                .build()
                )
                .widthRes(R.dimen.feed_horizontal_product_width)
                .clickHandler(DailyProductBodyComponent.onClick(c))
                .build()

    }

}