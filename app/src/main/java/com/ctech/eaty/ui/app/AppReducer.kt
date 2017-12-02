package com.ctech.eaty.ui.app

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.app.result.NetworkChangeResult

class AppReducer() : Reducer<AppState> {

    override fun apply(state: AppState, result: Result): AppState {
        if (result is NetworkChangeResult) {
            return state.copy(connectionType = result.connectionType)
        }
        return state
    }

}