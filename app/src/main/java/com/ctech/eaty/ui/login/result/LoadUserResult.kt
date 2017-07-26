package com.ctech.eaty.ui.login.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.UserDetail

data class LoadUserResult(val loading: Boolean = false, val error: Throwable? = null,
                          val content: UserDetail = UserDetail.GUEST) : Result {
    companion object {
        fun inProgress(): LoadUserResult {
            return LoadUserResult(true)
        }

        fun success(content: UserDetail): LoadUserResult {
            return LoadUserResult(content = content)
        }

        fun fail(throwable: Throwable): LoadUserResult {
            return LoadUserResult(error = throwable)
        }
    }
}