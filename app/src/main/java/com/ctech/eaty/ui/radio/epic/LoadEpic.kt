package com.ctech.eaty.ui.radio.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.RadioRepository
import com.ctech.eaty.ui.radio.action.BarCodeGenerator
import com.ctech.eaty.ui.radio.action.RadioAction
import com.ctech.eaty.ui.radio.result.LoadResult
import com.ctech.eaty.ui.radio.state.RadioState
import com.ctech.eaty.ui.radio.viewmodel.TrackItemViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(val radioRepository: RadioRepository,
               val barCodeGenerator: BarCodeGenerator,
               val threadScheduler: ThreadScheduler) : Epic<RadioState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<RadioState>): Observable<LoadResult> {
        return action
                .filter {
                    it == RadioAction.LOAD
                }
                .flatMap {
                    radioRepository.getTracks(barCodeGenerator.get("33463205"))
                            .map {
                                LoadResult.success(it)
                            }
                            .onErrorReturn {
                                LoadResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadResult.inProgress())
                }
    }
}