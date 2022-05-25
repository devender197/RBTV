package com.nousguide.android.common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nousguide.android.common.R
import com.nousguide.android.common.adapter.channelcard.ChannelViewHolder
import com.nousguide.android.common.adapter.covercard.CoverViewHolder
import com.nousguide.android.common.adapter.featurecard.FeatureViewHolder
import com.nousguide.android.common.adapter.headercard.HeaderViewHolder
import com.nousguide.android.common.commom.CommonRecyclerViewAdapter
import com.nousguide.android.common.commom.CommonViewHolder
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.models.LinearChannel.LinearItem
import com.nousguide.android.common.models.ProductCollection.Item
import kotlinx.coroutines.CoroutineScope

class HorizontalAdapter(
    private val context: Context,
    private val listener: CardClickListener,
    private val items: List<Any>,
    scopeProvider: () -> CoroutineScope,
    private val itemViewType: Int
) : CommonRecyclerViewAdapter(scopeProvider) {

    companion object {
        const val HEADER_VIEW = 0
        const val FEATURE_CARD = 1
        const val CHANNEL_VIEW = 2
        const val COVER_VIEW = 3
    }

    override fun createCommonViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return when (itemViewType) {
            HEADER_VIEW -> HeaderViewHolder(
                LayoutInflater.from(context).inflate(R.layout.header_item_layout, parent, false)
            )
            COVER_VIEW -> CoverViewHolder(
                LayoutInflater.from(context).inflate(R.layout.cover_item_layout, parent, false)
            )
            CHANNEL_VIEW -> ChannelViewHolder(
                LayoutInflater.from(context).inflate(R.layout.channel_item_layout, parent, false)
            )
            else -> FeatureViewHolder(
                LayoutInflater.from(context).inflate(R.layout.feature_item_layout, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.setup(items[position] as Item, listener)

            is CoverViewHolder -> holder.setup(items[position] as Item, listener)

            is ChannelViewHolder -> holder.setup(items[position] as LinearItem, listener)

            else -> (holder as FeatureViewHolder).setup(items[position] as Item, listener)
        }

    }

    override fun getItemCount(): Int = items.size

    private fun getItemViewType(): Int {
        return when (val model = items.first()) {
            is Item -> {
                when {
                    model.title.equals("Featuring Test Cases", true) -> HEADER_VIEW
                    model.title.equals("Surf: Enjoy the best shows", true) -> COVER_VIEW
                    model.title.equals("Shows", true) -> COVER_VIEW
                    model.title.equals("Vitalize Body & Mind", true) -> COVER_VIEW
                    model.title.equals("H265 HFR Renditions", true) -> COVER_VIEW
                    model.title.equals("H264 HFR Renditions", true) -> COVER_VIEW
                    model.title.equals("Films and Documentaries", true) -> COVER_VIEW
                    else -> FEATURE_CARD
                }
            }
            is LinearItem -> CHANNEL_VIEW
            else -> FEATURE_CARD
        }
    }

}