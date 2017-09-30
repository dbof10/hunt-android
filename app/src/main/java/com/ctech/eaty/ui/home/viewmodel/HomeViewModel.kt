package com.ctech.eaty.ui.home.viewmodel

import android.widget.ImageView
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.home.navigation.HomeNavigation
import com.ctech.eaty.ui.home.state.HomeState
import io.reactivex.Observable
import timber.log.Timber

class HomeViewModel(private val stateDispatcher: Observable<HomeState>,
                    private val userRepository: UserRepository,
                    private val navigation: HomeNavigation) {
    fun loading(): Observable<HomeState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun refreshing(): Observable<HomeState> {
        return stateDispatcher
                .filter { it.refreshing }
    }

    fun loadingMore(): Observable<HomeState> {
        return stateDispatcher
                .filter { it.loadingMore }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null }
                .map { it.loadError }
    }

    fun refreshError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.refreshError != null }
                .map { it.refreshError }
    }

    fun loadMoreError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadMoreError != null }
                .map { it.loadMoreError }
    }

    fun refreshSuccess(): Observable<HomeState> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && !it.refreshing
                            && !it.loadingMore
                            && it.loadError == null
                            && it.refreshError == null
                            && it.loadMoreError == null
                            && it.dayAgo == 0
                }
    }

    fun content(): Observable<List<HomeItemViewModel>> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && !it.refreshing
                            && !it.loadingMore
                            && it.loadError == null
                            && it.refreshError == null
                            && it.loadMoreError == null
                            && it.content.isNotEmpty()
                }
                .map { it.content }
    }

    fun userNavigation(ivAvatar: ImageView) {
        userRepository
                .getUser()
                .flatMapCompletable {
                    if (it == UserDetail.GUEST) {
                        navigation.toLogin(ivAvatar)
                    } else {
                        navigation.toUser(it)
                    }
                }
                .subscribe({}, Timber::e)
    }

    fun notificationNavigation() {
        userRepository
                .getUser()
                .flatMapCompletable {
                    if (it == UserDetail.GUEST) {
                        navigation.toLogin()
                    } else {
                        navigation.toNotification()
                    }
                }
                .subscribe({}, Timber::e)
    }

    fun user(): Observable<UserDetail> {
        return stateDispatcher
                .filter {
                    it.user != null && it.user != UserDetail.GUEST
                }
                .map { it.user }
    }

    fun navigationClick(id: Int) {
        navigation.delegate(id).subscribe()
    }
}