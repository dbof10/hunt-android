package com.ctech.eaty.ui.topic.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.TopicRepository
import com.ctech.eaty.ui.topic.epic.LoadEpic
import com.ctech.eaty.ui.topic.epic.LoadMoreEpic
import com.ctech.eaty.ui.topic.reducer.TopicReducer
import com.ctech.eaty.ui.topic.state.TopicState
import com.ctech.eaty.ui.topic.viewmodel.TopicViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class TopicModule {


    @ActivityScope
    @Provides
    fun provideCommentStore(topicRepository: TopicRepository,
                            threadScheduler: ThreadScheduler): Store<TopicState> {
        return Store<TopicState>(TopicState(), TopicReducer(), arrayOf(LoadEpic(topicRepository, threadScheduler),
                LoadMoreEpic(topicRepository, threadScheduler)))

    }

    @Provides
    fun provideCommentViewModel(store: Store<TopicState>): TopicViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return TopicViewModel(state)
    }
}