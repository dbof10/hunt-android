package com.ctech.eaty.ui.user.viewmodel

import android.view.View
import android.widget.Button
import com.ctech.eaty.R
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.user.navigation.UserDetailNavigation
import com.ctech.eaty.ui.user.state.UserDetailState
import com.ctech.eaty.util.ResourceProvider
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class UserDetailViewModel(private val stateDispatcher: BehaviorSubject<UserDetailState>,
                          private val userRepository: UserRepository,
                          private val navigation: UserDetailNavigation,
                          private val threadScheduler: ThreadScheduler,
                          private val resourceProvider: ResourceProvider) {


    fun loadingUser(): Observable<UserDetailState> = stateDispatcher
            .filter { it.loading && it.user == UserDetail.GUEST }


    fun loadUserError(): Observable<Throwable> = stateDispatcher
            .filter { it.loadError != null }
            .map {
                it.loadError!!
            }


    private fun content(): Observable<UserDetail> = stateDispatcher
            .filter {
                !it.loading && it.loadError == null
            }
            .map {
                it.user ?: UserDetail.GUEST
            }
            .observeOn(threadScheduler.uiThread())


    fun header(): Observable<UserDetail> = content()
            .filter {
                it != UserDetail.GUEST
            }
            .observeOn(threadScheduler.uiThread())


    fun loadingProduct(): Observable<UserDetailState> = stateDispatcher
            .filter { it.loadingProduct }
            .observeOn(threadScheduler.uiThread())


    fun loadProductError(): Observable<Throwable> = stateDispatcher
            .filter { it.loadProductError != null }
            .map {
                it.loadProductError!!
            }
            .observeOn(threadScheduler.uiThread())

    fun loadingMoreProduct(): Observable<UserDetailState> = stateDispatcher
            .filter { it.loadingMoreProduct }
            .observeOn(threadScheduler.uiThread())


    fun loadMoreProductError(): Observable<Throwable> = stateDispatcher
            .filter { it.loadMoreProductError != null }
            .map {
                it.loadMoreProductError!!
            }
            .observeOn(threadScheduler.uiThread())


    fun body(): Observable<List<ProductItemViewModel>> = stateDispatcher
            .filter {
                !it.loadingProduct
                        && it.loadProductError == null
                        && !it.loadingMoreProduct
                        && it.loadMoreProductError == null
                        && it.products.isNotEmpty()
            }
            .map {
                it.products.map { ProductItemViewModel(it) }
            }
            .observeOn(threadScheduler.uiThread())


    fun loadingRelationship(): Observable<UserDetailState> = stateDispatcher
            .filter { it.loadingRL }
            .observeOn(threadScheduler.uiThread())


    fun loadRelationshipError(): Observable<Throwable> = stateDispatcher
            .filter { it.loadRLError != null }
            .map {
                it.loadRLError!!
            }
            .observeOn(threadScheduler.uiThread())


    fun relationship(): Observable<FollowButtonViewModel> = stateDispatcher
            .filter {
                it.following != null
            }
            .map {
                it.following!!
            }
            .map {
                val stringId = if (it) R.string.unfollow else R.string.follow
                FollowButtonViewModel(View.VISIBLE, it, resourceProvider.getString(stringId))
            }
            .observeOn(threadScheduler.uiThread())


    fun checkRelationship(userId: Int): Maybe<FollowButtonViewModel> {
        return Maybe.create { emitter ->
            userRepository.getUser()
                    .subscribe({
                        if (it == UserDetail.GUEST) {
                            emitter.onSuccess(FollowButtonViewModel(View.VISIBLE, false, resourceProvider.getString(R.string.follow)))
                        } else if (it.id == userId) {
                            emitter.onSuccess(FollowButtonViewModel(View.INVISIBLE))
                        } else {
                            emitter.onComplete()
                        }
                    }, Timber::e)
        }
    }

    fun followNavigation(btFollow: Button): Maybe<Any> {
        return Maybe.create { emitter ->
            userRepository.getUser()
                    .subscribe({
                        if (it == UserDetail.GUEST) {
                            navigation.toLogin(btFollow).subscribe {
                                emitter.onComplete()
                            }
                        } else {
                            emitter.onSuccess(Any())
                        }
                    }, Timber::e)
        }

    }

    fun navigateProduct(id: Int) {
        navigation.toProduct(id)
                .subscribe({}, Timber::e)
    }

    fun navigateFollower(userId: Int) {
        val user = stateDispatcher.value.user
        user?.run {
            navigation.toFollowers(userId, followerCount)
                    .subscribe({}, Timber::e)
        }

    }

    fun navigateFollowing(userId: Int) {
        val user = stateDispatcher.value.user
        user?.run {
            navigation.toFollowing(userId, followingCount)
                    .subscribe({}, Timber::e)
        }

    }

}