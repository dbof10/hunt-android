package com.ctech.eaty.ui.noti.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.noti.result.LoadResult
import com.ctech.eaty.ui.noti.state.NotificationState
import java.lang.IllegalArgumentException

class NotificationReducer : Reducer<NotificationState> {

    override fun apply(state: NotificationState, result: Result): NotificationState {
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
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}