package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.home.model.SuggestedCollection

data class LoadCollectionResult(val loading: Boolean = false,
                                val error: Throwable? = null,
                                val content: SuggestedCollection? = null,
                                val page: Int = 1) : Result {
    companion object {
        fun inProgress(): LoadCollectionResult {
            return LoadCollectionResult(true)
        }

        fun success(content: SuggestedCollection, page: Int): LoadCollectionResult {
            return LoadCollectionResult(content = content, page = page)
        }

        fun fail(throwable: Throwable, page: Int = 1): LoadCollectionResult {
            return LoadCollectionResult(error = throwable, page = page)
        }
    }
}