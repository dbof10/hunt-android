package com.ctech.eaty.ui.collectiondetail.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.collectiondetail.result.LoadResult
import com.ctech.eaty.ui.collectiondetail.state.CollectionDetailState
import java.lang.IllegalArgumentException

class CollectionDetailReducer : Reducer<CollectionDetailState> {

    override fun apply(state: CollectionDetailState, result: Result): CollectionDetailState {
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
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}