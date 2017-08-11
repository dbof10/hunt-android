package com.ctech.eaty.ui.radio.action

import com.ctech.eaty.response.CollectionResponse
import com.nytimes.android.external.store3.base.impl.BarCode

class BarCodeGenerator {
    fun get(playlistId: String): BarCode = BarCode(CollectionResponse::class.java.simpleName, playlistId)
}