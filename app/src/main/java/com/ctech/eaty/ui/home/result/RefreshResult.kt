package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Product
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import org.joda.time.DateTime

class RefreshResult(val refreshing: Boolean = false,
                    val error: Throwable? = null,
                    val content: List<ProductItemViewModel> = emptyList(),
                    val date: DateTime = DateTime.now()) : Result {

    companion object {
        fun inProgress(): RefreshResult {
            return RefreshResult(true)
        }

        fun success(content: List<ProductItemViewModel>): RefreshResult {
            return RefreshResult(content = content)
        }

        fun fail(throwable: Throwable): RefreshResult {
            return RefreshResult(error = throwable)
        }
    }
}