package com.ctech.eaty.ui.home.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.Product
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.navigation.HomeNavigation
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.*
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.widget.recyclerview.InfiniteScrollListener
import com.ctech.eaty.widget.recyclerview.VerticalSpaceItemDecoration
import com.facebook.ads.NativeAdsManager
import kotlinx.android.synthetic.main.fragment_products.*
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class HomeFragment : BaseFragment<HomeState>(), Injectable {

    companion object {
        fun newInstance(): Fragment {

            val args = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
    lateinit var store: Store<HomeState>

    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    @Inject
    lateinit var navigator: HomeNavigation

    private val loadMoreCallback by lazy {
        InfiniteScrollListener(rvNewFeeds.layoutManager as LinearLayoutManager, 3, Runnable {
            store.dispatch(HomeAction.LOAD_MORE)
        })
    }

    private val adsManager: NativeAdsManager by lazy {
        NativeAdsManager(context, "1966287263602613_1966287926935880", 5)
    }


    private val diffCallback = object : DiffCallback {

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

    }

    private val adapter: OnlyAdapter by lazy {
        OnlyAdapter.builder()
                .diffCallback(diffCallback)
                .onItemClickListener { view, item, _ ->
                    if (item is ProductItemViewModel) {
                        when (view.id) {
                            R.id.llUpvote, R.id.llComment -> {
                                navigator
                                        .toComment(item.id)
                                        .subscribe()
                            }
                            R.id.flProductHolder -> {
                                navigator
                                        .toProduct(item.id)
                                        .subscribe()
                            }
                            R.id.llShare -> {
                                navigator
                                        .toShare(item.shareUrl)
                                        .subscribe()
                            }
                        }
                    }

                }
                .viewHolderFactory { viewGroup, type ->
                    when (type) {
                        1 -> SectionViewHolder.create(viewGroup)
                        2 -> ProductViewHolder.create(viewGroup, imageLoader)
                        3 -> HorizontalAdsViewHolder.create(viewGroup, adsManager)
                        else -> EmptyViewHolder.create(viewGroup)
                    }
                }
                .typeFactory { any ->
                    when (any) {
                        is ProductItemViewModel -> 2
                        is SectionViewModel -> 1
                        is HorizontalAdsItemViewModel -> 3
                        else -> 0
                    }
                }
                .build()
    }

    override fun store(): Store<HomeState> {
        return store
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
        setupRecyclerView()
        setupViewModel()
        setupRefresh()
    }

    private fun setupRefresh() {
        sfRefresh.setOnRefreshListener {
            store.dispatch(HomeAction.REFRESH)
        }
    }

    override fun onStart() {
        super.onStart()
        store.dispatch(HomeAction.LOAD)
    }

    override fun onDestroyView() {
        vLottie.cancelAnimation()
        super.onDestroyView()
    }

    private fun renderContent(list: List<HomeItemViewModel>) {
        sfRefresh.isRefreshing = false
        vLottie.cancelAnimation()
        vLottie.visibility = View.GONE
        adapter.setItems(list)
    }

    private fun renderRefreshError() {
        sfRefresh.isRefreshing = false

    }

    private fun renderLoadMoreError() {

    }


    private fun renderLoadError(error: Throwable) {
        vLottie.cancelAnimation()
        vLottie.visibility = View.GONE
    }

    private fun renderLoadingMore() {

    }

    private fun renderRefreshing() {
        sfRefresh.isRefreshing = true
    }

    private fun renderLoading() {
        vLottie.playAnimation()
        vLottie.visibility = View.VISIBLE
    }

    private fun setupViewModel() {
        disposeOnStop(viewModel.loading().subscribe { renderLoading() })
        disposeOnStop(viewModel.refreshing().subscribe { renderRefreshing() })
        disposeOnStop(viewModel.loadingMore().subscribe { renderLoadingMore() })
        disposeOnStop(viewModel.loadError().subscribe { renderLoadError(it) })
        disposeOnStop(viewModel.loadMoreError().subscribe { renderLoadMoreError() })
        disposeOnStop(viewModel.refreshError().subscribe { renderRefreshError() })
        disposeOnStop(viewModel.content().subscribe { renderContent(it) })
        disposeOnStop(viewModel.refreshSuccess().subscribe { loadMoreCallback.resetState() })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvNewFeeds.adapter = adapter
        rvNewFeeds.layoutManager = layoutManager
        rvNewFeeds.addItemDecoration(VerticalSpaceItemDecoration(listOf(ProductViewHolder::class.java, HorizontalAdsViewHolder::class.java), resources.getDimensionPixelSize(R.dimen.divider_space)))
        rvNewFeeds.addOnScrollListener(loadMoreCallback)
    }


}