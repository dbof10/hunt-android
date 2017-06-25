package com.ctech.eaty.ui.collectiondetail.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.CollectionRepository
import com.ctech.eaty.ui.collectiondetail.action.BarCodeGenerator
import com.ctech.eaty.ui.collectiondetail.action.CollectionDetailAction
import com.ctech.eaty.ui.collectiondetail.result.LoadResult
import com.ctech.eaty.ui.collectiondetail.state.CollectionDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(val collectionRepository: CollectionRepository,
               val barCodeGenerator: BarCodeGenerator,
               val threadScheduler: ThreadScheduler) : Epic<CollectionDetailState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<CollectionDetailState>): Observable<LoadResult> {
        return action.ofType(CollectionDetailAction.Load::class.java)
                .flatMap {
                    collectionRepository.getCollectionDetail(barCodeGenerator.currentBarCode(it.id))
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