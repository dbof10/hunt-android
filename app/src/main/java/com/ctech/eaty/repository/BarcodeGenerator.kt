package com.ctech.eaty.repository

import com.ctech.eaty.response.ProductDetailResponse
import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.util.DateUtils
import com.nytimes.android.external.store3.base.impl.BarCode
import org.joda.time.DateTime


fun createHomeNextBarCode(dayAgo: Int): BarCode = BarCode(ProductResponse::class.java.simpleName,
        DateUtils.getFormattedPastDate(DateTime.now(), dayAgo))

fun createProductDetailBarcode(id: Int): BarCode = BarCode(ProductDetailResponse::class.java.simpleName, id.toString())
