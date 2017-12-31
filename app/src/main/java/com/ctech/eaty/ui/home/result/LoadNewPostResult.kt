package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel

data class LoadNewPostResult(val loading: Boolean = false, val error: Throwable? = null,
                             val content: List<ProductItemViewModel> = emptyList(),
                             val page: Int = 1) : Result {
    companion object {
        fun inProgress(): LoadNewPostResult {
            return LoadNewPostResult(true)
        }

        fun success(content: List<ProductItemViewModel>, page: Int): LoadNewPostResult {
            return LoadNewPostResult(content = content, page = page)
        }

        fun fail(throwable: Throwable): LoadNewPostResult {
            return LoadNewPostResult(error = throwable)
        }
    }
}