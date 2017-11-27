package com.ctech.eaty.ui.search.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.SearchRepository
import com.ctech.eaty.ui.search.epic.LoadEpic
import com.ctech.eaty.ui.search.epic.LoadMoreEpic
import com.ctech.eaty.ui.search.navigation.SearchNavigation
import com.ctech.eaty.ui.search.reducer.SearchReducer
import com.ctech.eaty.ui.search.state.SearchState
import com.ctech.eaty.ui.search.viewmodel.SearchViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class SearchModule {


    @ActivityScope
    @Provides
    fun provideSearchStore(searchRepository: SearchRepository,
                           threadScheduler: ThreadScheduler): Store<SearchState> {
        return Store(SearchState(), SearchReducer(), arrayOf(LoadEpic(searchRepository, threadScheduler),
                LoadMoreEpic(searchRepository, threadScheduler)))

    }

    @Provides
    fun provideSearchViewModel(store: Store<SearchState>,
                               navigation: SearchNavigation): SearchViewModel {
        return SearchViewModel(store.state.observeOn(AndroidSchedulers.mainThread()), navigation)
    }
}