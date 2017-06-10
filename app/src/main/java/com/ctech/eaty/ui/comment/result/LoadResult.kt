package com.ctech.eaty.ui.comment.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Comment


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: List<Comment> = emptyList()) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: List<Comment>): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}