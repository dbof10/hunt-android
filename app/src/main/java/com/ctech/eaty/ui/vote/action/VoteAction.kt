
package com.ctech.eaty.ui.vote.action

import com.ctech.eaty.base.redux.Action

sealed class VoteAction : Action() {

    data class Load(val id: Int): VoteAction()

    data class LoadMore(val id: Int): VoteAction()
}