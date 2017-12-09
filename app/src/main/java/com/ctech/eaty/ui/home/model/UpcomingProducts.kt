package com.ctech.eaty.ui.home.model

import com.ctech.eaty.ui.home.viewmodel.StickyItemViewModel
import com.ctech.eaty.ui.home.viewmodel.UpcomingProductItemProps

data class UpcomingProducts(val sticky: StickyItemViewModel, val products: List<UpcomingProductItemProps>) : HomeFeed {

    val label get() = sticky.label
}