package com.ctech.eaty.ui.home.model

import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel

data class SuggestedCollection(val id: String,
                      val name: String,
                      val title: String,
                      val imageUrl: String,
                      val products: List<ProductItemViewModel>) : HomeFeed {
    val label get() = name
}