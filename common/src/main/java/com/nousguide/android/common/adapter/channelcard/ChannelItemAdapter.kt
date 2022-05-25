package com.nousguide.android.common.adapter.channelcard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.CommonRecyclerViewAdapter
import com.nousguide.android.common.commom.CommonViewHolder
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.models.LinearChannel.LinearItem
import kotlinx.coroutines.CoroutineScope

class ChannelItemAdapter(
    private val context: Context,
    private val listener: CardClickListener,
    private val items: List<LinearItem>,
    scopeProvider: () -> CoroutineScope
) : CommonRecyclerViewAdapter(scopeProvider) {

    /*init {
        setHasStableIds(true)
    }*/

    override fun createCommonViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return ChannelViewHolder(LayoutInflater.from(context).inflate(R.layout.channel_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        (holder as ChannelViewHolder).setup(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

   /*override fun getItemId(position: Int): Long {
        return position.toLong()
    }*/
}