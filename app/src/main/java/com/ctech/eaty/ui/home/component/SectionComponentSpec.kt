package com.ctech.eaty.ui.home.component

import android.graphics.Color
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.SectionViewModel
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge


@LayoutSpec
class SectionComponentSpec {
    companion object {

        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext, @Prop viewModel: SectionViewModel) =
                Column.create(c)
                        .backgroundColor(Color.WHITE)
                        .paddingPx(YogaEdge.BOTTOM, c.resources.getDimensionPixelSize(R.dimen.content_padding_vertical))
                        .paddingPx(YogaEdge.TOP, c.resources.getDimensionPixelSize(R.dimen.content_padding_vertical))
                        .paddingPx(YogaEdge.LEFT, c.resources.getDimensionPixelSize(R.dimen.content_padding_horizontal))
                        .paddingPx(YogaEdge.START, c.resources.getDimensionPixelSize(R.dimen.content_padding_horizontal))
                        .child(
                                Text.create(c, 0, R.style.TextAppearance_Section)
                                        .text(viewModel.date)
                        ).build()

    }
}