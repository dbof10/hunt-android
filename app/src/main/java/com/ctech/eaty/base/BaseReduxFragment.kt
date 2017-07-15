package com.ctech.eaty.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.ctech.eaty.base.redux.LifeCycleDelegate
import com.ctech.eaty.base.redux.Store
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseReduxFragment<State> : Fragment() {

    private lateinit var lifecycleDelegate: LifeCycleDelegate<State>
    private val disposables = CompositeDisposable()


    protected abstract fun store(): Store<State>

    protected fun disposeOnStop(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleDelegate = LifeCycleDelegate(store())

    }

    override fun onStart() {
        super.onStart()
        lifecycleDelegate.onStart()
    }

    override fun onStop() {
        lifecycleDelegate.onStop(activity.isFinishing)
        disposables.clear()
        super.onStop()
    }
}
