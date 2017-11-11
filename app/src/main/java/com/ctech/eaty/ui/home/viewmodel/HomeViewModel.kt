package com.ctech.eaty.ui.home.viewmodel

import android.view.View
import android.widget.ImageView
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.home.navigation.HomeNavigation
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.util.rx.Functions
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

    fun loadingMore(): Observable<List<HomeFeed>> {
        return stateDispatcher
                .filter { it.loadingMore }
                .map { it.content }
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

    fun loadMoreError(): Observable<List<HomeFeed>> {
        return stateDispatcher
                .filter { it.loadMoreError != null }
                .map { it.content }
    }

    fun refreshSuccess(): Observable<List<HomeFeed>> {
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
                .map { it.content }
    }

    fun content(): Observable<List<HomeFeed>> {
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
                .subscribe(Functions.EMPTY, Timber::e)
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
                .subscribe(Functions.EMPTY, Timber::e)
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

    fun navigateSearch(it: View) {
        navigation.toSearch(it)
                .subscribe()
    }
}