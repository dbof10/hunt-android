package com.ctech.eaty.ui.upcomingdetail.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.upcomingdetail.result.LoadResult
import com.ctech.eaty.ui.upcomingdetail.result.SubscribeResult
import com.ctech.eaty.ui.upcomingdetail.state.UpcomingProductState
import java.lang.IllegalArgumentException

class UpcomingProductReducer : Reducer<UpcomingProductState> {

    override fun apply(state: UpcomingProductState, result: Result): UpcomingProductState {
        when (result) {
            is LoadResult -> {
                return when {
                    result.loading -> state.copy(loading = true, loadError = null)
                    result.error != null -> state.copy(loading = false, loadError = result.error)
                    else -> state.copy(loading = false, loadError = null,
                            content = result.content)
                }
            }
            is SubscribeResult -> {
                return when {
                    result.loading -> state.copy(subscribing = true, subscribeError = null)
                    result.error != null -> state.copy(subscribing = false, loadError = result.error)
                    else -> state.copy(subscribed = true, subscribeError = null,
                            subscribing = false)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}