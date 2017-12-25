package com.ctech.eaty.ui.noti.view


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
import com.ctech.eaty.entity.Notification
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.noti.action.NotificationAction
import com.ctech.eaty.ui.noti.state.NotificationState
import com.ctech.eaty.ui.noti.viewmodel.NotificationViewModel
import com.ctech.eaty.util.glide.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_notifications.*
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class NotificationFragment : BaseReduxFragment<NotificationState>(), Injectable {

    companion object {

        fun newInstance(): Fragment {

            val args = Bundle()

            val fragment = NotificationFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var store: Store<NotificationState>

    @Inject
    lateinit var viewModel: NotificationViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    @Inject
    lateinit var trackManager: FirebaseTrackManager

    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is Notification && newItem is Notification) {
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
                    NotificationViewHolder.create(viewGroup, imageLoader, trackManager)
                }
                .build()
    }

    override fun store(): Store<NotificationState> = store

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
        setupErrorView()
        store.dispatch(NotificationAction.LOAD)
    }

    private fun setupErrorView() {
        vError.setOnRetryListener {
            store.dispatch(NotificationAction.LOAD)
        }

    }

    private fun renderContent(list: List<Notification>) {
        vError.visibility = View.GONE
        progressBar.visibility = View.GONE
        vEmpty.visibility = View.GONE
        adapter.setItems(list)
    }


    private fun renderLoadError() {
        vError.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        vEmpty.visibility = View.GONE
    }

    private fun renderLoading() {
        vError.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        vEmpty.visibility = View.GONE
    }

    private fun renderEmpty() {
        vError.visibility = View.GONE
        progressBar.visibility = View.GONE
        vEmpty.visibility = View.VISIBLE
    }

    private fun setupViewModel() {
        viewModel.loading().subscribe { renderLoading() }
        viewModel.loadError().subscribe {
            renderLoadError()
        }
        viewModel.empty().subscribe { renderEmpty() }
        viewModel.content().subscribe { renderContent(it) }
    }


    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvNotifications.adapter = adapter
        rvNotifications.layoutManager = layoutManager
        rvNotifications.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

}