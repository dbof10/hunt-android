package com.ctech.eaty.ui.productdetail.viewmodel

import com.ctech.eaty.entity.Product
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel

data class ProductRecommendItemViewModel(private val products: List<Product>) : ProductBodyItemViewModel {

    val id = listItem.size

    val listItem: List<ProductItemViewModel> get() {
        return products.map {
            ProductItemViewModel(it)
        }
    }
}