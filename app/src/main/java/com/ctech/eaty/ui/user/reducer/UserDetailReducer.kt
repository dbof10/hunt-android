package com.ctech.eaty.ui.user.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.user.result.LoadResult
import com.ctech.eaty.ui.user.state.UserDetailState
import java.lang.IllegalArgumentException

class UserDetailReducer : Reducer<UserDetailState> {

    override fun apply(state: UserDetailState, result: Result): UserDetailState {
        when (result) {
            is LoadResult -> {
                if (result.loading) {
                    return state.copy(loading = true)
                } else if (result.error != null) {
                    return state.copy(loading = false, loadError = result.error)
                } else {
                    return state.copy(loading = false, loadError = null,
                            content = result.content)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}