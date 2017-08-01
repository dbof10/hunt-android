
package com.ctech.eaty.ui.follow.action

import com.ctech.eaty.base.redux.Action

sealed class FollowAction : Action() {

    data class LoadFollower(val id: Int): FollowAction()

    data class LoadMoreFollower(val id: Int): FollowAction()

    data class LoadFollowing(val id: Int): FollowAction()

    data class LoadMoreFollowing(val id: Int): FollowAction()
}