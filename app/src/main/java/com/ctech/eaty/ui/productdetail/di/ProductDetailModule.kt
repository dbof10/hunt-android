package com.ctech.eaty.ui.productdetail.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.repository.VoteRepository
import com.ctech.eaty.ui.productdetail.action.BarCodeGenerator
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

    @Provides
    fun provideBarCodeGenerator(): BarCodeGenerator {
        return BarCodeGenerator()
    }

    @ActivityScope
    @Provides
    fun provideProductDetailStore(productRepository: ProductRepository,
                                  voteRepository: VoteRepository,
                                  userRepository: UserRepository,
                                  barCodeGenerator: BarCodeGenerator,
                                  threadScheduler: ThreadScheduler): Store<ProductDetailState> {
        return Store<ProductDetailState>(ProductDetailState(), ProductDetailReducer(),
                arrayOf(LoadEpic(productRepository, barCodeGenerator, threadScheduler),
                        LikeEpic(voteRepository, userRepository, productRepository, barCodeGenerator,threadScheduler),
                        UnlikeEpic(voteRepository, userRepository,productRepository, barCodeGenerator, threadScheduler)))

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