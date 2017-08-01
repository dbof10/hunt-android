package com.ctech.eaty.ui.follow.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.follow.result.LoadMoreResult
import com.ctech.eaty.ui.follow.result.LoadResult
import com.ctech.eaty.ui.follow.state.FollowState
import java.lang.IllegalArgumentException

class FollowReducer : Reducer<FollowState> {

    override fun apply(state: FollowState, result: Result): FollowState {
        when (result) {
            is LoadResult -> {
                if (result.loading) {
                    return state.copy(loading = true, loadError = null)
                } else if (result.error != null) {
                    return state.copy(loading = false, loadError = result.error)
                } else {
                    return state.copy(loading = false, loadError = null,
                            content = result.content)
                }
            }
            is LoadMoreResult -> {
                if (result.loading) {
                    return state.copy(loadingMore = true)
                } else if (result.error != null) {

                    return state.copy(loadingMore = false, loadMoreError = result.error)
                } else {
                    return state.copy(loadingMore = false, loadError = null, content = state.content + result.content, page = result.page)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}