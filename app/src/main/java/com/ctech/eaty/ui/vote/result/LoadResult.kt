package com.ctech.eaty.ui.vote.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Vote


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: List<Vote> = emptyList()) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: List<Vote>): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}