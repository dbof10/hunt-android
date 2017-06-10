package com.ctech.eaty.ui.comment.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Comment

data class LoadMoreResult(val loading: Boolean = false, val error: Throwable? = null,
                          val content: List<Comment> = emptyList(), val page: Int = 2) : Result {
    companion object {
        fun inProgress(): LoadMoreResult {
            return LoadMoreResult(true)
        }

        fun success(page: Int, content: List<Comment>): LoadMoreResult {
            return LoadMoreResult(content = content, page = page)
        }

        fun fail(throwable: Throwable): LoadMoreResult {
            return LoadMoreResult(error = throwable)
        }
    }
}