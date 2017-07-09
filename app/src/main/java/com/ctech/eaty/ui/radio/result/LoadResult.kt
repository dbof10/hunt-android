package com.ctech.eaty.ui.radio.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.response.RadioResponse


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: RadioResponse? = null) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: RadioResponse): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}