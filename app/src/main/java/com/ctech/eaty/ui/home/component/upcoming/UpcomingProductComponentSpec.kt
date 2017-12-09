package com.ctech.eaty.ui.home.component.upcoming

import android.text.TextUtils
import com.ctech.eaty.R
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.component.DataSaverComponent
import com.ctech.eaty.ui.home.component.daily.DailyProductBodyComponent
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.UpcomingProductItemProps
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.ctech.eaty.util.ViewUtils
import com.ctech.eaty.widget.drawable.CircleProgressBarDrawable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.fresco.FrescoImage
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaJustify
import com.facebook.yoga.YogaPositionType


@LayoutSpec
object UpcomingProductComponentSpec {


    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop viewModel: UpcomingProductItemProps, @Prop store: Store<HomeState>): ComponentLayout {
        val backgroundSize = c.resources.getDimensionPixelSize(R.dimen.upcoming_product_height)
        val foregroundSize = c.resources.getDimensionPixelSize(R.dimen.upcoming_foreground_product_size)

        val thumbnailSize = c.resources.getDimensionPixelSize(R.dimen.thumbnail_preview_size)

        val controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(
                        ResizeImageUrlProvider.overrideUrl(viewModel.backgroundUrl, thumbnailSize)))
                .setImageRequest(ImageRequest.fromUri(ResizeImageUrlProvider.overrideUrl(viewModel.backgroundUrl, backgroundSize / 2)))
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .build()

        val foregroundController = Fresco.newDraweeControllerBuilder()
                .setUri(ResizeImageUrlProvider.overrideUrl(viewModel.foregroundUrl, foregroundSize / 2))
                .build()

        val body = if (viewModel.saveMode) {
            DataSaverComponent.create(c)
                    .store(store)
                    .buildWithLayout()
        } else {
            Column.create(c)
                    .child(
                            FrescoImage
                                    .create(c)
                                    .progressBarImage(CircleProgressBarDrawable(c))
                                    .controller(controller)
                                    .actualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                                    .widthPx(ViewUtils.getScreenWidth(c) * 2 / 3)
                                    .heightPx(backgroundSize)
                    )
                    .child(
                            FrescoImage
                                    .create(c)
                                    .controller(foregroundController)
                                    .heightPx(foregroundSize)
                                    .actualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                                    .positionType(YogaPositionType.ABSOLUTE)
                    )
                    .alignItems(YogaAlign.CENTER)
                    .justifyContent(YogaJustify.CENTER)
                    .build()

        }

        return Column.create(c)

                .child(
                        body
                )
                .child(
                        Text.create(c, 0, R.style.Text_Body)
                                .text(viewModel.name)
                                .paddingRes(YogaEdge.TOP, R.dimen.content_padding_vertical)
                                .paddingRes(YogaEdge.LEFT, R.dimen.content_padding_horizontal)
                                .paddingRes(YogaEdge.RIGHT, R.dimen.content_padding_horizontal)
                                .minLines(1)
                                .maxLines(1)
                                .backgroundRes(R.color.white_100)
                )
                .child(
                        Text.create(c, 0, R.style.Text_Body3)
                                .text(viewModel.tagline)
                                .paddingRes(YogaEdge.TOP, R.dimen.space_small)
                                .paddingRes(YogaEdge.BOTTOM, R.dimen.content_padding_vertical)
                                .paddingRes(YogaEdge.LEFT, R.dimen.content_padding_horizontal)
                                .paddingRes(YogaEdge.RIGHT, R.dimen.content_padding_horizontal)
                                .minLines(2)
                                .maxLines(2)
                                .ellipsize(TextUtils.TruncateAt.END)
                                .backgroundRes(R.color.white_100)
                )

                .widthPx(ViewUtils.getScreenWidth(c) * 2 / 3)
                .clickHandler(DailyProductBodyComponent.onClick(c))
                .build()
    }


    @OnEvent(ClickEvent::class)
    fun onClick(
            c: ComponentContext,
            @Prop viewModel: UpcomingProductItemProps) {

    }
}