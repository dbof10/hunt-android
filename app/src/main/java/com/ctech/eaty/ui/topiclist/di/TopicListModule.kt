package com.ctech.eaty.ui.topiclist.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.topiclist.action.BarCodeGenerator
import com.ctech.eaty.ui.topiclist.epic.LoadEpic
import com.ctech.eaty.ui.topiclist.epic.LoadMoreEpic
import com.ctech.eaty.ui.topiclist.reducer.SearchReducer
import com.ctech.eaty.ui.topiclist.state.SearchState
import com.ctech.eaty.ui.topiclist.viewmodel.SearchViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class TopicListModule {

    @Provides
    fun provideBarCodeGenerator(): BarCodeGenerator {
        return BarCodeGenerator()
    }

    @ActivityScope
    @Provides
    fun provideSearchStore(productRepository: ProductRepository, barCodeGenerator: BarCodeGenerator,
                           threadScheduler: ThreadScheduler): Store<SearchState> {
        return Store(SearchState(), SearchReducer(), arrayOf(LoadEpic(productRepository, barCodeGenerator, threadScheduler),
                LoadMoreEpic(productRepository, barCodeGenerator, threadScheduler)))

    }

    @Provides
    fun provideSearchViewModel(store: Store<SearchState>): SearchViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return SearchViewModel(state)
    }
}