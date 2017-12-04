package com.ctech.eaty.ui.topiclist.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.topiclist.action.BarCodeGenerator
import com.ctech.eaty.ui.topiclist.action.TopicList
import com.ctech.eaty.ui.topiclist.result.LoadResult
import com.ctech.eaty.ui.topiclist.state.SearchState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(val productRepository: ProductRepository,
               val barCodeGenerator: BarCodeGenerator,
               val threadScheduler: ThreadScheduler) : Epic<SearchState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<SearchState>): Observable<LoadResult> {
        return action.ofType(TopicList.Load::class.java)
                .filter {
                    state.value.content.isEmpty()
                }
                .flatMap {
                    productRepository.getPostsByTopic(barCodeGenerator.currentBarCode(it.id))
                            .map {
                                LoadResult.success(it.map { ProductItemViewModel(it) })
                            }
                            .onErrorReturn {
                                LoadResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadResult.inProgress())
                }
    }
}