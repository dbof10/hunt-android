package com.ctech.eaty.ui.home.viewmodel

data class HomeFeed(val date: DateItemViewModel,
                    val products: List<ProductItemViewModel>,
                    val horizontalAds: List<HorizontalAdsItemViewModel> = emptyList(),
                    val feedFooter: FeedFooterItemViewModel? = null){

    override fun toString(): String {
        return feedFooter?.toString() ?: "empty z"
    }
}