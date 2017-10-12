package com.ctech.eaty.ui.follow.view


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReduxFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.User
import com.ctech.eaty.ui.follow.action.FollowAction
import com.ctech.eaty.ui.follow.state.FollowState
import com.ctech.eaty.ui.follow.viewmodel.FollowViewModel
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.widget.recyclerview.InfiniteScrollListener
import kotlinx.android.synthetic.main.fragment_votes.*
import timber.log.Timber
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class FollowFragment : BaseReduxFragment<FollowState>(), Injectable {

    companion object {
        private val USER_ID_KEY = "userId"
        private val RELATIONSHIP_KEY = "relationship"

        fun newInstance(id: Int, type: Relationship): Fragment {

            val args = Bundle()

            val fragment = FollowFragment()
            args.putInt(USER_ID_KEY, id)
            args.putSerializable(RELATIONSHIP_KEY, type)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var store: Store<FollowState>

    @Inject
    lateinit var viewModel: FollowViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    private val userId by lazy {
        arguments.getInt(USER_ID_KEY)
    }

    private val relationship by lazy {
        arguments.getSerializable(RELATIONSHIP_KEY)
    }

    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is User && newItem is User) {
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
                    FollowViewHolder.create(viewGroup, imageLoader)
                }
                .build()
    }

    private lateinit var contractor: FragmentContractor

    override fun store(): Store<FollowState> {
        return store
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FollowActivity) {
            contractor = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupErrorView()
        setupViewModel()
    }

    override fun onStart() {
        super.onStart()
        store.dispatch(relationship.run {
            if (this == Relationship.FOLLOWER)
                FollowAction.LoadFollower(userId)
            else
                FollowAction.LoadFollowing(userId)
        })
    }

    private fun renderContent(list: List<User>) {
        vLottie.cancelAnimation()
        vLottie.visibility = View.GONE
        vError.visibility = View.GONE
        adapter.setItems(list)
    }

    private fun setupErrorView() {
        vError.setOnRetryListener {
            store.dispatch(relationship.run {
                if (this == Relationship.FOLLOWER)
                    FollowAction.LoadFollower(userId)
                else
                    FollowAction.LoadFollowing(userId)
            })
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
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvVotes.adapter = adapter
        rvVotes.layoutManager = layoutManager
        rvVotes.addOnScrollListener(InfiniteScrollListener(layoutManager, 3){
            store.dispatch(relationship.run {
                if (this == Relationship.FOLLOWER)
                    FollowAction.LoadMoreFollower(userId)
                else
                    FollowAction.LoadMoreFollowing(userId)
            })
        })
        rvVotes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val raiseTitleBar = dy > 0 || rvVotes.computeVerticalScrollOffset() != 0
                contractor.getTitleBar().isActivated = raiseTitleBar // animated via a StateListAnimator
            }
        })
    }

}