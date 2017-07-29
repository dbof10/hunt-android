package com.ctech.eaty.ui.user.result

import com.ctech.eaty.base.redux.Result

data class FollowUserResult(val loading: Boolean = false, val error: Throwable? = null,
                            val following: Boolean = false) : Result {
    companion object {
        fun inProgress(): FollowUserResult {
            return FollowUserResult(true)
        }

        fun success(following: Boolean): FollowUserResult {
            return FollowUserResult(following = following)
        }

        fun fail(throwable: Throwable): FollowUserResult {
            return FollowUserResult(error = throwable)
        }
    }
}