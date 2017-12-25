package com.ctech.eaty.ui.productdetail.view

import android.app.ActivityOptions
import android.content.Context
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.transition.AutoTransition
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReduxFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.ui.home.view.EmptyViewHolder
import com.ctech.eaty.ui.productdetail.action.CHECK_DATA_SAVER
import com.ctech.eaty.ui.productdetail.action.Load
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import com.ctech.eaty.ui.productdetail.viewmodel.CommentItemViewModel
import com.ctech.eaty.ui.productdetail.viewmodel.ProductBodyItemViewModel
import com.ctech.eaty.ui.productdetail.viewmodel.ProductDetailViewModel
import com.ctech.eaty.ui.productdetail.viewmodel.ProductHeaderItemViewModel
import com.ctech.eaty.ui.productdetail.viewmodel.ProductRecommendItemViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.util.AnimUtils.getFastOutSlowInInterpolator
import com.ctech.eaty.util.glide.GlideImageLoader
import com.ctech.eaty.util.rx.plusAssign
import com.ctech.eaty.widget.TransitionListenerAdapter
import com.ctech.eaty.widget.recyclerview.InsetDividerDecoration
import kotlinx.android.synthetic.main.fragment_product_body.progressBar
import kotlinx.android.synthetic.main.fragment_product_body.rvBody
import kotlinx.android.synthetic.main.fragment_product_body.vError
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject


class ProductBodyFragment : BaseReduxFragment<ProductDetailState>(), Injectable {


    companion object {
        private val KEY_PRODUCT_ID = "productId"

        fun newInstance(id: Int): Fragment {

            val args = Bundle()
            val fragment = ProductBodyFragment()
            args.putInt(KEY_PRODUCT_ID, id)
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

    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    private val productId by lazy {
        arguments!!.getInt(KEY_PRODUCT_ID, 0)
    }

    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is CommentItemViewModel && newItem is CommentItemViewModel) {
                return oldItem.id == newItem.id
            } else if (oldItem is ProductHeaderItemViewModel && newItem is ProductHeaderItemViewModel) {
                return oldItem.id == newItem.id
            } else if (oldItem is ProductRecommendItemViewModel && newItem is ProductRecommendItemViewModel) {
                return oldItem.id == newItem.id
            }
            return false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is CommentItemViewModel && newItem is CommentItemViewModel) {
                oldItem.id == newItem.id && oldItem.isSelected == newItem.isSelected
            } else {
                oldItem == newItem
            }
        }
    }

    private lateinit var headerView: View

    private val ivSpacer by lazy {
        headerView.findViewById<View>(R.id.ivSpacer)
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
                            headerView = LayoutInflater.from(context).inflate(R.layout.item_product_detail_header, viewGroup, false)
                            ProductHeaderViewHolder.create(headerView, imageLoader)
                        }
                        2 -> ProductCommentViewHolder.create(viewGroup, imageLoader)
                        3 -> ProductRecommendViewHolder.create(viewGroup, imageLoader, viewModel)
                        else -> EmptyViewHolder.create(viewGroup)
                    }
                }
                .typeFactory {
                    when (it) {
                        is ProductHeaderItemViewModel -> 1
                        is CommentItemViewModel -> 2
                        is ProductRecommendItemViewModel -> 3
                        else -> 0
                    }
                }
                .onItemClickListener { view, item, position ->
                    when (view.id) {
                        R.id.btVote -> {
                            with(view as Button) {
                                (compoundDrawables[1] as AnimatedVectorDrawable).start()
                                viewModel.navigateVote()
                            }
                        }
                        R.id.btCommentCount -> {
                            viewModel.navigateComment()
                        }
                        R.id.btShare -> {
                            with(view as Button) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    val drawable = (compoundDrawables[1] as AnimatedVectorDrawable)
                                    drawable.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                                        override fun onAnimationEnd(drawableCallback: Drawable) {
                                            super.onAnimationEnd(drawableCallback)
                                            viewModel.shareLink()
                                        }
                                    })
                                    drawable.start()
                                } else {
                                    viewModel.shareLink()
                                }
                            }
                        }

                        R.id.tvComment -> {
                            viewModel.navigateComment()
                        }

                        R.id.btGetIt -> {
                            viewModel.getProduct(customTabActivityHelper.session)
                        }
                        R.id.rlComment -> {
                            TransitionManager.beginDelayedTransition(rvBody, expandCollapse)
                            val animator = rvBody.itemAnimator as? CommentAnimator
                            animator?.run {
                                setAnimateMoves(false)
                            }
                            viewModel.selectCommentAt(position)

                        }
                        R.id.ivAvatar -> {
                            val options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.transition_user_avatar))
                            viewModel.navigateUser((item as CommentItemViewModel).user, options)
                        }
                        R.id.ivHunterAvatar -> {
                            val options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.transition_user_avatar))
                            viewModel.navigateUser((item as ProductHeaderItemViewModel).user, options)
                        }
                    }
                }
                .build()
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            contractor.onScrolled(headerView)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            contractor.onScrollStateChanged(newState)
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

    override fun onAttach(context: Context) {
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
        contractor.onFinishFragmentInflate()
        setupRecyclerView()
        setupViewModel()
        store.dispatch(CHECK_DATA_SAVER())
        store.dispatch(Load(productId))
    }

    fun setSpacerBackground(drawable: Drawable) {
        ivSpacer.background = drawable
    }

    private fun setupViewModel() {
        disposables += viewModel.loading().subscribe { renderLoading() }
        disposables += viewModel.body().subscribe {
            renderContent(it)

        }
        disposables += viewModel.commentsSelection().subscribe { adapter.setItems(it) }
    }

    private fun renderContent(items: List<ProductBodyItemViewModel>) {
        contractor.onDataLoaded()
        vError.visibility = View.GONE
        progressBar.visibility = View.GONE
        adapter.setItems(items)
    }

    fun renderLoading() {
        vError.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvBody.adapter = adapter
        rvBody.layoutManager = layoutManager
        rvBody.itemAnimator = CommentAnimator()
        rvBody.addItemDecoration(InsetDividerDecoration(ProductCommentViewHolder::class.java, resources.getDimensionPixelSize(R.dimen.divider_height),
                resources.getDimensionPixelSize(R.dimen.keyline_1), ContextCompat.getColor(context!!, R.color.black_12)))

        rvBody.addOnScrollListener(scrollListener)
        rvBody.onFlingListener = flingListener
    }

}

