package com.ctech.eaty.ui.collectiondetail.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.CollectionDetail


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: CollectionDetail? = null) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: CollectionDetail): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}