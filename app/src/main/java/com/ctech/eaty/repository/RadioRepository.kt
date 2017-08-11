package com.ctech.eaty.repository

import com.ctech.eaty.response.RadioResponse
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Observable

class RadioRepository(private val store: Store<RadioResponse, BarCode>) {
    fun getTracks(barcode: BarCode): Observable<RadioResponse> {
        return store.get(barcode).toObservable()
    }
}