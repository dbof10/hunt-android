package com.ctech.eaty.ui.home.controller

import android.net.NetworkInfo
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.controller.NetworkController
import com.ctech.eaty.error.NoConnectionException
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.util.rx.ThreadScheduler
import com.ctech.eaty.util.rx.plusAssign
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class HomeNetworkController @Inject constructor(private val networkController: NetworkController,
                                                private val threadScheduler: ThreadScheduler,
                                                private val store: Store<HomeState>) {
    private val disposeBag = CompositeDisposable()

    fun registerNetworkMonitor() {
        disposeBag += networkController.observeNetworkConnectivity()
                .filter(ConnectivityPredicate.hasState(NetworkInfo.State.CONNECTED))
                .filter { filterNetworkError(store.getState().loadError) }
                .subscribeOn(threadScheduler.workerThread())
                .observeOn(threadScheduler.uiThread())
                .subscribe({
                    store.dispatch(HomeAction.LOAD)
                }, Timber::e)
        disposeBag += networkController.observeNetworkConnectivity()
                .filter(ConnectivityPredicate.hasState(NetworkInfo.State.CONNECTED))
                .filter { filterNetworkError(store.getState().loadMoreError) }
                .subscribeOn(threadScheduler.workerThread())
                .observeOn(threadScheduler.uiThread())
                .subscribe({
                    store.dispatch(HomeAction.LOAD_MORE)
                }, Timber::e)

        disposeBag += networkController.observeNetworkConnectivity()
                .filter(ConnectivityPredicate.hasState(NetworkInfo.State.CONNECTED))
                .filter { filterNetworkError(store.getState().refreshError) }
                .subscribeOn(threadScheduler.workerThread())
                .observeOn(threadScheduler.uiThread())
                .subscribe({
                    store.dispatch(HomeAction.REFRESH)
                }, Timber::e)
    }

    fun unregisterNetworkMonitor() {
        disposeBag.dispose()
    }

    private fun filterNetworkError(error: Throwable?): Boolean {
        if (error != null) {
            if (error is SSLHandshakeException) {
                return true
            } else if (error is NoConnectionException) {
                return true
            }
            return false
        } else {
            return false
        }
    }
}