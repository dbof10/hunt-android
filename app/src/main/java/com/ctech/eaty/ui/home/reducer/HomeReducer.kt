package com.ctech.eaty.ui.home.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.home.result.*
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.HomeItemViewModel
import com.ctech.eaty.ui.home.viewmodel.SectionViewModel
import com.ctech.eaty.util.DateUtils
import org.joda.time.DateTime
import java.lang.IllegalArgumentException

class HomeReducer : Reducer<HomeState> {

    private val AD_ID = "1966287263602613_1966287926935880"
    private val ADMOB_NATIVE_1 = "ca-app-pub-6007267266282978/2953025043"


    override fun apply(state: HomeState, result: Result): HomeState {
        when (result) {
            is LoadResult -> {
                return when {
                    result.loading -> state.copy(loading = true)
                    result.error != null -> state.copy(loading = false, loadError = result.error)
                    else -> state.copy(loading = false, loadError = null,
                            content = listOf(SectionViewModel(0, DateUtils.getRelativeTime(result.date))) + result.content)
                }
            }
            is RefreshResult -> {
                return when {
                    result.refreshing -> state.copy(refreshing = true)
                    result.error != null -> state.copy(refreshing = false, refreshError = result.error)
                    else -> state.copy(refreshing = false, refreshError = null,
                            loadError = null,
                            content = listOf(SectionViewModel(0, DateUtils.getRelativeTime(result.date))) + result.content,
                            dayAgo = 0)
                }
            }
            is LoadMoreResult -> {
                return when {
                    result.loading -> state.copy(loadingMore = true)
                    result.error != null -> state.copy(loadingMore = false, loadMoreError = result.error)
                    else -> state.copy(loadingMore = false, loadError = null,
                            refreshError = null,
                            loadMoreError = null,
                            content = computeNextBody(state, result),
                            dayAgo = result.dayAgo)
                }
            }
            is LoadUserResult -> {
                return state.copy(user = result.content)
            }
            is CheckLoginResult -> {
                return state.copy(user = result.content)
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }

    private fun computeNextBody(state: HomeState, result: LoadMoreResult): List<HomeItemViewModel> {
        return if (result.dayAgo < 2) {
            state.content + //HorizontalAdsItemViewModel(result.dayAgo, AD_ID) +
                    listOf(SectionViewModel(result.dayAgo, DateUtils.getRelativeTime(DateTime.now(), result.date))) + result.content
        } else {
            state.content + listOf(SectionViewModel(result.dayAgo, DateUtils.getRelativeTime(DateTime.now(), result.date))) + result.content
        }
    }
}