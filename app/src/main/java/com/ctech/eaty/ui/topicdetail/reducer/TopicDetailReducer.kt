package com.ctech.eaty.ui.topicdetail.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.topicdetail.result.LoadMoreResult
import com.ctech.eaty.ui.topicdetail.result.LoadResult
import com.ctech.eaty.ui.topicdetail.state.TopicDetailState
import java.lang.IllegalArgumentException

class TopicDetailReducer : Reducer<TopicDetailState> {

    override fun apply(state: TopicDetailState, result: Result): TopicDetailState {
        when (result) {
            is LoadResult -> {
                return when {
                    result.loading -> state.copy(loading = true)
                    result.error != null -> state.copy(loading = false, loadError = result.error)
                    else -> state.copy(loading = false, loadError = null,
                            content = result.content)
                }
            }
            is LoadMoreResult -> {
                return when {
                    result.loading -> state.copy(loadingMore = true)
                    result.error != null -> state.copy(loadingMore = false, loadMoreError = result.error)
                    else -> state.copy(loadingMore = false, loadError = null, content = state.content + result.content, page = result.page)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}