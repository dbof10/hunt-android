package com.ctech.eaty.ui.radio.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.radio.result.LoadResult
import com.ctech.eaty.ui.radio.state.RadioState
import java.lang.IllegalArgumentException

class RadioReducer : Reducer<RadioState> {

    override fun apply(state: RadioState, result: Result): RadioState {
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