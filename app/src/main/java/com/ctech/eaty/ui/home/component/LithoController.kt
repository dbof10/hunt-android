package com.ctech.eaty.ui.home.component

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.HomeFeed
import com.ctech.eaty.ui.home.viewmodel.HomeViewModel
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.sections.widget.RecyclerCollectionEventsController
import javax.inject.Inject


@ActivityScope
class LithoController @Inject constructor(private val context: ComponentContext,
                                          private val viewModel: HomeViewModel,
                                          private val store: Store<HomeState>) {

    private lateinit var lithoView: LithoView
    private val eventsController = RecyclerCollectionEventsController()

    fun take(lithoView: LithoView) {
        this.lithoView = lithoView
        internalSetup()
    }

    private fun internalSetup() {
        viewModel.content().subscribe { renderContent(it) }
        viewModel.refreshSuccess().subscribe { refreshSuccess() }
    }

    private fun refreshSuccess() {
        eventsController.clearRefreshing()
    }

    private fun renderContent(items: List<HomeFeed>) {
        val rootComponent = HomeListComponent.create(context)
                .dataSource(items)
                .store(store)
                .eventsController(eventsController)
                .build()
        lithoView.setComponentAsync(rootComponent)
    }


}