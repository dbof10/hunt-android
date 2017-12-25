package com.ctech.eaty.ui.upcomingdetail.result

import com.ctech.eaty.base.redux.Result

data class SubscribeResult(val loading: Boolean = false,
                           val subscribed: Boolean = false,
                           val error: Throwable? = null) : Result {

    companion object {
        fun inProgress(): SubscribeResult {
            return SubscribeResult(true)
        }

        fun success(): SubscribeResult {
            return SubscribeResult(subscribed = true)
        }

        fun fail(throwable: Throwable): SubscribeResult {
            return SubscribeResult(error = throwable)
        }
    }
}