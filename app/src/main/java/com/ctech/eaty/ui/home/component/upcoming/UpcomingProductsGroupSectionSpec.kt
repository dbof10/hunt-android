package com.ctech.eaty.ui.home.component.upcoming

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.model.UpcomingProducts
import com.ctech.eaty.ui.home.state.HomeState
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.SingleComponentSection

@GroupSectionSpec
object UpcomingProductsGroupSectionSpec {

    private val KEY_LIST = "list"

    @OnCreateChildren
    fun onCreateChildren(c: SectionContext, @Prop products: UpcomingProducts,
                         @Prop store: Store<HomeState>): Children {
        val builder = Children.create()
                .child(
                        SingleComponentSection.create(c)
                                .component(
                                        UpcomingProductsSection.create(c)
                                                .products(products)
                                                .store(store)
                                )
                                .key(KEY_LIST)
                )


        return builder.build()
    }

}