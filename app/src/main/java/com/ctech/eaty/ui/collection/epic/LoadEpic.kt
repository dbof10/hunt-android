package com.ctech.eaty.ui.collection.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.BarcodeGenerator.createCollectionListBarCode
import com.ctech.eaty.repository.CollectionRepository
import com.ctech.eaty.ui.collection.action.CollectionAction
import com.ctech.eaty.ui.collection.result.LoadResult
import com.ctech.eaty.ui.collection.state.CollectionState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(private val collectionRepository: CollectionRepository,
               private val threadScheduler: ThreadScheduler) : Epic<CollectionState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<CollectionState>): Observable<LoadResult> {
        return action.filter {
            it == CollectionAction.LOAD
        }
                .filter {
                    state.value.content.isEmpty()
                }
                .flatMap {
                    collectionRepository.getCollections(createCollectionListBarCode(0))
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