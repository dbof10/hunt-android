package com.ctech.eaty.util.rx


import com.ctech.eaty.error.NoConnectionException
import com.ctech.eaty.util.NetworkManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer


class NetworkCheckTransformer(private val networkManager: NetworkManager) : ObservableTransformer<Any, Any> {

    override fun apply(upstream: Observable<Any>): ObservableSource<Any> {
        if (networkManager.isConnected()) {
            return upstream
        }
        return Observable.error(NoConnectionException())
    }

}
