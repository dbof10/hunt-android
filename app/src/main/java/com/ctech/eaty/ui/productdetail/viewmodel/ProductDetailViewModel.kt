package com.ctech.eaty.ui.productdetail.viewmodel

import android.app.ActivityOptions
import android.support.customtabs.CustomTabsSession
import com.ctech.eaty.entity.ProductDetail
import com.ctech.eaty.entity.User
import com.ctech.eaty.ui.productdetail.navigation.ProductDetailNavigation
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.ctech.eaty.util.rx.Functions
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber


class ProductDetailViewModel(private val stateDispatcher: BehaviorSubject<ProductDetailState>,
                             private val navigation: ProductDetailNavigation,
                             private val threadScheduler: ThreadScheduler) {
    private val HEADER_IMAGE_SIZE = 300
    private val MAX_BODY_ITEM = 7
    private var body: List<ProductBodyItemViewModel> = emptyList()
    private val bodySubject: PublishSubject<List<ProductBodyItemViewModel>> = PublishSubject.create()


    fun requiredLoggedIn(): Completable {
        return stateDispatcher
                .filter {
                    it.requiredLoggedIn
                }
                .flatMapCompletable {
                    navigation.toLogin()
                }
                .observeOn(threadScheduler.uiThread())

    }

    fun liked(): Observable<Boolean> = stateDispatcher
            .map { it.liked }

    fun liking(): Observable<Boolean> {
        return stateDispatcher
                .filter {
                    it.liking
                }
                .map { true }
    }

    fun unliking(): Observable<Boolean> {
        return stateDispatcher
                .filter {
                    it.unliking
                }
                .map { false }
    }

    fun loading(): Observable<ProductDetailState> {
        return stateDispatcher
                .observeOn(threadScheduler.uiThread())
                .filter { it.loading && it.content == ProductDetail.EMPTY }
    }


    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.error != null && !it.loading }
                .observeOn(threadScheduler.uiThread())
                .map { it.error }

    }

    fun dataSaver(): Observable<ProductDetailState> {
        return content()
                .filter {
                    it.saveMode == true
                }
    }

    fun header(): Observable<String> {
        return content()
                .filter {
                    it.saveMode == false
                }
                .map {
                    it.content
                }
                .map {
                    ResizeImageUrlProvider.overrideUrl(it.thumbnail.imageUrl, HEADER_IMAGE_SIZE)
                }
    }


    fun content(): Observable<ProductDetailState> {
        return stateDispatcher
                .doOnNext {

                }
                .filter {
                    !it.loading && it.error == null && it.content != null && it.content != ProductDetail.EMPTY
                }
                .observeOn(threadScheduler.uiThread())


    }

    fun body(): Observable<List<ProductBodyItemViewModel>> {
        return content()
                .map { it.content }
                .map {
                    val body = ArrayList<ProductBodyItemViewModel>(MAX_BODY_ITEM)
                    body += mapHeader(it)
                    body += if (it.comments.size > 5) {
                        it.comments.take(5).map {
                            CommentItemViewModel(it)
                        }
                    } else {
                        it.comments.map {
                            CommentItemViewModel(it)
                        }
                    }
                    if (it.relatedPosts.isNotEmpty()) {
                        body += mapRecommend(it)
                    }
                    this.body = body
                    body
                }

    }


    private fun mapRecommend(productDetail: ProductDetail): ProductRecommendItemViewModel = ProductRecommendItemViewModel(productDetail.relatedPosts)

    private fun mapHeader(productDetail: ProductDetail): ProductHeaderItemViewModel = ProductHeaderItemViewModel(productDetail)

    fun commentsSelection(): Observable<List<ProductBodyItemViewModel>> {
        return bodySubject
                .subscribeOn(threadScheduler.workerThread())
                .observeOn(threadScheduler.uiThread())
    }

    fun selectCommentAt(position: Int) {

        body = body.mapIndexed { index, itemViewModel ->
            if (itemViewModel is CommentItemViewModel) {
                val viewModel = itemViewModel
                if (index == position) {
                    viewModel.copy(selected = !viewModel.isSelected)
                } else {
                    viewModel.copy(selected = false)
                }
            } else {
                return@mapIndexed itemViewModel
            }
        }
        bodySubject.onNext(body)
    }

    fun navigateVote() {
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

    fun getProduct(session: CustomTabsSession?) {
        val product = stateDispatcher.value.content
        product?.run {
            navigation.toUrl(redirectUrl, session).subscribe({}, Timber::e)
        }

    }

    fun navigateProduct(id: Int) {
        navigation.toProduct(id).subscribe({}, Timber::e)
    }

    fun navigateGallery() {
        val product = stateDispatcher.value.content
        product?.run {
            navigation.toGallery(ArrayList(media)).subscribe({}, Timber::e)
        }
    }

    fun navigateUser(user: User, option: ActivityOptions) {
        navigation.toUser(user, option)
                .subscribe(Functions.EMPTY, Timber::e)
    }

}