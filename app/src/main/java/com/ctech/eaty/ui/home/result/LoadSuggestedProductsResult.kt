package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel

data class LoadSuggestedProductsResult(val loading: Boolean = false, val error: Throwable? = null,
                                       val content: List<ProductItemViewModel> = emptyList(),
                                       val page: Int = 1) : Result {
    companion object {
        fun inProgress(): LoadSuggestedProductsResult {
            return LoadSuggestedProductsResult(true)
        }

        fun success(content: List<ProductItemViewModel>, page: Int): LoadSuggestedProductsResult {
            return LoadSuggestedProductsResult(content = content, page = page)
        }

        fun fail(throwable: Throwable): LoadSuggestedProductsResult {
            return LoadSuggestedProductsResult(error = throwable)
        }
    }
}