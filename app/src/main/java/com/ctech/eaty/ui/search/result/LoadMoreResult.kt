package com.ctech.eaty.ui.search.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Product
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel

data class LoadMoreResult(val loading: Boolean = false, val error: Throwable? = null,
                          val content: List<ProductItemViewModel> = emptyList(), val page: Int = 2) : Result {
    companion object {
        fun inProgress(): LoadMoreResult {
            return LoadMoreResult(true)
        }

        fun success(page: Int, content: List<ProductItemViewModel>): LoadMoreResult {
            return LoadMoreResult(content = content, page = page)
        }

        fun fail(throwable: Throwable): LoadMoreResult {
            return LoadMoreResult(error = throwable)
        }
    }
}