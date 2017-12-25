package com.ctech.eaty.ui.topic.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReduxFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.Topic
import com.ctech.eaty.ui.comment.action.CommentAction
import com.ctech.eaty.ui.topic.action.TopicAction
import com.ctech.eaty.ui.topic.state.TopicState
import com.ctech.eaty.ui.topic.viewmodel.TopicViewModel
import com.ctech.eaty.ui.topiclist.view.TopicListActivity
import com.ctech.eaty.util.glide.GlideImageLoader
import com.ctech.eaty.widget.recyclerview.InfiniteScrollListener
import kotlinx.android.synthetic.main.fragment_topics.*
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject

class TopicFragment : BaseReduxFragment<TopicState>(), Injectable {

    companion object {

        fun newInstance(): Fragment {

            val args = Bundle()

            val fragment = TopicFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var store: Store<TopicState>

    @Inject
    lateinit var viewModel: TopicViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is Topic && newItem is Topic) {
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
                    TopicViewHolder.create(viewGroup, imageLoader)
                }
                .onItemClickListener { _, item, _ ->
                    (item as Topic).run {
                        val intent = TopicListActivity.newIntent(context!!, this)
                        context?.startActivity(intent)
                    }

                }
                .build()
    }


    override fun store(): Store<TopicState> {
        return store
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_topics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setupErrorView()
        store.dispatch(TopicAction.LOAD)
    }

    private fun setupErrorView() {
        vError.setOnRetryListener {
            store.dispatch(TopicAction.LOAD)
        }
    }

    private fun renderContent(list: List<Topic>) {
        progressBar.visibility = View.GONE
        vError.visibility = View.GONE
        adapter.setItems(list)
    }

    private fun renderLoadMoreError() {

    }


    private fun renderLoadError() {
        vError.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun renderLoadingMore() {

    }


    private fun renderLoading() {
        vError.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
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
        rvTopic.adapter = adapter
        rvTopic.layoutManager = layoutManager
        rvTopic.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rvTopic.addOnScrollListener(InfiniteScrollListener(layoutManager, 3, {
            store.dispatch(CommentAction.LoadMore(id))
        }))
    }
}