package com.ctech.eaty.ui.topiclist.action

import com.ctech.eaty.response.ProductResponse

class BarCodeGenerator {

    fun currentBarCode(id: Int): SearchBarCode = SearchBarCode(ProductResponse::class.java.simpleName, "Post $id", id)

    fun generateNextBarCode(id: Int, page: Int): SearchBarCode = SearchBarCode(ProductResponse::class.java.simpleName, "Post $id page $page", id, page)

}