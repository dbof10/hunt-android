package com.ctech.eaty.ui.home.component

import android.support.v4.content.ContextCompat
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
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
import com.facebook.litho.widget.VerticalGravity
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaPositionType

@LayoutSpec
class BodyComponentSpec {

    companion object {


        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel): ComponentLayout {
            val height = c.resources.getDimensionPixelSize(R.dimen.feed_product_height)
            val controller = Fresco.newDraweeControllerBuilder()
                    .setUri(ResizeImageUrlProvider.overrideUrl(viewModel.imageUrl, height / 2))
                    .setAutoPlayAnimations(true)
                    .build()

            return Column.create(c)
                    .child(
                            Text.create(c, 0, R.style.TextAppearance_Body)
                                    .text(viewModel.tagline)
                                    .paddingRes(YogaEdge.BOTTOM, R.dimen.content_padding_vertical)
                                    .paddingRes(YogaEdge.TOP, R.dimen.content_padding_vertical)
                                    .paddingRes(YogaEdge.LEFT, R.dimen.content_padding_horizontal)
                                    .paddingRes(YogaEdge.RIGHT, R.dimen.content_padding_horizontal)
                                    .backgroundRes(R.color.white_100)
                    )
                    .child(
                            FrescoImage
                                    .create(c)
                                    .controller(controller)
                                    .actualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                                    .heightPx(height)
                    )
                    .clickHandler(BodyComponent.onClick(c))
                    .build()
        }

        @JvmStatic
        @OnEvent(ClickEvent::class)
        fun onClick(
                c: ComponentContext,
                @Prop viewModel: ProductItemViewModel) {

            val intent = ProductDetailActivity.newIntent(c, viewModel.id)
            c.startActivity(intent)
        }
    }
}