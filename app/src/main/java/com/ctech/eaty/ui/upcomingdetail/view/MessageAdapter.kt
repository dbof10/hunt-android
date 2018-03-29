package com.ctech.eaty.ui.upcomingdetail.view

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ctech.eaty.ui.upcomingdetail.viewmodel.MessageViewModel
import com.ctech.eaty.ui.upcomingdetail.viewmodel.MessageViewModel.Companion.TYPE_DEFAULT
import com.ctech.eaty.ui.upcomingdetail.viewmodel.MessageViewModel.Companion.TYPE_EXTENDED
import com.ctech.eaty.widget.social.OnLinkClickListener

typealias OnEmailSubmitListener = (String) -> Unit

class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {

    override fun getItemCount() = items.size

    var items: List<MessageViewModel> = emptyList()
    internal var linkClickListener: OnLinkClickListener? = null
    internal var emailSubmitListener: OnEmailSubmitListener? = null

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.run {
            bind(items[position])
            linkListener = linkClickListener
        }
        if (holder is MessageExtendedViewHolder) {
            holder.emailSubmit = emailSubmitListener
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): MessageViewHolder {
        if (type == TYPE_DEFAULT) {
            return MessageViewHolder.create(viewGroup)
        } else if (type == TYPE_EXTENDED) {
            return MessageExtendedViewHolder.create(viewGroup)
        }
        throw IllegalArgumentException("Unsupported type $type")
    }

    override fun onViewRecycled(holder: MessageViewHolder) {
        super.onViewRecycled(holder)
        holder.onUnbind()
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

}