package com.ctech.eaty.ui.home.component.daily

import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaJustify

@LayoutSpec
object FooterSocialComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel): ComponentLayout {
        val resources = c.resources

        return Row.create(c)
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
    }

}