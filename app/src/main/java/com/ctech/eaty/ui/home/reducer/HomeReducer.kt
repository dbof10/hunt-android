package com.ctech.eaty.ui.home.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.home.result.LoadMoreResult
import com.ctech.eaty.ui.home.result.LoadResult
import com.ctech.eaty.ui.home.result.RefreshResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.HorizontalAdsItemViewModel
import com.ctech.eaty.ui.home.viewmodel.SectionViewModel
import com.ctech.eaty.util.DateUtils
import org.joda.time.DateTime
import java.lang.IllegalArgumentException

//TODO: Ignore empty
class HomeReducer : Reducer<HomeState> {

    override fun apply(state: HomeState, result: Result): HomeState {
        when (result) {
            is LoadResult -> {
                if (result.loading) {
                    return state.copy(loading = true)
                } else if (result.error != null) {
                    return state.copy(loading = false, loadError = result.error)
                } else {
                    return state.copy(loading = false, loadError = null,
                            content = listOf(SectionViewModel(0, DateUtils.getRelativeTime(result.date))) + result.content)
                }
            }
            is RefreshResult -> {
                if (result.refreshing) {
                    return state.copy(refreshing = true)
                } else if (result.error != null) {
                    return state.copy(refreshing = false, refreshError = result.error)
                } else {
                    return state.copy(refreshing = false, refreshError = null,
                            content = listOf(SectionViewModel(0, DateUtils.getRelativeTime(result.date))) + result.content,
                            dayAgo = 0)
                }
            }
            is LoadMoreResult -> {
                if (result.loading) {
                    return state.copy(loadingMore = true)
                } else if (result.error != null) {

                    return state.copy(loadingMore = false, loadMoreError = result.error)
                } else {
                    return state.copy(loadingMore = false, loadError = null, content = state.content
                            + HorizontalAdsItemViewModel(result.dayAgo)
                            + listOf(SectionViewModel(result.dayAgo, DateUtils.getRelativeTime(DateTime.now(), result.date)))
                            + result.content, dayAgo = result.dayAgo)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}