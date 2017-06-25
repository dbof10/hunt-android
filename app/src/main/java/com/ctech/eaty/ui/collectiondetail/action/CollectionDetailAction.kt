package com.ctech.eaty.ui.collectiondetail.action

import com.ctech.eaty.base.redux.Action

sealed class CollectionDetailAction : Action() {

    data class Load(val id: Int) : CollectionDetailAction()

}