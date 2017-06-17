package com.ctech.eaty.ui.productdetail.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.productdetail.result.LoadResult
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import java.lang.IllegalArgumentException

class ProductDetailReducer : Reducer<ProductDetailState> {

    override fun apply(state: ProductDetailState, result: Result): ProductDetailState {
        when (result) {
            is LoadResult -> {
                if (result.loading) {
                    return state.copy(loading = true, error = null)
                } else if (result.error != null) {
                    return state.copy(loading = false, error = result.error)
                } else {
                    return state.copy(loading = false, error = null,
                            content = result.content)
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}