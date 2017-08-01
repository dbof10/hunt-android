package com.ctech.eaty.ui.follow.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.User


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: List<User> = emptyList()) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: List<User>): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}