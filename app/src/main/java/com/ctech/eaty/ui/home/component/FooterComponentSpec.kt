package com.ctech.eaty.ui.home.component

import android.support.v4.content.ContextCompat
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.SolidColor
import com.facebook.yoga.YogaEdge

@LayoutSpec
object FooterComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel): ComponentLayout {
        return Column.create(c)
                .backgroundColor(ContextCompat.getColor(c, R.color.white_100))
                .child(
                        FooterInfoComponent.create(c)
                                .viewModel(viewModel)
                                .marginRes(YogaEdge.TOP, R.dimen.content_padding_vertical)
                                .marginRes(YogaEdge.BOTTOM, R.dimen.content_padding_vertical)
                )
                .child(
                        SolidColor.create(c)
                                .colorRes(R.color.divider_color)
                                .flex(1F)
                                .heightPx(2)
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


    }


}