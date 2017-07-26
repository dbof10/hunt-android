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
                if (result.loading) {
                    return state.copy(loading = true, loadError = null)
                } else if (result.error != null) {
                    return state.copy(loading = false, loadError = result.error)
                } else {
                    return state.copy(loading = false, loadError = null,
                            tokenGrant = true)
                }
            }
            is LoadUserResult -> {
                if (result.loading) {
                    return state.copy(loading = true, loadError = null, tokenGrant = false)
                } else if (result.error != null) {
                    return state.copy(loading = false, loadError = result.error, tokenGrant = false)
                } else {
                    return state.copy(loading = false, loadError = null, content = result.content, tokenGrant = false)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}