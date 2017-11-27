package com.ctech.eaty.ui.topiclist.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReduxFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.Topic
import com.ctech.eaty.ui.home.view.ProductViewHolder
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.topiclist.action.SearchAction
import com.ctech.eaty.ui.topiclist.navigation.SearchNavigation
import com.ctech.eaty.ui.topiclist.state.SearchState
import com.ctech.eaty.ui.topiclist.viewmodel.SearchViewModel
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.widget.recyclerview.InfiniteScrollListener
import com.ctech.eaty.widget.recyclerview.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_search.*
import timber.log.Timber
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class SearchFragment : BaseReduxFragment<SearchState>(), Injectable {

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
        InfiniteScrollListener(rvSearch.layoutManager as LinearLayoutManager, 3) {
            store.dispatch(SearchAction.LoadMore(topic.id))
        }
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
                    ProductViewHolder.create(viewGroup)
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
        setupRecyclerView()
        setupViewModel()
        store.dispatch(SearchAction.Load(topic.id))
    }


    private fun renderContent(list: List<ProductItemViewModel>) {
        progressBar.visibility = View.GONE
        vError.visibility = View.GONE
        adapter.setItems(list)
    }

    private fun renderLoadMoreError() {

    }


    private fun renderLoadError(error: Throwable) {
        progressBar.visibility = View.GONE
        vError.visibility = View.VISIBLE
        Timber.e(error)
    }

    private fun renderLoadingMore() {

    }


    private fun renderLoading() {
        vError.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun setupViewModel() {
        viewModel.loading().subscribe { renderLoading() }
        viewModel.loadingMore().subscribe { renderLoadingMore() }
        viewModel.loadError().subscribe { renderLoadError(it) }
        viewModel.loadMoreError().subscribe { renderLoadMoreError() }
        viewModel.content().subscribe { renderContent(it) }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvSearch.adapter = adapter
        rvSearch.layoutManager = layoutManager
        rvSearch.addItemDecoration(VerticalSpaceItemDecoration(ProductViewHolder::class.java, resources.getDimensionPixelSize(R.dimen.divider_space)))
        rvSearch.addOnScrollListener(loadMoreCallback)
    }


}