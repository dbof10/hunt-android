package com.ctech.eaty.ui.productdetail.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.error.LikeExistedException
import com.ctech.eaty.error.UnauthorizedActionException
import com.ctech.eaty.error.UnlikeExistedException
import com.ctech.eaty.ui.productdetail.result.LikeResult
import com.ctech.eaty.ui.productdetail.result.LoadResult
import com.ctech.eaty.ui.productdetail.result.UnlikeResult
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import java.lang.IllegalArgumentException

//TODO invalidate model
class ProductDetailReducer : Reducer<ProductDetailState> {

    override fun apply(state: ProductDetailState, result: Result): ProductDetailState {
        when (result) {
            is LoadResult -> {
                return when {
                    result.loading -> state.copy(loading = true, error = null)
                    result.error != null -> state.copy(loading = false, error = result.error)
                    else -> state.copy(loading = false, error = null,
                            content = result.content)
                }
            }
            is LikeResult -> {
                return when {
                    result.loading -> state.copy(liking = true, requiredLoggedIn = false)
                    result.error != null ->
                        state.copy(liking = false,
                                likeError =
                                if ((result.error is LikeExistedException))
                                    null
                                else
                                    result.error,
                                liked = (result.error is LikeExistedException),
                                requiredLoggedIn = (result.error is UnauthorizedActionException))
                    else -> state.copy(liking = false,
                            likeError = null,
                            liked = true,
                            requiredLoggedIn = false)
                }
            }
            is UnlikeResult -> {
                return when {
                    result.loading -> state.copy(
                            unliking = true,
                            requiredLoggedIn = false,
                            likeError = null
                    )
                    result.error != null -> state.copy(
                            unliking = false,
                            unlikeError =
                            if ((result.error is UnlikeExistedException))
                                null
                            else
                                result.error,
                            liked = (result.error !is UnlikeExistedException),
                            requiredLoggedIn = (result.error is UnauthorizedActionException),
                            likeError = null
                    )
                    else -> state.copy(
                            unliking = false,
                            unlikeError = null,
                            liked = false,
                            requiredLoggedIn = false,
                            likeError = null
                    )

                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }
}