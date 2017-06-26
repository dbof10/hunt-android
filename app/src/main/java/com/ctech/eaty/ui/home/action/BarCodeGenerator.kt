package com.ctech.eaty.ui.home.action

import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.util.DateUtils
import com.nytimes.android.external.store2.base.impl.BarCode
import org.joda.time.DateTime

class BarCodeGenerator {

    val currentBarCode = BarCode(ProductResponse::class.java.simpleName, DateUtils.getFormattedDate(DateTime.now()))

    fun generateNextBarCode(dayAgo: Int): BarCode = BarCode(ProductResponse::class.java.simpleName,
            DateUtils.getFormattedPastDate(DateTime.now(), dayAgo))

}