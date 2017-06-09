package com.ctech.eaty.ui.home.result;

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Category

data class TabLoadResult(val loading: Boolean = false, val error: Throwable? = null,
                         val content: List<Category> = emptyList()) : Result {
    companion object {
        fun inProgress(): TabLoadResult {
            return TabLoadResult(true)
        }

        fun success(content: List<Category>): TabLoadResult {
            return TabLoadResult(content = content)
        }

        fun fail(throwable: Throwable): TabLoadResult {
            return TabLoadResult(error = throwable)
        }
    }
}