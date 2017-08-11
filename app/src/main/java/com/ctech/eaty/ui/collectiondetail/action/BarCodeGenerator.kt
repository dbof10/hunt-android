package com.ctech.eaty.ui.collectiondetail.action

import com.ctech.eaty.response.CollectionDetailResponse
import com.nytimes.android.external.store3.base.impl.BarCode

class BarCodeGenerator {

    fun currentBarCode(id: Int): BarCode = BarCode(CollectionDetailResponse::class.java.simpleName, id.toString())
}