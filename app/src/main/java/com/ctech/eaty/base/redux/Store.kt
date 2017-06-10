package com.ctech.eaty.base.redux

import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class Store<State> constructor(initState: State, private val reducer: Reducer<State>, effects: Array<Epic<State>>) {
    private val actionDispatcher: PublishSubject<Action> = PublishSubject.create()
    private val state: BehaviorSubject<State> = BehaviorSubject.createDefault(initState)
    private val result: Observable<Result>
    private lateinit var disposable: Disposable

    init {
        this.result = Observable.fromArray<Epic<State>>(*effects)
                .flatMap {
                    it.apply(actionDispatcher, state)
                }
    }

    fun dispatch(action: Action) {
        actionDispatcher.onNext(action)
    }

    fun getState(): Observable<State> {
        return state.hide()
    }

    fun startBinding() {
        disposable = result
                .scan(state.value, reducer)
                .subscribe {
                    state.onNext(it)
                }
    }

    fun stopBinding() {
        disposable.dispose()
    }


}