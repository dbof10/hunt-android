package com.ctech.eaty.ui.topic.action

import com.ctech.eaty.response.TopicResponse
import com.nytimes.android.external.store2.base.impl.BarCode

class BarCodeGenerator {
    fun get(page: Int): BarCode = BarCode(TopicResponse::class.java.simpleName, page.toString())
}