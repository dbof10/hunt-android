package com.ctech.eaty.ui.noti.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Notification


data class LoadResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: List<Notification> = emptyList()) : Result {
    companion object {
        fun inProgress(): LoadResult {
            return LoadResult(true)
        }

        fun success(content: List<Notification>): LoadResult {
            return LoadResult(content = content)
        }

        fun fail(throwable: Throwable): LoadResult {
            return LoadResult(error = throwable)
        }
    }
}