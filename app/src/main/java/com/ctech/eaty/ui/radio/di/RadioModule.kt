package com.ctech.eaty.ui.radio.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.player.ExoMediaController
import com.ctech.eaty.player.MediaController
import com.ctech.eaty.repository.RadioRepository
import com.ctech.eaty.ui.radio.action.BarCodeGenerator
import com.ctech.eaty.ui.radio.epic.LoadEpic
import com.ctech.eaty.ui.radio.reducer.RadioReducer
import com.ctech.eaty.ui.radio.state.RadioState
import com.ctech.eaty.ui.radio.view.RadioActivity
import com.ctech.eaty.ui.radio.viewmodel.RadioViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class RadioModule {

    @Provides
    fun provideBarCodeGenerator(): BarCodeGenerator {
        return BarCodeGenerator()
    }

    @ActivityScope
    @Provides
    fun provideRadioStore(radioRepository: RadioRepository, barCodeGenerator: BarCodeGenerator,
                          threadScheduler: ThreadScheduler): Store<RadioState> {
        return Store<RadioState>(RadioState(), RadioReducer(), arrayOf(LoadEpic(radioRepository, barCodeGenerator, threadScheduler)))

    }

    @ActivityScope
    @Provides
    fun provideRadioController(context: RadioActivity): MediaController<SimpleExoPlayerView> {
        return ExoMediaController(context)

    }

    @ActivityScope
    @Provides
    fun provideRadioViewModel(store: Store<RadioState>, radioController: MediaController<SimpleExoPlayerView>): RadioViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return RadioViewModel(state, radioController)
    }
}