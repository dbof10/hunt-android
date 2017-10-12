package com.ctech.eaty.base

import android.os.Bundle
import android.view.View
import com.ctech.eaty.base.redux.Store
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposeWith
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseReduxFragment<State> : BaseFragment() {

    protected val disposables = CompositeDisposable()

    protected abstract fun store(): Store<State>

    protected fun disposeOnStop(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store().startBinding()
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe()
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()

    }

}
