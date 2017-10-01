package com.ctech.eaty.util.rx


import com.ctech.eaty.error.NoConnectionException
import com.ctech.eaty.util.NetworkManager
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer


class NetworkObservableTransformer<Upstream>(private val networkManager: NetworkManager) : ObservableTransformer<Upstream, Upstream> {

    override fun apply(upstream: Observable<Upstream>): Observable<Upstream> {
        if (networkManager.isConnected()) {
            return upstream
        }
        return Observable.error(NoConnectionException())
    }

}

class NetworkSingleTransformer<Upstream>(private val networkManager: NetworkManager) : SingleTransformer<Upstream, Upstream> {

    override fun apply(upstream: Single<Upstream>): Single<Upstream> {
        if (networkManager.isConnected()) {
            return upstream
        }
        return Single.error(NoConnectionException())
    }

}

