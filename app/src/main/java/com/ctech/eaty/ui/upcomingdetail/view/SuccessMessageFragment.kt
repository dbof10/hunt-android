package com.ctech.eaty.ui.upcomingdetail.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReduxFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.UpcomingProduct
import com.ctech.eaty.ui.upcomingdetail.state.UpcomingProductState
import com.ctech.eaty.ui.upcomingdetail.viewmodel.UpcomingDetailViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.util.HtmlUtils
import com.ctech.eaty.util.glide.GlideImageGetter
import com.ctech.eaty.util.onLaidOut
import com.ctech.eaty.widget.recyclerview.HorizontalSpaceItemDecoration2
import com.ctech.eaty.widget.social.BetterLinkMovementMethod
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposable
import kotlinx.android.synthetic.main.fragment_upcoming_message.rvProducts
import kotlinx.android.synthetic.main.fragment_upcoming_message.tvMessage
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject

class SuccessMessageFragment : BaseReduxFragment<UpcomingProductState>(), Injectable {


    companion object {

        fun newInstance(): Fragment {

            val args = Bundle()

            val fragment = SuccessMessageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    @Inject
    lateinit var store: Store<UpcomingProductState>

    @Inject
    lateinit var viewModel: UpcomingDetailViewModel

    private val adapter by lazy {
        OnlyAdapter.builder()
                .diffCallback(object : DiffCallback {
                    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                        if (oldItem is UpcomingProduct && newItem is UpcomingProduct) {
                            return oldItem.id == newItem.id
                        }
                        return false
                    }

                    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                        return oldItem == newItem
                    }

                })
                .onItemClickListener { _ , item, _ ->

                }
                .viewHolderFactory { viewGroup, _ ->
                    UpcomingItemViewHolder.create(viewGroup)
                }
                .build()
    }

    override fun store() = store

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_upcoming_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMessageView()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupMessageView() {
        BetterLinkMovementMethod
                .linkify(Linkify.ALL, tvMessage)
                .setOnLinkClickListener { _ , url ->
                    viewModel.openLink(url, customTabActivityHelper.session)
                    true
                }
    }

    private fun setupRecyclerView() {
        rvProducts.adapter = adapter
        rvProducts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvProducts.addItemDecoration(HorizontalSpaceItemDecoration2(resources.getDimensionPixelSize(R.dimen.divider_space)))
    }


    private fun setupViewModel() {
        viewModel.moreUpcomingProducts()
                .autoDisposable(AndroidLifecycleScopeProvider.from(this))
                .subscribe {
                    adapter.setItems(it)
                }

        viewModel.successMessage()
                .autoDisposable(AndroidLifecycleScopeProvider.from(this))
                .subscribe {
                    tvMessage.onLaidOut {
                        val imageSetter = GlideImageGetter(tvMessage)
                        imageSetter.setHolderWidth(tvMessage.width)
                        tvMessage.text = HtmlUtils.fromHtml(it, imageSetter)
                    }

                }

    }


}