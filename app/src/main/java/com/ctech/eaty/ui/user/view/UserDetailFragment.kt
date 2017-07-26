package com.ctech.eaty.ui.user.view

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
import com.ctech.eaty.ui.home.view.ProductViewHolder
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.user.action.UserAction
import com.ctech.eaty.ui.user.state.UserDetailState
import com.ctech.eaty.ui.user.viewmodel.UserDetailViewModel
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.util.setPaddingTop
import com.ctech.eaty.widget.recyclerview.SlideInItemAnimator
import com.ctech.eaty.widget.recyclerview.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_user.*
import timber.log.Timber
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class UserDetailFragment : BaseReduxFragment<UserDetailState>(), Injectable {

    companion object {

        private val USER_ID_KEY = "userId"
        fun newInstance(id: Int): Fragment {

            val args = Bundle()

            val fragment = UserDetailFragment()
            args.putInt(USER_ID_KEY, id)
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
    lateinit var store: Store<UserDetailState>

    @Inject
    lateinit var viewModel: UserDetailViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    private val progressBar by lazy {
        activity.findViewById(R.id.progressBar)
    }

    private val container by lazy {
        activity.findViewById(R.id.container)
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
                        when (view.id) {
                            R.id.flProductHolder -> {
                                viewModel.navigateProduct(item.id)

                            }
                        }
                    }

                }
                .viewHolderFactory { viewGroup, _ ->
                    ProductViewHolder.create(viewGroup, imageLoader)
                }
                .build()
    }

    override fun store(): Store<UserDetailState> {
        return store
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
    }

    override fun onStart() {
        super.onStart()
        store.dispatch(UserAction.Load(arguments.getInt(USER_ID_KEY)))
    }


    private fun renderContent(list: List<ProductItemViewModel>) {
        progressBar.visibility = View.GONE
        vError.visibility = View.GONE
        rvProducts.visibility = View.VISIBLE
        rvProducts.setPaddingTop(activity.findViewById(R.id.tvProductCount).bottom)
        adapter.setItems(list)
    }


    private fun renderLoadError(error: Throwable) {
        vError.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        Timber.e(error)
    }

    private fun renderLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun setupViewModel() {
        disposeOnStop(viewModel.loading().subscribe { renderLoading() })
        disposeOnStop(viewModel.loadError().subscribe { renderLoadError(it) })
        disposeOnStop(viewModel.body().subscribe { renderContent(it) })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvProducts.adapter = adapter
        rvProducts.layoutManager = layoutManager
        rvProducts.setHasFixedSize(true)
        rvProducts.itemAnimator = SlideInItemAnimator()
        rvProducts.addItemDecoration(VerticalSpaceItemDecoration(ProductViewHolder::class.java, resources.getDimensionPixelSize(R.dimen.divider_space)))
        rvProducts.setOnTouchListener { _, event ->
            val firstVisible = layoutManager.findFirstVisibleItemPosition()
            if (firstVisible > 0) {
                return@setOnTouchListener false
            }

            if (adapter.itemCount == 0) {
                return@setOnTouchListener container.dispatchTouchEvent(event)
            }

            val vh = rvProducts.findViewHolderForAdapterPosition(0) ?: return@setOnTouchListener false
            val firstTop = vh.itemView.top
            if (event.y < firstTop) {
                return@setOnTouchListener container.dispatchTouchEvent(event)
            }
            return@setOnTouchListener false
        }
    }
}