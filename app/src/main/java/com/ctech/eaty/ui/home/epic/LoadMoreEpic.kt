package com.ctech.eaty.ui.home.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.action.BarCodeGenerator
import com.ctech.eaty.ui.home.result.LoadMoreResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadMoreEpic(val productRepository: ProductRepository,
                   val barCodeGenerator: BarCodeGenerator,
                   val threadScheduler: ThreadScheduler) : Epic<HomeState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<HomeState>): Observable<LoadMoreResult> {
        return action.filter {
            it == HomeAction.LOAD_MORE
        }.filter {
            !state.value.loadingMore
        }.flatMap {
            val dayAgo = state.value.dayAgo + 1
            productRepository.getHomePosts(barCodeGenerator.generateNextBarCode(dayAgo))
                    .map {
                        LoadMoreResult.success(dayAgo, it.map { ProductItemViewModel(it) })
                    }
                    .onErrorReturn {
                        LoadMoreResult.fail(it)
                    }
                    .subscribeOn(threadScheduler.workerThread())
                    .startWith(LoadMoreResult.inProgress())
        }
    }
}