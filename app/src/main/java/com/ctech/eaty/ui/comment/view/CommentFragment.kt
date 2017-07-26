package com.ctech.eaty.ui.comment.view


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
import com.ctech.eaty.ui.comment.action.CommentAction
import com.ctech.eaty.ui.comment.state.CommentState
import com.ctech.eaty.ui.comment.viewmodel.CommentItemViewModel
import com.ctech.eaty.ui.comment.viewmodel.CommentViewModel
import com.ctech.eaty.ui.home.view.EmptyViewHolder
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.widget.recyclerview.InfiniteScrollListener
import kotlinx.android.synthetic.main.fragment_comments.*
import timber.log.Timber
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class CommentFragment : BaseReduxFragment<CommentState>(), Injectable {

    companion object {
        val PRODUCT_ID = "productId"

        fun newInstance(id: Int): Fragment {

            val args = Bundle()

            val fragment = CommentFragment()
            args.putInt(PRODUCT_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var store: Store<CommentState>

    @Inject
    lateinit var viewModel: CommentViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    private val productId by lazy {
        arguments.getInt(PRODUCT_ID)
    }

    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is CommentItemViewModel && newItem is CommentItemViewModel) {
                return oldItem.id == newItem.id && oldItem.parentId == newItem.parentId
            }
            return false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is CommentItemViewModel && newItem is CommentItemViewModel) {
                return oldItem.isExpanded == newItem.isExpanded
            }
            return oldItem == newItem
        }

    }

    private val adapter: OnlyAdapter by lazy {
        OnlyAdapter.builder()
                .diffCallback(diffCallback)
                .onItemClickListener { view, _, position ->
                    if (view.id == R.id.tvComment) {
                        viewModel.selectCommentAt(position)
                    }
                }
                .viewHolderFactory { viewGroup, type ->
                    when (type) {
                        1 -> CommentCollapsedViewHolder.create(viewGroup, imageLoader)
                        2 -> CommentExpandedViewHolder.create(viewGroup, imageLoader)
                        else -> EmptyViewHolder.create(viewGroup)
                    }
                }
                .typeFactory {
                    val item = it as CommentItemViewModel
                    if (!item.isExpanded)
                        1
                    else
                        2
                }
                .build()
    }

    override fun store(): Store<CommentState> {
        return store
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
        setupErrorView()
    }

    override fun onStart() {
        super.onStart()
        store.dispatch(CommentAction.Load(productId))
    }

    private fun renderContent(list: List<CommentItemViewModel>) {
        vLottie.cancelAnimation()
        vLottie.visibility = View.GONE
        vError.visibility = View.GONE
        adapter.setItems(list)
    }

    private fun setupErrorView() {
        vError.setOnRetryListener {
            store.dispatch(CommentAction.Load(productId))
        }
    }

    private fun renderLoadMoreError() {

    }


    private fun renderLoadError(error: Throwable) {
        vLottie.cancelAnimation()
        vLottie.visibility = View.GONE
        vError.visibility = View.VISIBLE
        Timber.e(error)
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
        disposeOnStop(viewModel.commentExpansion().subscribe { adapter.setItems(it) })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvComments.adapter = adapter
        rvComments.layoutManager = layoutManager
        rvComments.addOnScrollListener(InfiniteScrollListener(layoutManager, 3, Runnable {
            store.dispatch(CommentAction.LoadMore(productId))
        }))
    }

}