package com.ctech.eaty.ui.noti.action

import com.ctech.eaty.response.CollectionResponse
import com.nytimes.android.external.store3.base.impl.BarCode

class BarCodeGenerator {
    fun get(page: Int): BarCode = BarCode(CollectionResponse::class.java.simpleName, page.toString())
}