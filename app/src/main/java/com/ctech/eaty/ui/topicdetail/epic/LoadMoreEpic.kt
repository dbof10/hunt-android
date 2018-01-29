package com.ctech.eaty.ui.topicdetail.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.BarcodeGenerator.createTopicDetailBarCode
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.topicdetail.action.TopicDetailAction
import com.ctech.eaty.ui.topicdetail.result.LoadMoreResult
import com.ctech.eaty.ui.topicdetail.state.TopicDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadMoreEpic(private val productRepository: ProductRepository,
                   private val threadScheduler: ThreadScheduler) : Epic<TopicDetailState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<TopicDetailState>): Observable<LoadMoreResult> {
        return action.ofType(TopicDetailAction.LoadMore::class.java)
                .filter {
                    !state.value.loadingMore
                }
                .flatMap {
                    val page = state.value.page + 1
                    productRepository.getPostsByTopic(createTopicDetailBarCode(it.id, page))
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