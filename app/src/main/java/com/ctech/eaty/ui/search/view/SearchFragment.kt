package com.ctech.eaty.ui.search.view

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
import com.ctech.eaty.entity.Topic
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.search.action.SearchAction
import com.ctech.eaty.ui.search.navigation.SearchNavigation
import com.ctech.eaty.ui.search.state.SearchState
import com.ctech.eaty.ui.search.viewmodel.SearchViewModel
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.widget.recyclerview.InfiniteScrollListener
import com.ctech.eaty.widget.recyclerview.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_search.*
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class SearchFragment : BaseFragment<SearchState>(), Injectable {

    companion object {
        val TOPIC_ID_KEY = "topicId"

        fun newInstance(topic: Topic): Fragment {

            val args = Bundle()

            val fragment = SearchFragment()
            args.putParcelable(TOPIC_ID_KEY, topic)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var store: Store<SearchState>

    @Inject
    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    @Inject
    lateinit var navigator: SearchNavigation

    private val topic by lazy {
        arguments.getParcelable<Topic>(TOPIC_ID_KEY)
    }

    private val loadMoreCallback by lazy {
        InfiniteScrollListener(rvSearch.layoutManager as LinearLayoutManager, 3, Runnable {
            store.dispatch(SearchAction.LoadMore(topic.id))
        })
    }

    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is ProductItemViewModel && newItem is ProductItemViewModel) {
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
                        if (view.id == R.id.flProductHolder) {
                            navigator
                                    .toProduct(item.id)
                                    .subscribe()
                        }
                    }

                }
                .viewHolderFactory { viewGroup, _ ->
                    SearchViewHolder.create(viewGroup, imageLoader)
                }
                .build()
    }

    override fun store(): Store<SearchState> {
        return store
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
        setupRecyclerView()
        setupViewModel()
    }


    override fun onStart() {
        super.onStart()
        store.dispatch(SearchAction.Load(topic.id))
    }

    override fun onDestroyView() {
        vLottie.cancelAnimation()
        super.onDestroyView()
    }

    private fun renderContent(list: List<ProductItemViewModel>) {
        vLottie.cancelAnimation()
        vLottie.visibility = View.GONE
        vError.visibility = View.GONE
        adapter.setItems(list)
    }

    private fun renderLoadMoreError() {

    }


    private fun renderLoadError(error: Throwable) {
        vLottie.cancelAnimation()
        vLottie.visibility = View.GONE
        vError.visibility = View.VISIBLE
    }

    private fun renderLoadingMore() {

    }


    private fun renderLoading() {
        vLottie.playAnimation()
        vError.visibility = View.GONE
        vLottie.visibility = View.VISIBLE
    }

    private fun setupViewModel() {
        disposeOnStop(viewModel.loading().subscribe { renderLoading() })
        disposeOnStop(viewModel.loadingMore().subscribe { renderLoadingMore() })
        disposeOnStop(viewModel.loadError().subscribe { renderLoadError(it) })
        disposeOnStop(viewModel.loadMoreError().subscribe { renderLoadMoreError() })
        disposeOnStop(viewModel.content().subscribe { renderContent(it) })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvSearch.adapter = adapter
        rvSearch.layoutManager = layoutManager
        rvSearch.addItemDecoration(VerticalSpaceItemDecoration(SearchViewHolder::class.java, resources.getDimensionPixelSize(R.dimen.divider_space)))
        rvSearch.addOnScrollListener(loadMoreCallback)
    }


}