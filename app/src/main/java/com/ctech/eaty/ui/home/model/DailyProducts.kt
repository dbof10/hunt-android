package com.ctech.eaty.ui.home.model

import com.ctech.eaty.ui.home.viewmodel.HorizontalAdsItemViewModel
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.home.viewmodel.StickyItemViewModel

data class DailyProducts(val sticky: StickyItemViewModel,
                         val products: List<ProductItemViewModel>,
                         val horizontalAds: List<HorizontalAdsItemViewModel> = emptyList()) : HomeFeed {

    val label get() = sticky.label
}