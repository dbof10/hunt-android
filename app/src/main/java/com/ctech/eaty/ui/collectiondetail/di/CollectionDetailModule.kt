package com.ctech.eaty.ui.collectiondetail.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.CollectionRepository
import com.ctech.eaty.ui.collectiondetail.epic.LoadEpic
import com.ctech.eaty.ui.collectiondetail.reducer.CollectionDetailReducer
import com.ctech.eaty.ui.collectiondetail.state.CollectionDetailState
import com.ctech.eaty.ui.collectiondetail.viewmodel.CollectionDetailViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class CollectionDetailModule {

    @ActivityScope
    @Provides
    fun provideCollectionDetailStore(collectionRepository: CollectionRepository,
                                     threadScheduler: ThreadScheduler): Store<CollectionDetailState> {
        return Store<CollectionDetailState>(CollectionDetailState(), CollectionDetailReducer(),
                arrayOf(LoadEpic(collectionRepository, threadScheduler)))

    }

    @ActivityScope
    @Provides
    fun provideCollectionDetailViewModel(store: Store<CollectionDetailState>): CollectionDetailViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return CollectionDetailViewModel(state)
    }
}