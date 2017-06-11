package com.ctech.eaty.ui.collection.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Collection


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: List<Collection> = emptyList()) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: List<Collection>): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}