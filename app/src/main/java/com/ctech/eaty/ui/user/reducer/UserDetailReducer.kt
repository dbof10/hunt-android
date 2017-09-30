package com.ctech.eaty.ui.user.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.user.result.*
import com.ctech.eaty.ui.user.state.UserDetailState
import java.lang.IllegalArgumentException

class UserDetailReducer : Reducer<UserDetailState> {

    override fun apply(state: UserDetailState, result: Result): UserDetailState {
        when (result) {
            is LoadResult -> {
                return when {
                    result.loading -> state.copy(loading = true)
                    result.error != null -> state.copy(loading = false, loadError = result.error)
                    else -> state.copy(loading = false, loadError = null,
                            user = result.content)
                }
            }
            is LoadRelationshipResult -> {
                return when {
                    result.loading -> state.copy(loadingRL = true)
                    result.error != null -> state.copy(loadingRL = false, loadRLError = result.error)
                    else -> state.copy(loadingRL = false, loadRLError = null,
                            following = result.following)
                }
            }
            is FollowUserResult -> {
                return when {
                    result.loading -> state.copy(loadingRL = true)
                    result.error != null -> state.copy(loadingRL = false, loadRLError = result.error)
                    else -> state.copy(loadingRL = false, loadRLError = null,
                            following = result.following)
                }
            }
            is LoadProductResult -> {
                return when {
                    result.loading -> state.copy(loadingProduct = true)
                    result.error != null -> state.copy(loadingProduct = false, loadProductError = result.error)
                    else -> state.copy(loadingProduct = false, loadProductError = null,
                            products = result.content)
                }
            }
            is LoadMoreProductResult -> {
                return when {
                    result.loading -> state.copy(loadingMoreProduct = true)
                    result.error != null -> state.copy(loadingMoreProduct = false, loadMoreProductError = result.error)
                    else -> state.copy(loadingMoreProduct = false, loadMoreProductError = null,
                            products = state.products + result.content, page = result.page)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}