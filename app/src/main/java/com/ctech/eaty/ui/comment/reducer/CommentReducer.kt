package com.ctech.eaty.ui.comment.reducer

import android.util.Log
import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.comment.result.LoadMoreResult
import com.ctech.eaty.ui.comment.result.LoadResult
import com.ctech.eaty.ui.comment.state.CommentState
import java.lang.IllegalArgumentException

class CommentReducer : Reducer<CommentState> {

    override fun apply(state: CommentState, result: Result): CommentState {
        Log.e("Reducer", state.toString())
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