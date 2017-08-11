package com.ctech.eaty.ui.productdetail.action

import com.ctech.eaty.response.ProductDetailResponse
import com.nytimes.android.external.store3.base.impl.BarCode

class BarCodeGenerator {
    fun get(id: Int): BarCode = BarCode(ProductDetailResponse::class.java.simpleName, id.toString())
}