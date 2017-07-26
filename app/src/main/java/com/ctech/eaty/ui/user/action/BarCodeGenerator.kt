package com.ctech.eaty.ui.user.action

import com.ctech.eaty.response.UserResponse
import com.nytimes.android.external.store2.base.impl.BarCode

class BarCodeGenerator {

    fun currentBarCode(id: Int): BarCode = BarCode(UserResponse::class.java.simpleName, id.toString())
}