package com.ctech.eaty.ui.productdetail.viewmodel

import android.net.Uri
import com.ctech.eaty.entity.ProductDetail
import com.ctech.eaty.ui.productdetail.navigation.ProductDetailNavigation
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import com.ctech.eaty.util.UrlEncodedQueryString
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject


class ProductDetailViewModel(private val stateDispatcher: BehaviorSubject<ProductDetailState>,
                             private val navigation: ProductDetailNavigation,
                             private val threadScheduler: ThreadScheduler) {
    private val HEADER_IMAGE_SIZE = 500
    private val MAX_BODY_ITEM = 7
    private var body: List<ProductBodyItemViewModel> = emptyList()
    private val bodySubject: PublishSubject<List<ProductBodyItemViewModel>> = PublishSubject.create()

    fun loading(): Observable<ProductDetailState> {
        return stateDispatcher
                .observeOn(threadScheduler.uiThread())
                .filter { it.loading }
    }


    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .observeOn(threadScheduler.uiThread())
                .filter { it.error != null && !it.loading }
                .map { it.error }

    }

    fun header(): Observable<String> {
        return content()
                .map {
                    handleUrl(it.thumbnail.imageUrl)
                }
    }

    private fun handleUrl(url: String): String {
        val uri = Uri.parse(url)
        return "${uri.scheme}://${uri.host}${uri.path}?${UrlEncodedQueryString.parse(uri).set("h", HEADER_IMAGE_SIZE)
                .set("w", HEADER_IMAGE_SIZE)}"
    }

    fun content(): Observable<ProductDetail> {
        return stateDispatcher
                .observeOn(threadScheduler.uiThread())
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

    fun body(): Observable<List<ProductBodyItemViewModel>> {
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

    fun navigateToVote() {
        val product = stateDispatcher.value.content
        product?.run {
            navigation.toVote(id, voteCount).subscribe()
        }
    }

    fun navigateComment() {
        val product = stateDispatcher.value.content
        product?.run {
            navigation.toComment(id).subscribe()
        }
    }

    fun shareLink() {
        val product = stateDispatcher.value.content
        product?.run {
            navigation.toShare(redirectUrl).subscribe()
        }
    }
}