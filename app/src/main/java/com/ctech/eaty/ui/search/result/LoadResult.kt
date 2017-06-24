package com.ctech.eaty.ui.search.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Product
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: List<ProductItemViewModel> = emptyList()) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: List<ProductItemViewModel>): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}