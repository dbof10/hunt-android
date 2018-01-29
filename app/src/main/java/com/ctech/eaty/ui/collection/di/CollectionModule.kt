package com.ctech.eaty.ui.collection.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.CollectionRepository
import com.ctech.eaty.ui.collection.epic.LoadEpic
import com.ctech.eaty.ui.collection.epic.LoadMoreEpic
import com.ctech.eaty.ui.collection.reducer.CollectionReducer
import com.ctech.eaty.ui.collection.state.CollectionState
import com.ctech.eaty.ui.collection.viewmodel.CollectionViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class CollectionModule {

    @ActivityScope
    @Provides
    fun provideCommentStore(collectionRepository: CollectionRepository,
                            threadScheduler: ThreadScheduler): Store<CollectionState> {
        return Store<CollectionState>(CollectionState(), CollectionReducer(), arrayOf(LoadEpic(collectionRepository, threadScheduler),
                LoadMoreEpic(collectionRepository, threadScheduler)))

    }

    @Provides
    fun provideCommentViewModel(store: Store<CollectionState>): CollectionViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return CollectionViewModel(state)
    }
}