package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.home.model.Cursor
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import org.joda.time.DateTime

data class LoadMoreResult(val loading: Boolean = false, val error: Throwable? = null,
                          val dayAgo: Int = 1,
                          val date: DateTime = DateTime.now().minusDays(dayAgo),
                          val content: List<ProductItemViewModel> = emptyList(),
                          val cursor: Cursor = Cursor.DAILY) : Result {
    companion object {
        fun inProgress(): LoadMoreResult {
            return LoadMoreResult(true)
        }

        fun success(dayAgo: Int, content: List<ProductItemViewModel>): LoadMoreResult {
            return LoadMoreResult(content = content, dayAgo = dayAgo)
        }

        fun fail(throwable: Throwable): LoadMoreResult {
            return LoadMoreResult(error = throwable)
        }
    }
}