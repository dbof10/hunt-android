package com.ctech.eaty.base.redux

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class Store<State> constructor(initState: State, private val reducer: Reducer<State>, effects: Array<Epic<State>>) {
    val state: BehaviorSubject<State> = BehaviorSubject.createDefault(initState)
    private val actionDispatcher: PublishSubject<Action> = PublishSubject.create()
    private val result: Observable<Result>

    init {
        this.result = Observable.fromArray<Epic<State>>(*effects)
                .flatMap {
                    it.apply(actionDispatcher, state)
                }
    }


    fun dispatch(action: Action) {
        actionDispatcher.onNext(action)
    }

    fun startBinding(): Observable<State> {
        return result
                .scan(state.value, reducer)
                .doOnNext {
                    state.onNext(it)
                }

    }


}