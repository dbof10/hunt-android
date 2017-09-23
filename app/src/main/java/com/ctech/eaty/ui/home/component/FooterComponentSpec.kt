package com.ctech.eaty.ui.home.component

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.ctech.eaty.R
import com.ctech.eaty.ui.comment.view.CommentActivity
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.SolidColor
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge

@LayoutSpec
class FooterComponentSpec {

    companion object {

        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel) =
                Column.create(c)
                        .backgroundColor(Color.WHITE)
                        .child(
                                Text.create(c, 0, R.style.Widget_Hunt_ShotDescription)
                                        .text(viewModel.tagline)
                                        .withLayout()
                                        .paddingPx(YogaEdge.BOTTOM, c.resources.getDimensionPixelSize(R.dimen.content_padding_vertical))
                                        .paddingPx(YogaEdge.TOP, c.resources.getDimensionPixelSize(R.dimen.content_padding_vertical))
                                        .paddingPx(YogaEdge.LEFT, c.resources.getDimensionPixelSize(R.dimen.content_padding_horizontal))
                                        .paddingPx(YogaEdge.RIGHT, c.resources.getDimensionPixelSize(R.dimen.content_padding_horizontal))

                        )
                        .child(
                                SolidColor.create(c)
                                        .color(ContextCompat.getColor(c, R.color.divider_color))
                                        .withLayout()
                                        .flex(1F)
                                        .heightPx(2)
                        )
                        .child(
                                Row.create(c)
                                        .heightPx(c.resources.getDimensionPixelSize(R.dimen.feed_footer_height))
                                        .child(
                                                FooterActionComponentSpec
                                                        .onCreateLayout(c, viewModel, R.drawable.ic_heart_solid_grey)
                                        )
                                        .child(
                                                FooterActionComponentSpec
                                                        .onCreateLayout(c, viewModel, R.drawable.ic_comment)
                                        )
                                        .child(
                                                FooterActionComponentSpec
                                                        .onCreateLayout(c, viewModel, R.drawable.ic_share)
                                        )
                        )


    }


}