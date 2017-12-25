package com.ctech.eaty.ui.collectiondetail.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReduxFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.ui.collectiondetail.navigation.CollectionDetailNavigation
import com.ctech.eaty.ui.collectiondetail.state.CollectionDetailState
import com.ctech.eaty.ui.collectiondetail.viewmodel.CollectionDetailItemViewModel
import com.ctech.eaty.ui.collectiondetail.viewmodel.CollectionDetailViewModel
import com.ctech.eaty.ui.collectiondetail.viewmodel.CollectionHeaderItemViewModel
import com.ctech.eaty.ui.home.view.EmptyViewHolder
import com.ctech.eaty.ui.home.view.ProductViewHolder
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.util.glide.GlideImageLoader
import com.ctech.eaty.widget.recyclerview.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_collection_detail.*
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class CollectionDetailFragment : BaseReduxFragment<CollectionDetailState>(), Injectable {

    companion object {

        fun newInstance(): Fragment {

            val args = Bundle()

            val fragment = CollectionDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var store: Store<CollectionDetailState>

    @Inject
    lateinit var viewModel: CollectionDetailViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    @Inject
    lateinit var navigator: CollectionDetailNavigation


    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is ProductItemViewModel && newItem is ProductItemViewModel) {
                return oldItem.id == newItem.id
            } else if (oldItem is CollectionHeaderItemViewModel && newItem is CollectionHeaderItemViewModel) {
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
                .viewHolderFactory { viewGroup, type ->
                    when (type) {
                        1 -> CollectionHeaderViewHolder.create(viewGroup, imageLoader)
                        2 -> ProductViewHolder.create(viewGroup)
                        else -> EmptyViewHolder.create(viewGroup)
                    }

                }
                .typeFactory { item ->
                    when (item) {
                        is CollectionHeaderItemViewModel -> 1
                        is ProductItemViewModel -> 2
                        else -> 0
                    }
                }
                .build()
    }

    override fun store(): Store<CollectionDetailState> {
        return store
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collection_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
    }


    private fun setupViewModel() {
        disposeOnStop(viewModel.body().subscribe { renderContent(it) })
        disposeOnStop(viewModel.loading().subscribe { renderLoading() })
    }

    private fun renderLoading(){
        vError.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun renderContent(content: List<CollectionDetailItemViewModel>) {
        vError.visibility = View.GONE
        progressBar.visibility = View.GONE
        adapter.setItems(content)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvBody.adapter = adapter
        rvBody.layoutManager = layoutManager
        rvBody.addItemDecoration(VerticalSpaceItemDecoration(ProductViewHolder::class.java, resources.getDimensionPixelSize(R.dimen.divider_space)))
    }


}