package com.ctech.eaty.ui.search.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.search.action.BarCodeGenerator
import com.ctech.eaty.ui.search.epic.LoadEpic
import com.ctech.eaty.ui.search.epic.LoadMoreEpic
import com.ctech.eaty.ui.search.reducer.SearchReducer
import com.ctech.eaty.ui.search.state.SearchState
import com.ctech.eaty.ui.search.viewmodel.SearchViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class SearchModule {

    @Provides
    fun provideBarCodeGenerator(): BarCodeGenerator {
        return BarCodeGenerator()
    }

    @ActivityScope
    @Provides
    fun provideSearchStore(productRepository: ProductRepository, barCodeGenerator: BarCodeGenerator,
                           threadScheduler: ThreadScheduler): Store<SearchState> {
        return Store<SearchState>(SearchState(), SearchReducer(), arrayOf(LoadEpic(productRepository, barCodeGenerator, threadScheduler),
                LoadMoreEpic(productRepository, barCodeGenerator, threadScheduler)))

    }

    @Provides
    fun provideSearchViewModel(store: Store<SearchState>): SearchViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return SearchViewModel(state)
    }
}