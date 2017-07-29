package com.ctech.eaty.ui.user.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.user.result.FollowUserResult
import com.ctech.eaty.ui.user.result.LoadProductResult
import com.ctech.eaty.ui.user.result.LoadRelationshipResult
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
                            user = result.content)
                }
            }
            is LoadRelationshipResult -> {
                if (result.loading) {
                    return state.copy(loadingRL = true)
                } else if (result.error != null) {
                    return state.copy(loadingRL = false, loadRLError = result.error)
                } else {
                    return state.copy(loadingRL = false, loadRLError = null,
                            following = result.following)
                }
            }
            is FollowUserResult -> {
                if (result.loading) {
                    return state.copy(loadingRL = true)
                } else if (result.error != null) {
                    return state.copy(loadingRL = false, loadRLError = result.error)
                } else {
                    return state.copy(loadingRL = false, loadRLError = null,
                            following = result.following)
                }
            }
            is LoadProductResult -> {
                if (result.loading) {
                    return state.copy(loadingProduct = true)
                } else if (result.error != null) {
                    return state.copy(loadingProduct = false, loadProductError = result.error)
                } else {
                    return state.copy(loadingProduct = false, loadProductError = null,
                            products = result.content)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}