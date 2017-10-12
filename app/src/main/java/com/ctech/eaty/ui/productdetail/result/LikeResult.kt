package com.ctech.eaty.ui.productdetail.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Like

data class LikeResult(val loading: Boolean = false, val error: Throwable? = null,
                      val content: Like? = null) : Result {
    companion object {
        fun inProgress(): LikeResult {
            return LikeResult(true)
        }

        fun success(content: Like): LikeResult {
            return LikeResult(content = content)
        }

        fun fail(throwable: Throwable): LikeResult {
            return LikeResult(error = throwable)
        }
    }
}