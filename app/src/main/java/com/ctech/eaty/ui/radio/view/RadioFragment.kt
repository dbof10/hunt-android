package com.ctech.eaty.ui.radio.view


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
import com.ctech.eaty.ui.radio.action.RadioAction
import com.ctech.eaty.ui.radio.state.RadioState
import com.ctech.eaty.ui.radio.viewmodel.RadioViewModel
import com.ctech.eaty.ui.radio.viewmodel.TrackItemViewModel
import com.ctech.eaty.util.glide.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_radio.*
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class RadioFragment : BaseReduxFragment<RadioState>(), Injectable {

    companion object {

        fun newInstance(): Fragment {

            val args = Bundle()

            val fragment = RadioFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var store: Store<RadioState>

    @Inject
    lateinit var viewModel: RadioViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is TrackItemViewModel && newItem is TrackItemViewModel) {
                return oldItem.id == newItem.id
            }
            return false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is TrackItemViewModel && newItem is TrackItemViewModel) {
                return oldItem.status == newItem.status
            }
            return false
        }

    }

    private val adapter: OnlyAdapter by lazy {
        OnlyAdapter.builder()
                .diffCallback(diffCallback)
                .viewHolderFactory { viewGroup, _ ->
                    TrackViewHolder.create(viewGroup, imageLoader)
                }
                .onItemClickListener { _, _, position ->
                    viewModel.selectTrackAt(position)
                }
                .build()
    }

    override fun store(): Store<RadioState> {
        return store
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_radio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
        setupErrorView()
        store.dispatch(RadioAction.LOAD)
    }

    private fun setupErrorView() {
        vError.setOnRetryListener {
            store.dispatch(RadioAction.LOAD)
        }
    }

    private fun renderContent(list: List<TrackItemViewModel>) {
        vError.visibility = View.GONE
        progressBar.visibility = View.GONE
        adapter.setItems(list)
    }


    private fun renderLoadError() {
        vError.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun renderLoading() {
        vError.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun setupViewModel() {
        disposeOnStop(viewModel.loading().subscribe { renderLoading() })
        disposeOnStop(viewModel.loadError().subscribe {
            renderLoadError()
        })
        disposeOnStop(viewModel.body().subscribe { renderContent(it) })
        disposeOnStop(viewModel.trackSelection().subscribe { adapter.setItems(it) })

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvTracks.adapter = adapter
        rvTracks.setHasFixedSize(true)
        rvTracks.layoutManager = layoutManager
        rvTracks.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

}