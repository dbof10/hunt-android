package com.ctech.eaty.ui.productdetail.viewmodel

import android.util.Log
import com.ctech.eaty.entity.ProductDetail
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

const val MAX_BODY_ITEM = 7

class ProductDetailViewModel(private val stateDispatcher: Observable<ProductDetailState>, private val threadScheduler: ThreadScheduler) {
    private var body: List<ProductBodyItemViewModel> = emptyList()
    private val bodySubject: PublishSubject<List<ProductBodyItemViewModel>> = PublishSubject.create()

    fun loading(): Observable<ProductDetailState> {
        return stateDispatcher
                .filter { it.loading }
    }


    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.error != null && !it.loading }
                .map { it.error }
    }


    fun content(): Observable<ProductDetail> {
        return stateDispatcher
                .filter {
                    !it.loading && it.error == null
                }
                .flatMap {
                    if (it.content == null)
                        Observable.just(ProductDetail.EMPTY)
                    else
                        Observable.just(it.content)
                }
    }

    fun comments(): Observable<List<ProductBodyItemViewModel>> {
        return content()
                .map {
                    val body = ArrayList<ProductBodyItemViewModel>(MAX_BODY_ITEM)
                    body += mapHeader(it)
                    if (it.comments.size > 5) {
                        body += it.comments.take(5).map {
                            CommentItemViewModel(it)
                        }
                    } else {
                        body += it.comments.map {
                            CommentItemViewModel(it)
                        }
                    }
                    this.body = body
                    body
                }
    }

    fun commentsSelection(): Observable<List<ProductBodyItemViewModel>> {
        return bodySubject
                .subscribeOn(threadScheduler.workerThread())
                .observeOn(threadScheduler.uiThread())
    }

    fun selectCommentAt(position: Int) {

        body = body.mapIndexed { index, bodyItemViewModel ->
            if (index == 0) {
                return@mapIndexed bodyItemViewModel
            }
            val viewModel = bodyItemViewModel as CommentItemViewModel
            if (index == position) {
                viewModel.copy(selected = !viewModel.isSelected)
            } else {
                viewModel.copy(selected = false)
            }
        }
        bodySubject.onNext(body)
    }

    private fun mapHeader(productDetail: ProductDetail): ProductHeaderItemViewModel = ProductHeaderItemViewModel(productDetail)
}