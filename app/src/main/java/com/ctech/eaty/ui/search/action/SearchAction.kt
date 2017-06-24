package com.ctech.eaty.ui.search.action

import com.ctech.eaty.base.redux.Action

sealed class SearchAction : Action() {

    data class Load(val id: Int) : SearchAction()

    data class LoadMore(val id: Int) : SearchAction()

}