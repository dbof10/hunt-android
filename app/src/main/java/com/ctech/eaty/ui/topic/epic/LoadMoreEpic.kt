package com.ctech.eaty.ui.topic.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.BarcodeGenerator.createTopicListBarCode
import com.ctech.eaty.repository.TopicRepository
import com.ctech.eaty.ui.topic.action.TopicAction
import com.ctech.eaty.ui.topic.result.LoadMoreResult
import com.ctech.eaty.ui.topic.state.TopicState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadMoreEpic(private val topicRepository: TopicRepository,
                   private val threadScheduler: ThreadScheduler) : Epic<TopicState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<TopicState>): Observable<LoadMoreResult> {
        return action
                .filter {
                    it == TopicAction.LOAD_MORE
                }
                .filter {
                    !state.value.loadingMore
                }
                .flatMap {
                    val page = state.value.page + 1
                    topicRepository.getTopics(createTopicListBarCode(page))
                            .map {
                                LoadMoreResult.success(page, it)
                            }
                            .onErrorReturn {
                                LoadMoreResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadMoreResult.inProgress())
                }
    }
}