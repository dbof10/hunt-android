package com.ctech.eaty.ui.topic.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.BarcodeGenerator.createTopicListBarCode
import com.ctech.eaty.repository.TopicRepository
import com.ctech.eaty.ui.topic.action.TopicAction
import com.ctech.eaty.ui.topic.result.LoadResult
import com.ctech.eaty.ui.topic.state.TopicState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(private val topicRepository: TopicRepository,
               private val threadScheduler: ThreadScheduler) : Epic<TopicState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<TopicState>): Observable<LoadResult> {
        return action.filter {
            it == TopicAction.LOAD
        }
                .filter {
                    state.value.content.isEmpty()
                }
                .flatMap {
                    topicRepository.getTopics(createTopicListBarCode(0))
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