package com.ctech.eaty.ui.user.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Product

data class LoadMoreProductResult(val loading: Boolean = false, val error: Throwable? = null,
                             val content: List<Product> = emptyList(), val page: Int = 2) : Result {
    companion object {
        fun inProgress(): LoadMoreProductResult {
            return LoadMoreProductResult(true)
        }

        fun success(content: List<Product>): LoadMoreProductResult {
            return LoadMoreProductResult(content = content)
        }

        fun fail(throwable: Throwable): LoadMoreProductResult {
            return LoadMoreProductResult(error = throwable)
        }
    }
}