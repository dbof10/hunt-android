package com.ctech.eaty.ui.home.component.topic

import android.text.Layout
import com.ctech.eaty.R
import com.ctech.eaty.entity.Topic
import com.ctech.eaty.ui.topicdetail.view.TopicDetailActivity
import com.ctech.eaty.util.ResizeImageUrlProvider
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
import com.facebook.yoga.YogaEdge

@LayoutSpec
object TopicComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop viewModel: Topic): ComponentLayout {
        val height = c.resources.getDimensionPixelSize(R.dimen.feed_horizontal_product_height)
        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(ResizeImageUrlProvider.overrideUrl(viewModel.imageUrl, height / 2)))
                .build()

        return Column.create(c)
                .backgroundRes(R.color.white_100)
                .child(

                        FrescoImage
                                .create(c)
                                .progressBarImage(CircleProgressBarDrawable(c))
                                .controller(controller)
                                .actualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                                .heightRes(R.dimen.feed_topic_height)
                )
                .child(
                        Text.create(c, 0, R.style.Text_Body2)
                                .text(viewModel.name)
                                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                                .isSingleLine(true)
                                .paddingRes(YogaEdge.TOP, R.dimen.content_padding_vertical)
                                .paddingRes(YogaEdge.LEFT, R.dimen.space_medium)
                                .paddingRes(YogaEdge.RIGHT, R.dimen.space_medium)
                )
                .paddingRes(YogaEdge.BOTTOM, R.dimen.content_padding_vertical)
                .widthRes(R.dimen.feed_topic_width)
                .clickHandler(TopicComponent.onClick(c))
                .build()
    }

    @OnEvent(ClickEvent::class)
    fun onClick(
            c: ComponentContext,
            @Prop viewModel: Topic) {

        val intent = TopicDetailActivity.newIntent(c, viewModel)
        c.startActivity(intent)
    }
}