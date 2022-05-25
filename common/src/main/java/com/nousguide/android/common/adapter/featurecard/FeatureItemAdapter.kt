package com.nousguide.android.common.adapter.featurecard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nousguide.android.common.R
import com.nousguide.android.common.adapter.HomeRowAdapter
import com.nousguide.android.common.commom.CommonViewHolder
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.models.ProductCollection.Item
import kotlinx.coroutines.CoroutineScope

class FeatureItemAdapter(
    private val context: Context,
    private val listener: CardClickListener,
    items: List<Item>,
    scopeProvider: () -> CoroutineScope
) : HomeRowAdapter(scopeProvider, items) {

    /*init {
        setHasStableIds(true)
    }*/

    override fun createCommonViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return FeatureViewHolder(
            LayoutInflater.from(context).inflate(R.layout.feature_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        (holder as FeatureViewHolder).setup(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    /*override fun getItemId(position: Int): Long {
        return position.toLong()
    }*/
}