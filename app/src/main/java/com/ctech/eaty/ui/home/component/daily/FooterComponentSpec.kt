package com.ctech.eaty.ui.home.component.daily

import android.support.v4.content.ContextCompat
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.SolidColor
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaJustify

@LayoutSpec
object FooterComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel): Component {
        val resources = c.resources
        val interaction = Row.create(c)
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

        val builder = Column.create(c)

        if (viewModel.commentsCount != -1 && viewModel.votesCount != -1) {
            builder.child(interaction)
        }

        builder.backgroundColor(ContextCompat.getColor(c, R.color.white_100))
                .child(
                        SolidColor.create(c)
                                .colorRes(R.color.divider_color)
                                .flex(1F)
                                .heightRes(R.dimen.divider_height)
                )
                .child(
                        Row.create(c)
                                .child(
                                        FooterActionComponent.create(c)
                                                .viewModel(viewModel)
                                                .actionLabelResId(R.string.like)
                                                .actionResId(R.drawable.ic_like)
                                )
                                .child(
                                        FooterActionComponent.create(c)
                                                .viewModel(viewModel)
                                                .actionLabelResId(R.string.comment)
                                                .actionResId(R.drawable.ic_comment)
                                )
                                .child(
                                        FooterActionComponent.create(c)
                                                .viewModel(viewModel)
                                                .actionLabelResId(R.string.share)
                                                .actionResId(R.drawable.ic_share_home)
                                )
                                .marginRes(YogaEdge.BOTTOM, R.dimen.content_padding_vertical)
                                .marginRes(YogaEdge.TOP, R.dimen.content_padding_vertical)
                )
                .child(
                        SolidColor.create(c)
                                .colorRes(R.color.divider_color)
                                .flex(1F)
                                .heightRes(R.dimen.divider_height))
                .paddingRes(YogaEdge.START, R.dimen.content_padding_horizontal)
                .paddingRes(YogaEdge.END, R.dimen.content_padding_horizontal)
                .build()
        return builder.build()
    }


}