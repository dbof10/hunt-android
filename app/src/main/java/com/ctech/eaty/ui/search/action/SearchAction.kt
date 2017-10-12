package com.ctech.eaty.ui.search.action

import com.ctech.eaty.base.redux.Action

sealed class SearchAction : Action() {

    data class Load(val keyword: String) : SearchAction()

    data class LoadMore(val keyword: String) : SearchAction()

}