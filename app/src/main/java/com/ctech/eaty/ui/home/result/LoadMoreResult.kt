package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Product
import org.joda.time.DateTime

data class LoadMoreResult(val loading: Boolean = false, val error: Throwable? = null,
                          val content: List<Product> = emptyList(), val dayAgo: Int = 1,
                          val date: DateTime = DateTime.now().minusDays(dayAgo)) : Result {
    companion object {
        fun inProgress(): LoadMoreResult {
            return LoadMoreResult(true)
        }

        fun success(dayAgo: Int, content: List<Product>): LoadMoreResult {
            return LoadMoreResult(content = content, dayAgo = dayAgo)
        }

        fun fail(throwable: Throwable): LoadMoreResult {
            return LoadMoreResult(error = throwable)
        }
    }
}