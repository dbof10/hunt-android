package com.ctech.eaty.ui.login.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.AccessToken

class RequestTokenResult(val loading: Boolean = false,
                         val error: Throwable? = null,
                         val firstTime: Boolean = false,
                         val content: AccessToken = AccessToken.EMPTY,
                         val loginSource: String? = null) : Result {
    companion object {
        fun inProgress() = RequestTokenResult(true)


        fun success(content: AccessToken, firstTime: Boolean, loginSource: String) =
                RequestTokenResult(content = content, firstTime = firstTime, loginSource = loginSource)


        fun fail(throwable: Throwable) = RequestTokenResult(error = throwable)

    }
}