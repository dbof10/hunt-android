package com.ctech.eaty.ui.productdetail.viewmodel

import com.ctech.eaty.entity.ProductDetail

data class ProductHeaderItemViewModel(private val productDetail: ProductDetail) : ProductBodyItemViewModel {

    val id: Int get() = productDetail.id
    val commentCount: Int get() = productDetail.commentCount
    val voteCount: Int get() = productDetail.voteCount
    val hunterName: String get() = productDetail.hunter.name
    val avatarUrl: String get() = productDetail.hunter.imageUrl.smallImgUrl
    val productTitle: String get() = productDetail.name
    val productDescription: String get() = productDetail.tagline

}