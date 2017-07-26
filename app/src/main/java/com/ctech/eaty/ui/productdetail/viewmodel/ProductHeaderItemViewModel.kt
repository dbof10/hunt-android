package com.ctech.eaty.ui.productdetail.viewmodel

import com.ctech.eaty.entity.ProductDetail
import com.ctech.eaty.entity.User
import org.joda.time.DateTime

data class ProductHeaderItemViewModel(private val productDetail: ProductDetail) : ProductBodyItemViewModel {

    val id: Int get() = productDetail.id
    val commentCount: Int get() = productDetail.commentCount
    val voteCount: Int get() = productDetail.voteCount
    val hunterName: String get() = productDetail.hunter.name
    val avatarUrl: String get() = productDetail.hunter.imageUrl.px48
    val productTitle: String get() = productDetail.name
    val productDescription: String get() = productDetail.tagline
    val createdAt: DateTime get() = productDetail.createdAt
    val user: User get() = productDetail.hunter

}