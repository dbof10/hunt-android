package com.ctech.eaty.ui.upcomingdetail.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.UpcomingDetail


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: UpcomingDetail? = null) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: UpcomingDetail): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}