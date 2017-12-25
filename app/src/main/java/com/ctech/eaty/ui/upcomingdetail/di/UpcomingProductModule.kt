package com.ctech.eaty.ui.upcomingdetail.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.upcomingdetail.epic.LoadEpic
import com.ctech.eaty.ui.upcomingdetail.epic.SubscribeEpic
import com.ctech.eaty.ui.upcomingdetail.navigation.UpcomingDetailNavigation
import com.ctech.eaty.ui.upcomingdetail.reducer.UpcomingProductReducer
import com.ctech.eaty.ui.upcomingdetail.state.UpcomingProductState
import com.ctech.eaty.ui.upcomingdetail.viewmodel.UpcomingDetailViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class UpcomingProductModule {


    @ActivityScope
    @Provides
    fun provideCustomTabHelper(): CustomTabActivityHelper {
        return CustomTabActivityHelper()
    }

    @ActivityScope
    @Provides
    fun provideUpcomingDetailStore(productRepository: ProductRepository,
                                   threadScheduler: ThreadScheduler): Store<UpcomingProductState> {
        return Store<UpcomingProductState>(UpcomingProductState(), UpcomingProductReducer(),
                arrayOf(LoadEpic(productRepository, threadScheduler),
                        SubscribeEpic(productRepository, threadScheduler)))

    }

    @ActivityScope
    @Provides
    fun provideUpcomingDetailViewModel(store: Store<UpcomingProductState>,
                                       navigation: UpcomingDetailNavigation): UpcomingDetailViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return UpcomingDetailViewModel(state, navigation)
    }

}