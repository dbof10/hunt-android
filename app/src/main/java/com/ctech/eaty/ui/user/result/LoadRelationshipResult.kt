package com.ctech.eaty.ui.user.result

import com.ctech.eaty.base.redux.Result

data class LoadRelationshipResult(val loading: Boolean = false, val error: Throwable? = null,
                                  val following: Boolean = false) : Result {
    companion object {
        fun inProgress(): LoadRelationshipResult {
            return LoadRelationshipResult(true)
        }

        fun success(following: Boolean): LoadRelationshipResult {
            return LoadRelationshipResult(following = following)
        }

        fun fail(throwable: Throwable): LoadRelationshipResult {
            return LoadRelationshipResult(error = throwable)
        }
    }
}