package com.ctech.eaty.ui.login.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.login.result.LoadUserResult
import com.ctech.eaty.ui.login.result.RequestTokenResult
import com.ctech.eaty.ui.login.state.LoginState

class LoginReducer : Reducer<LoginState> {

    override fun apply(state: LoginState, result: Result): LoginState {
        when (result) {
            is RequestTokenResult -> {
                return when {
                    result.loading -> state.copy(loading = true, loadError = null)
                    result.error != null -> state.copy(loading = false, loadError = result.error)
                    else -> state.copy(loading = false, loadError = null,
                            tokenGrant = true, firstTime = result.firstTime, loginSource = result.loginSource)
                }
            }
            is LoadUserResult -> {
                return when {
                    result.loading -> state.copy(loading = true, loadError = null, tokenGrant = false)
                    result.error != null -> state.copy(loading = false, loadError = result.error, tokenGrant = false)
                    else -> state.copy(loading = false, loadError = null, content = result.content, tokenGrant = false)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}