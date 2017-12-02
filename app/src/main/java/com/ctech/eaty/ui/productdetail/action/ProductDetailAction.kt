package com.ctech.eaty.ui.productdetail.action

import com.ctech.eaty.base.redux.Action

sealed class ProductDetailAction : Action()
data class Load(val id: Int) : ProductDetailAction()
data class Like(val id: Int) : ProductDetailAction()
data class UnLike(val id: Int) : ProductDetailAction()
class CHECK_DATA_SAVER : ProductDetailAction()
class USE_MOBILE_DATA : ProductDetailAction()
