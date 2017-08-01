package com.ctech.eaty.ui.collection.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReduxFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.Collection
import com.ctech.eaty.ui.collection.action.CollectionAction
import com.ctech.eaty.ui.collection.state.CollectionState
import com.ctech.eaty.ui.collection.viewmodel.CollectionViewModel
import com.ctech.eaty.ui.collectiondetail.view.CollectionDetailActivity
import com.ctech.eaty.ui.comment.action.CommentAction
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.widget.recyclerview.InfiniteScrollListener
import kotlinx.android.synthetic.main.fragment_collections.*
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class CollectionFragment : BaseReduxFragment<CollectionState>(), Injectable {

    companion object {

        fun newInstance(): Fragment {

            val args = Bundle()

            val fragment = CollectionFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var store: Store<CollectionState>

    @Inject
    lateinit var viewModel: CollectionViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is Collection && newItem is Collection) {
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
                .viewHolderFactory { viewGroup, _ ->
                    CollectionViewHolder.create(viewGroup, imageLoader)
                }
                .onItemClickListener { _, item, _ ->
                    (item as Collection).run {
                        val intent = CollectionDetailActivity.newIntent(context, id)
                        context.startActivity(intent)
                    }
                }
                .build()
    }

    override fun store(): Store<CollectionState> {
        return store
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collections, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
        setupErrorView()
    }

    override fun onStart() {
        super.onStart()
        store.dispatch(CollectionAction.LOAD)
    }

    private fun setupErrorView() {
        vError.setOnRetryListener {
            store.dispatch(CollectionAction.LOAD)
        }

    }

    private fun renderContent(list: List<Collection>) {
        vLottie.cancelAnimation()
        vError.visibility = View.GONE
        vLottie.visibility = View.GONE
        adapter.setItems(list)
    }


    private fun renderLoadMoreError() {

    }


    private fun renderLoadError() {
        vLottie.cancelAnimation()
        vError.visibility = View.VISIBLE
        vLottie.visibility = View.GONE
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
        disposeOnStop(viewModel.loadError().subscribe {
            renderLoadError()
        })
        disposeOnStop(viewModel.loadMoreError().subscribe { renderLoadMoreError() })
        disposeOnStop(viewModel.content().subscribe { renderContent(it) })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvCollections.adapter = adapter
        rvCollections.layoutManager = layoutManager
        rvCollections.addOnScrollListener(InfiniteScrollListener(layoutManager, 3) {
            store.dispatch(CommentAction.LoadMore(id))
        })
    }

}