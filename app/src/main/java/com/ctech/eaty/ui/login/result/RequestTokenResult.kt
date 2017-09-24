package com.ctech.eaty.ui.login.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.AccessToken

class RequestTokenResult(val loading: Boolean = false,
                         val error: Throwable? = null,
                         val firstTime: Boolean = false,
                         val content: AccessToken = AccessToken.EMPTY) : Result {
    companion object {
        fun inProgress(): RequestTokenResult = RequestTokenResult(true)


        fun success(content: AccessToken, firstTime: Boolean): RequestTokenResult =
                RequestTokenResult(content = content, firstTime = firstTime)


        fun fail(throwable: Throwable): RequestTokenResult = RequestTokenResult(error = throwable)

    }
}