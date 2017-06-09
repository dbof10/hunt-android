package com.ctech.eaty.ui.home.action

import com.ctech.eaty.entity.Products
import com.ctech.eaty.util.DateUtil
import com.nytimes.android.external.store2.base.impl.BarCode
import org.joda.time.DateTime

class HomeBarCodeGenerator {

    val currentBarCode = BarCode(Products::class.java.simpleName, DateUtil.getFormattedDate(DateTime.now()))

    fun generateNextBarCode(dayAgo: Int): BarCode {
        return BarCode(Products::class.java.simpleName, DateUtil.getFormattedPastDate(DateTime.now(),
                dayAgo))
    }
}