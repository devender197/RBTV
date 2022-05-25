package com.nousguide.android.common.adapter.covercard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.CommonRecyclerViewAdapter
import com.nousguide.android.common.commom.CommonViewHolder
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.models.ProductCollection.Item
import kotlinx.coroutines.CoroutineScope

class CoverItemAdapter(
    private val context: Context,
    private val listener: CardClickListener,
    private val items: List<Item>,
    scopeProvider: () -> CoroutineScope
) : CommonRecyclerViewAdapter(scopeProvider) {

    /*init {
        setHasStableIds(true)
    }*/

    override fun createCommonViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return CoverViewHolder(
            LayoutInflater.from(context).inflate(R.layout.cover_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        (holder as CoverViewHolder).setup(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    /*override fun getItemId(position: Int): Long {
        return position.toLong()
    }*/
}