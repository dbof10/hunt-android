package com.ctech.eaty.ui.productdetail.state

import com.ctech.eaty.entity.ProductDetail

data class ProductDetailState(val loading: Boolean = false, val error: Throwable? = null,
                              val content: ProductDetail? = null)
