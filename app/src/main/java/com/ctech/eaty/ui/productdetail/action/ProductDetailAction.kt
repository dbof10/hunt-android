package com.ctech.eaty.ui.productdetail.action

import com.ctech.eaty.base.redux.Action

sealed class ProductDetailAction : Action() {

    data class Load(val id: Int) : ProductDetailAction()

}