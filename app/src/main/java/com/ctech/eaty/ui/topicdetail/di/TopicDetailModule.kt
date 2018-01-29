package com.ctech.eaty.ui.topicdetail.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.topicdetail.epic.LoadEpic
import com.ctech.eaty.ui.topicdetail.epic.LoadMoreEpic
import com.ctech.eaty.ui.topicdetail.reducer.TopicDetailReducer
import com.ctech.eaty.ui.topicdetail.state.TopicDetailState
import com.ctech.eaty.ui.topicdetail.viewmodel.TopicDetailViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class TopicDetailModule {

    @ActivityScope
    @Provides
    fun provideSearchStore(productRepository: ProductRepository,
                           threadScheduler: ThreadScheduler): Store<TopicDetailState> {
        return Store<TopicDetailState>(TopicDetailState(), TopicDetailReducer(), arrayOf(LoadEpic(productRepository, threadScheduler),
                LoadMoreEpic(productRepository, threadScheduler)))

    }

    @Provides
    fun provideSearchViewModel(store: Store<TopicDetailState>): TopicDetailViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return TopicDetailViewModel(state)
    }
}