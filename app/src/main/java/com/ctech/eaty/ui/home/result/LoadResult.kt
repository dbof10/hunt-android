package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Product
import org.joda.time.DateTime


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: List<Product> = emptyList(),
                      val date: DateTime = DateTime.now()) : Result{
    companion object {
        fun inProgress(): LoadResult{
            return LoadResult(true)
        }

        fun success(content: List<Product>): LoadResult{
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult{
            return LoadResult(error = throwable)
        }
    }
}