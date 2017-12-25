package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.home.model.Cursor
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import org.joda.time.DateTime


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: List<ProductItemViewModel> = emptyList(),
                      val date: DateTime = DateTime.now(),
                      val cursor: Cursor = Cursor.UPCOMING) : Result {
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