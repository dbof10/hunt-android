package com.ctech.eaty.ui.home.component

import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.*
import com.ctech.eaty.util.DiffInfer
import com.ctech.eaty.widget.recyclerview.InfiniteScrollListener
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.LinearLayoutInfo
import com.facebook.litho.widget.RecyclerBinder
import com.facebook.litho.widget.RecyclerBinderUpdateCallback
import vn.tiki.noadapter2.DiffCallback
import javax.inject.Inject


@ActivityScope
class LithoController @Inject constructor(private val context: ComponentContext,
                                          private val viewModel: HomeViewModel,
                                          private val store: Store<HomeState>) {

    private lateinit var lithoView: LithoView
    private var items: List<HomeItemViewModel> = emptyList()

    private val diffInfer by lazy {
        DiffInfer(object : DiffCallback {

            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                if (oldItem is SectionViewModel && newItem is SectionViewModel) {
                    return oldItem.id == newItem.id
                } else if (oldItem is ProductItemViewModel && newItem is ProductItemViewModel) {
                    return oldItem.id == newItem.id
                } else if (oldItem is HorizontalAdsItemViewModel && newItem is HorizontalAdsItemViewModel) {
                    return oldItem.id == newItem.id
                }
                return false
            }

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

        })
    }

    private val componentRenderer = RecyclerBinderUpdateCallback.ComponentRenderer<HomeItemViewModel> { item, index ->
        val renderInfoBuilder = ComponentRenderInfo.create()
        when (item) {
            is ProductItemViewModel -> {
                renderInfoBuilder.component(
                        ProductComponent
                                .create(context)
                                .arg1(item)
                                .key(item.id.toString())
                                .build())
                        .build()
            }
            is SectionViewModel -> {
                renderInfoBuilder.component(
                        SectionComponent
                                .create(context)
                                .arg1(item)
                                .key(item.id.toString())
                                .build()
                )
                        .isSticky(true)
                        .build()
            }
            else -> renderInfoBuilder.build()
        }
    }

    private val layoutManager = LinearLayoutManager(context)

    private val loadMoreCallback by lazy {
        InfiniteScrollListener(layoutManager, 3) {
            store.dispatch(HomeAction.LOAD_MORE)
        }
    }

    private val recyclerBinder by lazy {
        RecyclerBinder.Builder()
                .canPrefetchDisplayLists(true)
                .layoutInfo(LinearLayoutInfo(layoutManager))
                .build(context)
    }

    fun take(lithoView: LithoView) {
        this.lithoView = lithoView
        internalSetup()
    }

    private fun internalSetup() {
        val rootComponent = HomeListComponent.create(context)
                .arg1(recyclerBinder)
                .arg2(loadMoreCallback)
                .build()
        lithoView.setComponentAsync(rootComponent)
        viewModel.content().subscribe { renderContent(it) }
    }

    private fun renderContent(items: List<HomeItemViewModel>) {
        diffInfer.setItems(this.items)
        diffInfer.setNewItems(items)

        val diffResult = DiffUtil.calculateDiff(diffInfer)
        val callback = RecyclerBinderUpdateCallback.acquire(
                this.items.size,
                items,
                componentRenderer,
                recyclerBinder)

        diffResult.dispatchUpdatesTo(callback)
        callback.applyChangeset()
        RecyclerBinderUpdateCallback.release(callback)
        this.items = items
    }

    fun resetRefreshState() {
        loadMoreCallback.resetState()
    }

}