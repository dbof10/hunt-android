package com.ctech.eaty.ui.home.component

import com.ctech.eaty.R
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.widget.Progress
import com.facebook.yoga.YogaJustify

@LayoutSpec
object LoadingFooterComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext) : Component =
            Row.create(c)
                    .justifyContent(YogaJustify.CENTER)
                    .child(
                            Progress.create(c)
                                    .widthRes(R.dimen.progressbar_size)
                                    .heightRes(R.dimen.progressbar_size)
                    )
                    .build()
}