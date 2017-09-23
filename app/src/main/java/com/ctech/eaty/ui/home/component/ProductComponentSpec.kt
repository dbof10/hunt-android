package com.ctech.eaty.ui.home.component

import android.graphics.Color
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.SolidColor


@LayoutSpec
class ProductComponentSpec {

    companion object {

        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel): ComponentLayout {
            return Column.create(c)
                    .child(
                            BodyComponent.create(c)
                                    .arg1(viewModel)
                    )
                    .child(
                            FooterComponent.create(c)
                                    .arg1(viewModel)
                    )
                    .child(
                            SolidColor.create(c)
                                    .color(Color.TRANSPARENT)
                                    .withLayout()
                                    .heightPx(c.resources.getDimensionPixelSize(R.dimen.divider_space))
                    )
                    .build()
        }

    }
}