package com.ctech.eaty.ui.home.component.job

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.text.Layout
import com.ctech.eaty.R
import com.ctech.eaty.entity.Job
import com.ctech.eaty.ui.web.WebviewFallback
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.ctech.eaty.widget.drawable.CircleProgressBarDrawable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.fresco.FrescoImage
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaJustify


@LayoutSpec
object JobComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop viewModel: Job): ComponentLayout {

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
                                .actualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                                .heightRes(R.dimen.feed_job_size)
                                .widthRes(R.dimen.feed_job_size)

                )
                .child(
                        Column.create(c)
                                .child(
                                        Text.create(c, 0, R.style.Text_Title3)
                                                .text(viewModel.companyName)
                                                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                                                .isSingleLine(true)
                                )
                                .child(
                                        Text.create(c, 0, R.style.Text_Body2)
                                                .text(viewModel.title)
                                                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                                                .isSingleLine(true)
                                                .paddingRes(YogaEdge.TOP, R.dimen.space_small)

                                )
                                .justifyContent(YogaJustify.CENTER)
                                .alignItems(YogaAlign.CENTER)
                                .paddingRes(YogaEdge.LEFT, R.dimen.space_medium)
                                .paddingRes(YogaEdge.RIGHT, R.dimen.space_medium)
                )
                .clickHandler(JobComponent.onClick(c))
                .build()
    }

    @OnEvent(ClickEvent::class)
    fun onClick(
            context: ComponentContext,
            @Prop viewModel: Job) {

        val intentBuilder = CustomTabsIntent.Builder(null)
        intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
        intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
        CustomTabActivityHelper.openCustomTab(context, intentBuilder.build(), Uri.parse(viewModel.url), WebviewFallback())
    }
}