package com.ctech.eaty.ui.productdetail.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.repository.VoteRepository
import com.ctech.eaty.ui.productdetail.epic.LikeEpic
import com.ctech.eaty.ui.productdetail.epic.LoadEpic
import com.ctech.eaty.ui.productdetail.epic.UnlikeEpic
import com.ctech.eaty.ui.productdetail.navigation.ProductDetailNavigation
import com.ctech.eaty.ui.productdetail.reducer.ProductDetailReducer
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import com.ctech.eaty.ui.productdetail.viewmodel.ProductDetailViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides


@Module
class ProductDetailModule {

    @ActivityScope
    @Provides
    fun provideProductDetailStore(productRepository: ProductRepository,
                                  voteRepository: VoteRepository,
                                  userRepository: UserRepository,
                                  threadScheduler: ThreadScheduler): Store<ProductDetailState> {
        return Store<ProductDetailState>(ProductDetailState(), ProductDetailReducer(),
                arrayOf(LoadEpic(productRepository, threadScheduler),
                        LikeEpic(voteRepository, userRepository, productRepository,threadScheduler),
                        UnlikeEpic(voteRepository, userRepository,productRepository, threadScheduler)))

    }

    @ActivityScope
    @Provides
    fun provideProductDetailViewModel(store: Store<ProductDetailState>, navigation: ProductDetailNavigation,
                                      threadScheduler: ThreadScheduler): ProductDetailViewModel {
        return ProductDetailViewModel(store.state, navigation, threadScheduler)
    }

    @ActivityScope
    @Provides
    fun provideCustomTabHelper(): CustomTabActivityHelper {
        return CustomTabActivityHelper()
    }
}