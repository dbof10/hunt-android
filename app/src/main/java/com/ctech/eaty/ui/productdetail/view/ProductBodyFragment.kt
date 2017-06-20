package com.ctech.eaty.ui.productdetail.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.transition.AutoTransition
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.Comment
import com.ctech.eaty.ui.home.view.EmptyViewHolder
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import com.ctech.eaty.ui.productdetail.viewmodel.CommentItemViewModel
import com.ctech.eaty.ui.productdetail.viewmodel.ProductDetailViewModel
import com.ctech.eaty.ui.productdetail.viewmodel.ProductHeaderItemViewModel
import com.ctech.eaty.util.AnimUtils.getFastOutSlowInInterpolator
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.widget.InsetDividerDecoration
import com.ctech.eaty.widget.TransitionListenerAdapter
import kotlinx.android.synthetic.main.fragment_product_body.*
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class ProductBodyFragment : BaseFragment<ProductDetailState>(), Injectable {


    companion object {

        fun newInstance(): Fragment {

            val args = Bundle()
            val fragment = ProductBodyFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var store: Store<ProductDetailState>

    @Inject
    lateinit var viewModel: ProductDetailViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is CommentItemViewModel && newItem is CommentItemViewModel) {
                return oldItem.id == newItem.id
            } else if (oldItem is ProductHeaderItemViewModel && newItem is ProductHeaderItemViewModel) {
                return oldItem.id == newItem.id
            }
            return false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is CommentItemViewModel && newItem is CommentItemViewModel) {
                return oldItem.id == newItem.id && oldItem.isSelected == newItem.isSelected
            } else {
                return oldItem == newItem
            }
        }
    }

    private lateinit var headerView: View

    private val ivSpacer by lazy {
        headerView.findViewById(R.id.ivSpacer)
    }

    private val expandCollapse: Transition by lazy {
        val expandCollapse = AutoTransition()
        expandCollapse.duration = resources.getInteger(R.integer.comment_expand_collapse_duration).toLong()
        expandCollapse.interpolator = getFastOutSlowInInterpolator(context)
        expandCollapse.addListener(object : TransitionListenerAdapter() {
            override fun onTransitionStart(transition: Transition) {
                rvBody.setOnTouchListener { _, _ ->
                    true
                }
            }

            override fun onTransitionEnd(transition: Transition) {
                val animator = rvBody.itemAnimator as? CommentAnimator
                animator?.run {
                    setAnimateMoves(false)
                }
                rvBody.setOnTouchListener(null)
            }
        })
        expandCollapse
    }


    private val adapter: OnlyAdapter by lazy {
        OnlyAdapter.builder()
                .diffCallback(diffCallback)
                .viewHolderFactory { viewGroup, type ->
                    when (type) {
                        1 -> {
                            headerView = LayoutInflater.from(context).inflate(R.layout.item_product_description, viewGroup, false)
                            ProductHeaderViewHolder.create(headerView, imageLoader)
                        }
                        2 -> ProductCommentViewHolder.create(viewGroup, imageLoader)
                        else -> EmptyViewHolder.create(viewGroup)
                    }
                }
                .typeFactory {
                    when (it) {
                        is CommentItemViewModel -> 2
                        is ProductHeaderItemViewModel -> 1
                        else -> 0
                    }
                }
                .onItemClickListener { _, _, position ->
                    viewModel.selectCommentAt(position)
                }
                .build()
    }

    private val scrollListener by lazy {
        object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                contractor.onScrolled(headerView)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                contractor.onScrollStateChanged(newState)
            }
        }
    }

    private val flingListener by lazy {
        object : RecyclerView.OnFlingListener() {
            override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                contractor.onFling()
                return false
            }
        }
    }

    private lateinit var contractor: FragmentContract

    override fun store(): Store<ProductDetailState> = store

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ProductDetailActivity) {
            contractor = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_body, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
    }

    fun setSpacerBackground(drawable: Drawable) {
        ivSpacer.background = drawable
    }

    private fun setupViewModel() {
        disposeOnStop(viewModel.comments().subscribe { adapter.setItems(it) })
        disposeOnStop(viewModel.commentsSelection().subscribe { adapter.setItems(it) })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvBody.adapter = adapter
        rvBody.layoutManager = layoutManager
        rvBody.itemAnimator = CommentAnimator()
        rvBody.addItemDecoration(InsetDividerDecoration(ProductCommentViewHolder.javaClass, resources.getDimensionPixelSize(R.dimen.divider_height),
                resources.getDimensionPixelSize(R.dimen.keyline_1), ContextCompat.getColor(context, R.color.black_12)))

        rvBody.addOnScrollListener(scrollListener)
        rvBody.onFlingListener = flingListener
    }

}

