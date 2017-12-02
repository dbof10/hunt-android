package com.ctech.eaty.ui.productdetail.state

import com.ctech.eaty.entity.ProductDetail

data class ProductDetailState(val loading: Boolean = false, val error: Throwable? = null,
                              val requiredLoggedIn: Boolean = false,
                              val liking: Boolean = false,
                              val liked: Boolean = false,
                              val likeError: Throwable? = null,
                              val unliking: Boolean = false,
                              val unlikeError: Throwable? = null,
                              val saveMode: Boolean? = null,
                              val content: ProductDetail? = ProductDetail.EMPTY)
