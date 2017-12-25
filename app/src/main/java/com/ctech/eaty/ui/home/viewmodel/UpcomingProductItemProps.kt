package com.ctech.eaty.ui.home.viewmodel

import com.ctech.eaty.entity.UpcomingProduct
import com.ctech.eaty.util.Constants

data class UpcomingProductItemProps(private val product: UpcomingProduct, val saveMode: Boolean = false) {

    val id: String get() = product.id
    val name get() = product.name
    val tagline get() = product.tagline

    val backgroundUrl get() = Constants.PRODUCT_CDN_URL + "/" + product.backgroundUrl


    val foregroundUrl get() = Constants.PRODUCT_CDN_URL + "/" + product.foregroundUrl


    val subscriberCount get() = product.subscriberCount

    val topSubscribers get() = product.topSubscribers
}