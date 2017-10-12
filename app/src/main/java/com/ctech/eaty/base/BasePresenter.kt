package com.ctech.eaty.base

import android.support.annotation.UiThread
import java.lang.ref.WeakReference


open class BasePresenter<V : MvpView> {

    private var viewRef: WeakReference<V>? = null
    val view: V? @UiThread get() = viewRef?.get()

    @UiThread
    fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    @UiThread
    fun detachView() {
        viewRef?.clear()
    }
}
