package com.ctech.eaty.ui.home.component.popular

import com.ctech.eaty.R
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.component.daily.FooterSocialComponent
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.yoga.YogaEdge

@LayoutSpec
object NewProductComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext,
                       @Prop viewModel: ProductItemViewModel,
                       @Prop store: Store<HomeState>): ComponentLayout {
        val footer = FooterSocialComponent.create(c)
                .viewModel(viewModel)
                .backgroundRes(R.color.white_100)
                .paddingRes(YogaEdge.START, R.dimen.content_padding_horizontal)
                .paddingRes(YogaEdge.END, R.dimen.content_padding_horizontal)

        val builder = Column.create(c)
                .child(
                        NewProductBodyComponent.create(c)
                                .store(store)
                                .viewModel(viewModel)
                )

        if (viewModel.commentsCount != -1 && viewModel.votesCount != -1) {
            builder.child(footer)
        }
        return builder.build()
    }

}