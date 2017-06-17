package com.ctech.eaty.ui.productdetail.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.ProductDetail


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: ProductDetail? = null) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: ProductDetail): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}