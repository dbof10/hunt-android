package com.ctech.eaty.ui.home.model

import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.home.viewmodel.StickyItemViewModel

abstract class HorizontalProduct(val sticky: StickyItemViewModel,
                                 val products: List<ProductItemViewModel>): HomeFeed {
    val label get() = sticky.label
}