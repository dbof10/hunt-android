package com.ctech.eaty.ui.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.SectionViewModel
import vn.tiki.noadapter2.AbsViewHolder

class SectionViewHolder(view: View) : AbsViewHolder(view) {


    @BindView(R.id.tvSection)
    lateinit var tvSection: TextView

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_section, parent, false)
            return SectionViewHolder(view)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val viewModel = item as SectionViewModel
        with(viewModel) {
            tvSection.text = viewModel.date
        }


    }

}