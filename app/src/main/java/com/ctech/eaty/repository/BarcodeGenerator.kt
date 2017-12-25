package com.ctech.eaty.repository

import com.ctech.eaty.response.ProductDetailResponse
import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.response.UserResponse
import com.ctech.eaty.ui.user.action.UserProductBarCode
import com.ctech.eaty.util.DateUtils
import com.nytimes.android.external.store3.base.impl.BarCode
import org.joda.time.DateTime


object BarcodeGenerator {
    fun createHomeNextBarCode(dayAgo: Int): BarCode = BarCode(ProductResponse::class.java.simpleName,
            DateUtils.getFormattedPastDate(DateTime.now(), dayAgo))
}


fun createProductDetailBarcode(id: Int): BarCode = BarCode(ProductDetailResponse::class.java.simpleName, id.toString())

fun createUserProductBarCode(id: Int, page: Int) = UserProductBarCode(ProductResponse::class.java.simpleName, "User $id page $page", id, page)

fun createUserBarCode(id: Int): BarCode = BarCode(UserResponse::class.java.simpleName, id.toString())
