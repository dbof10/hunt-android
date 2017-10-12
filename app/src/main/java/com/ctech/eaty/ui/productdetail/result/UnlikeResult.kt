package com.ctech.eaty.ui.productdetail.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Like

data class UnlikeResult(val loading: Boolean = false, val error: Throwable? = null,
                        val content: Like? = null) : Result {
    companion object {
        fun inProgress(): UnlikeResult {
            return UnlikeResult(true)
        }

        fun success(content: Like): UnlikeResult {
            return UnlikeResult(content = content)
        }

        fun fail(throwable: Throwable): UnlikeResult {
            return UnlikeResult(error = throwable)
        }
    }
}