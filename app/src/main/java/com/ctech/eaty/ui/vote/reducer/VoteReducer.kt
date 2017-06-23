package com.ctech.eaty.ui.vote.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.vote.result.LoadMoreResult
import com.ctech.eaty.ui.vote.result.LoadResult
import com.ctech.eaty.ui.vote.state.VoteState
import java.lang.IllegalArgumentException

class VoteReducer : Reducer<VoteState> {

    override fun apply(state: VoteState, result: Result): VoteState {
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