package com.ctech.eaty.ui.topic.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Topic


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: List<Topic> = emptyList()) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: List<Topic>): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}