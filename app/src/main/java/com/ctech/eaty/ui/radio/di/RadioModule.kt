package com.ctech.eaty.ui.radio.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.RadioRepository
import com.ctech.eaty.ui.radio.action.BarCodeGenerator
import com.ctech.eaty.ui.radio.controller.RadioController
import com.ctech.eaty.ui.radio.epic.LoadEpic
import com.ctech.eaty.ui.radio.reducer.RadioReducer
import com.ctech.eaty.ui.radio.state.RadioState
import com.ctech.eaty.ui.radio.view.RadioActivity
import com.ctech.eaty.ui.radio.viewmodel.RadioViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
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
    fun provideRadioController(context: RadioActivity): RadioController {
        return RadioController(context)

    }

    @ActivityScope
    @Provides
    fun provideRadioViewModel(store: Store<RadioState>, radioController: RadioController): RadioViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return RadioViewModel(state, radioController)
    }
}