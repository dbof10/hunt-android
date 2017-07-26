package com.ctech.eaty.ui.user.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.UserDetail


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: UserDetail? = null) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: UserDetail): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}