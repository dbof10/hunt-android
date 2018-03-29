package com.ctech.eaty.ui.home.component.daily

import android.graphics.Color
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.user.view.UserActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.litho.ClickEvent
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.fresco.FrescoImage
import com.facebook.litho.widget.Card
import com.facebook.litho.widget.Text
import com.facebook.litho.widget.VerticalGravity
import com.facebook.yoga.YogaEdge

@LayoutSpec
object HeaderComponentSpec {


    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel): Component {
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(viewModel.userImageUrl)
                .build()
        return Row.create(c)
                .child(
                        Card.create(c)
                                .cornerRadiusPx(c.resources.getDimensionPixelSize(R.dimen.avatar_size) / 2F)
                                .content(
                                        FrescoImage
                                                .create(c)
                                                .controller(controller)
                                                .actualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                                                .heightRes(R.dimen.avatar_size)
                                                .widthRes(R.dimen.avatar_size)
                                )

                                .marginRes(YogaEdge.TOP, R.dimen.home_header_margin_top)
                                .marginRes(YogaEdge.BOTTOM, R.dimen.home_header_margin_bottom)
                                .marginRes(YogaEdge.START, R.dimen.content_padding_horizontal)

                )
                .child(
                        Text
                                .create(c, 0, R.style.Text_Title3)
                                .text(viewModel.userName)
                                .verticalGravity(VerticalGravity.CENTER)
                                .marginRes(YogaEdge.START, R.dimen.space_medium)
                                .clickHandler(HeaderComponent.onClick(c))

                )
                .backgroundColor(Color.WHITE)
                .build()
    }

    @OnEvent(ClickEvent::class)
    fun onClick(
            c: ComponentContext,
            @Prop viewModel: ProductItemViewModel) {

        val intent = UserActivity.newIntent(c, viewModel.user)
        c.startActivity(intent)
    }
}