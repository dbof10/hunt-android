package com.ctech.eaty.ui.user.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Product

data class LoadProductResult(val loading: Boolean = false, val error: Throwable? = null,
                             val content: List<Product> = emptyList()) : Result {
    companion object {
        fun inProgress(): LoadProductResult {
            return LoadProductResult(true)
        }

        fun success(content: List<Product>): LoadProductResult {
            return LoadProductResult(content = content)
        }

        fun fail(throwable: Throwable): LoadProductResult {
            return LoadProductResult(error = throwable)
        }
    }
}