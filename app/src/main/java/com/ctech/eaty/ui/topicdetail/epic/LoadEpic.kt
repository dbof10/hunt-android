package com.ctech.eaty.ui.topicdetail.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.BarcodeGenerator.createTopicDetailBarCode
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.topicdetail.action.TopicDetailAction
import com.ctech.eaty.ui.topicdetail.result.LoadResult
import com.ctech.eaty.ui.topicdetail.state.TopicDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(private val productRepository: ProductRepository,
               private val threadScheduler: ThreadScheduler) : Epic<TopicDetailState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<TopicDetailState>): Observable<LoadResult> {
        return action.ofType(TopicDetailAction.Load::class.java)
                .filter {
                    state.value.content.isEmpty()
                }
                .flatMap {
                    productRepository.getPostsByTopic(createTopicDetailBarCode(it.id, 1))
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