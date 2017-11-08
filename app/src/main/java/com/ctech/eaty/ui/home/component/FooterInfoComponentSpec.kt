package com.ctech.eaty.ui.home.component

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
object FooterInfoComponentSpec {


    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel): ComponentLayout {
        val resource = c.resources
        return Row.create(c)
                .child(
                        Text.create(c, 0, R.style.Text_Body)
                                .text(resource.getQuantityString(R.plurals.like, viewModel.votesCount, viewModel.votesCount))
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
                                .text(resource.getQuantityString(R.plurals.comment, viewModel.commentsCount, viewModel.commentsCount))
                                .marginRes(YogaEdge.START, R.dimen.space_small)
                )
                .flex(1F)
                .justifyContent(YogaJustify.FLEX_START)
                .build()

    }


}