package com.ctech.eaty.ui.user.action

import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.response.UserResponse
import com.nytimes.android.external.store3.base.impl.BarCode

class BarCodeGenerator {

    fun currentBarCode(id: Int): BarCode = BarCode(UserResponse::class.java.simpleName, id.toString())

    fun currentProductBarCode(id: Int): UserProductBarCode = UserProductBarCode(ProductResponse::class.java.simpleName, "User $id", id)

    fun generateNextProductBarCode(id: Int, page: Int): UserProductBarCode = UserProductBarCode(ProductResponse::class.java.simpleName, "User $id page $page", id, page)
}