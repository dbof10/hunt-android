package com.ctech.eaty.ui.home.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.HomeRepository
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.action.BarCodeGenerator
import com.ctech.eaty.ui.home.result.RefreshResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class RefreshEpic(val homeRepository: HomeRepository,
                  val barCodeGenerator: BarCodeGenerator,
                  val threadScheduler: ThreadScheduler) : Epic<HomeState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<HomeState>): Observable<RefreshResult> {
        return action.filter {
            it == HomeAction.REFRESH
        }.flatMap {
            homeRepository.getHomePosts(barCodeGenerator.currentBarCode, true)
                    .map {
                        RefreshResult.success(emptyList())
                    }
                    .onErrorReturn {
                        RefreshResult.fail(it)
                    }
                    .subscribeOn(threadScheduler.workerThread())
                    .startWith(RefreshResult.inProgress())
        }
    }
}