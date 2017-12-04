package com.ctech.eaty.ui.topiclist.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.topiclist.action.BarCodeGenerator
import com.ctech.eaty.ui.topiclist.action.TopicList
import com.ctech.eaty.ui.topiclist.result.LoadMoreResult
import com.ctech.eaty.ui.topiclist.state.SearchState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadMoreEpic(val productRepository: ProductRepository,
                   val barCodeGenerator: BarCodeGenerator,
                   val threadScheduler: ThreadScheduler) : Epic<SearchState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<SearchState>): Observable<LoadMoreResult> {
        return action.ofType(TopicList.LoadMore::class.java)
                .filter {
                    !state.value.loadingMore
                }
                .flatMap {
                    val page = state.value.page + 1
                    productRepository.getPostsByTopic(barCodeGenerator.generateNextBarCode(it.id, page))
                            .map {
                                LoadMoreResult.success(page, it.map { ProductItemViewModel(it) })
                            }
                            .onErrorReturn {
                                LoadMoreResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadMoreResult.inProgress())
                }
    }
}