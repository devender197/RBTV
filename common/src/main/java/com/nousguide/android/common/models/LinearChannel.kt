package com.nousguide.android.common.models

import com.nousguide.android.common.adapter.HorizontalAdapter

data class LinearChannel(
    val content_type: String,
    val id: String,
    val item_type: String,
    val items: List<LinearItem>,
    val label: String,
    val list_type: String,
    val meta: LinearMeta,
    val publish_state: String,
    val short_label: String,
    val type: String,
    val whitelist_countries: List<String>
) {

    class LinearItem(
        val content_type: String,
        val hide_corner_bug: Boolean,
        val id: String,
        val links: List<Any>,
        val long_description: String,
        val playable: Boolean,
        val resources: List<String>,
        val share_url: String,
        val short_description: String,
        val subchannel_type: String,
        val subheading: String,
        val tags: List<Any>,
        val title: String,
        val type: String
    ) {
        val previewImageUrl
            get() =
                "https://resources-qa.redbull.tv/$id/rbtv_display_art_square/im:i:w_400,c_fill,q_65?namespace=rbtv"


        val itemViewType: Int
            get() = HorizontalAdapter.CHANNEL_VIEW
    }


    data class LinearMeta(
        val limit: Int,
        val offset: Int,
        val total: Int
    )
}