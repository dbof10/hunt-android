package com.ctech.eaty.base.redux

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

interface Epic<State>{

    fun apply(action: PublishSubject<Action>, state: BehaviorSubject<State>): Observable<out Result>
}