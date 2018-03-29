package com.ctech.eaty.ui.home.component

import com.ctech.eaty.R
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.widget.Image
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaJustify

@LayoutSpec
object ErrorFooterComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext): Component =
            Column.create(c)
                    .justifyContent(YogaJustify.CENTER)
                    .alignItems(YogaAlign.CENTER)
                    .child(
                            Image.create(c)
                                    .drawableRes(R.drawable.ic_network_error_footer)
                                    .heightRes(R.dimen.network_error_footer_size)
                                    .widthRes(R.dimen.network_error_footer_size)
                    )
                    .child(
                            Text.create(c, 0, R.style.Text_Body)
                                    .text(c.getString(R.string.cannot_load))
                    )
                    .child(
                            Row.create(c)
                                    .marginRes(YogaEdge.TOP, R.dimen.space_small)
                                    .marginRes(YogaEdge.BOTTOM, R.dimen.content_padding_vertical)
                                    .child(
                                            Text.create(c, 0, R.style.Text_InlineAction)
                                                    .text(c.getString(R.string.retry))
                                    )
                                    .child(
                                            Image.create(c)
                                                    .drawableRes(R.drawable.ic_retry)
                                                    .marginRes(YogaEdge.START, R.dimen.space_small)
                                    )

                    )
                    .build()

}