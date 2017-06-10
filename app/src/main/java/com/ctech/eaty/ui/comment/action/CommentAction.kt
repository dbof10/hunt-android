
package com.ctech.eaty.ui.comment.action

import com.ctech.eaty.base.redux.Action

sealed class CommentAction : Action() {

    data class Load(val id: Int): CommentAction()

    data class LoadMore(val id: Int): CommentAction()
}