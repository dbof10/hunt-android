package com.ctech.eaty.ui.collection.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.collection.result.LoadMoreResult
import com.ctech.eaty.ui.collection.result.LoadResult
import com.ctech.eaty.ui.collection.state.CollectionState
import java.lang.IllegalArgumentException

class CollectionReducer : Reducer<CollectionState> {

    override fun apply(state: CollectionState, result: Result): CollectionState {
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