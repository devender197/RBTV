package com.nousguide.android.common.cards

import android.content.Context
import com.nousguide.android.common.adapter.HorizontalAdapter
import com.nousguide.android.common.models.ProductCollection.Item
import com.nousguide.android.common.models.LinearChannel.LinearItem
import com.nousguide.android.common.models.core.ProductModel
import javax.inject.Inject

class CardFactory @Inject constructor(private val context: Context) {

    fun createCard(item: Any): Card {
        return when (if (item is Item) item.itemViewType else (item as LinearItem).itemViewType) {

            HorizontalAdapter.FEATURE_CARD -> FeatureCard(context).apply {
                setTitle((item as Item).title)
                setDesc(item.subheading)
                setImageUrl(item.previewImageUrl)
                setVideoUrl(item.previewVideoUrl)
            }

            HorizontalAdapter.CHANNEL_VIEW -> ChannelCard(context).apply {
                setTitle((item as LinearItem).title)
                setImageUrl(item.previewImageUrl)
            }
            HorizontalAdapter.COVER_VIEW -> CoverCard(context).apply {
                setImageUrl((item as Item).previewImageUrl)
            }
            else -> FeatureCard(context).apply {
                setTitle((item as Item).title)
                setDesc(item.subheading)
                setImageUrl(item.previewImageUrl)
                setVideoUrl(item.previewVideoUrl)
            }

        }
    }

    fun createHeaderCard(model: ProductModel) = HeaderCard(context).apply {
        val list = model.productCollection
        addItems(list as List<Item>)
        setTabs(list.size)
    }
}