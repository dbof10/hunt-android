package com.ctech.eaty.ui.home.component.daily

import android.graphics.Color
import com.ctech.eaty.R
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.SolidColor


@LayoutSpec
object ProductComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel,
                       @Prop store: Store<HomeState>): ComponentLayout {
        return Column.create(c)
                .child(HeaderComponent.create(c)
                        .viewModel(viewModel))
                .child(
                        DailyProductBodyComponent.create(c)
                                .store(store)
                                .viewModel(viewModel)
                )
                .child(
                        FooterComponent.create(c)
                                .viewModel(viewModel)
                )
                .child(
                        SolidColor.create(c)
                                .color(Color.TRANSPARENT)
                                .heightRes(R.dimen.divider_space)
                )
                .build()
    }

}