package com.ctech.eaty.ui.productdetail.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.error.LikeExistedException
import com.ctech.eaty.error.UnauthorizedActionException
import com.ctech.eaty.repository.BarcodeGenerator.createProductDetailBarCode
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.repository.VoteRepository
import com.ctech.eaty.ui.productdetail.action.Like
import com.ctech.eaty.ui.productdetail.result.LikeResult
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException

class LikeEpic(private val voteRepository: VoteRepository,
               private val userRepository: UserRepository,
               private val productRepository: ProductRepository,
               private val threadScheduler: ThreadScheduler) : Epic<ProductDetailState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<ProductDetailState>): Observable<LikeResult> {
        return action.ofType(Like::class.java)
                .flatMap {
                    val id = it.id
                    userRepository.getUser().flatMap {
                        if (it != UserDetail.GUEST) {
                            voteRepository
                                    .like(id)
                                    .doOnNext {
                                        productRepository.purgeProductDetail(createProductDetailBarCode(id))
                                    }
                                    .map {
                                        LikeResult.success(it)
                                    }
                                    .onErrorReturn {
                                        if (it is HttpException) {
                                            if (it.response().code() == 422) {
                                                return@onErrorReturn LikeResult.fail(LikeExistedException())
                                            }
                                        }
                                        return@onErrorReturn LikeResult.fail(it)
                                    }
                                    .subscribeOn(threadScheduler.workerThread())
                                    .startWith(LikeResult.inProgress())
                        } else {
                            Observable.just(LikeResult.fail(UnauthorizedActionException()))
                        }
                    }
                }
    }
}