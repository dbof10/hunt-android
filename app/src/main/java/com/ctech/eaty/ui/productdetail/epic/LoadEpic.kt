package com.ctech.eaty.ui.productdetail.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.productdetail.action.BarCodeGenerator
import com.ctech.eaty.ui.productdetail.action.ProductDetailAction
import com.ctech.eaty.ui.productdetail.result.LoadResult
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(private val productRepository: ProductRepository,
               private val barCodeGenerator: BarCodeGenerator,
               private val threadScheduler: ThreadScheduler) : Epic<ProductDetailState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<ProductDetailState>): Observable<LoadResult> {
        return action.ofType(ProductDetailAction.Load::class.java)
                .flatMap {
                    productRepository.getProductDetail(barCodeGenerator.get(it.id))
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