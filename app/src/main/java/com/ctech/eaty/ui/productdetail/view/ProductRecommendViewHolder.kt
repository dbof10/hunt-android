package com.ctech.eaty.ui.productdetail.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.productdetail.navigation.ProductDetailNavigation
import com.ctech.eaty.ui.productdetail.viewmodel.ProductDetailViewModel
import com.ctech.eaty.ui.productdetail.viewmodel.ProductRecommendItemViewModel
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.widget.recyclerview.HorizontalSpaceItemDecoration
import vn.tiki.noadapter2.AbsViewHolder
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter

class ProductRecommendViewHolder(view: View,
                                 private val imageLoader: GlideImageLoader,
                                 private val viewModel: ProductDetailViewModel) : AbsViewHolder(view) {

    @BindView(R.id.rvProducts)
    lateinit var rvProducts: RecyclerView

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
                .onItemClickListener { _, item, _ ->
                    if (item is ProductItemViewModel) {
                        viewModel.toProduct(item.id)
                    }

                }
                .viewHolderFactory { viewGroup, _ ->
                    RelatedProductViewHolder.create(viewGroup, imageLoader)
                }
                .build()
    }

    init {
        ButterKnife.bind(this, view)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        rvProducts.adapter = adapter
        rvProducts.layoutManager = layoutManager
        rvProducts.addItemDecoration(HorizontalSpaceItemDecoration(RelatedProductViewHolder::class.java,
                itemView.context.resources.getDimensionPixelSize(R.dimen.divider_horizontal_space)))
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: GlideImageLoader, viewModel: ProductDetailViewModel): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_detail_recommend, parent, false)
            return ProductRecommendViewHolder(view, imageLoader, viewModel)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val product = item as ProductRecommendItemViewModel
        adapter.setItems(product.listItem)

    }
}