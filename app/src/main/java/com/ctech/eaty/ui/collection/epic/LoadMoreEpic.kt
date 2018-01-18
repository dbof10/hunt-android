package com.ctech.eaty.ui.collection.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.BarcodeGenerator.createCollectionListBarCode
import com.ctech.eaty.repository.CollectionRepository
import com.ctech.eaty.ui.collection.action.CollectionAction
import com.ctech.eaty.ui.collection.result.LoadMoreResult
import com.ctech.eaty.ui.collection.state.CollectionState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadMoreEpic(private val collectionRepository: CollectionRepository,
                   private val threadScheduler: ThreadScheduler) : Epic<CollectionState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<CollectionState>): Observable<LoadMoreResult> {
        return action
                .filter {
                    it == CollectionAction.LOAD_MORE
                }
                .filter {
                    !state.value.loadingMore
                }
                .flatMap {
                    val page = state.value.page + 1
                    collectionRepository.getCollections(createCollectionListBarCode(page))
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