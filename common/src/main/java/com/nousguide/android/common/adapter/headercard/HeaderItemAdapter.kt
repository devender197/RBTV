package com.nousguide.android.common.adapter.headercard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nousguide.android.common.R
import com.nousguide.android.common.adapter.HomeRowAdapter
import com.nousguide.android.common.commom.CommonRecyclerViewAdapter
import com.nousguide.android.common.commom.CommonViewHolder
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.models.ProductCollection.Item
import kotlinx.coroutines.CoroutineScope

class HeaderItemAdapter(
    private val context: Context,
    private val listener: CardClickListener? = null,
    scopeProvider: () -> CoroutineScope
) : CommonRecyclerViewAdapter(scopeProvider) {

    val items: MutableList<Item> = mutableListOf()

    override fun createCommonViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return HeaderViewHolder(
            LayoutInflater.from(context).inflate(R.layout.header_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        (holder as HeaderViewHolder).setup(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    fun addItems(itemList: List<Item>) {
        items.addAll(itemList)
        notifyItemRangeChanged(0, itemList.size - 1)
    }
}