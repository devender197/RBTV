package com.nousguide.android.common.models

import com.nousguide.android.common.adapter.HorizontalAdapter

class ProductCollection(
    val content_type: String,
    val id: String,
    val item_type: String,
    val items: List<Item>?,
    val label: String,
    val list_type: String,
    val meta: Meta,
    val publish_state: String,
    val short_label: String,
    val type: String,
    val whitelist_countries: List<String>
) {

    private val itemType
        get() = Type.values().find { it.name.equals(type, true) }

    val getItems: List<Item>?
        get() = items?.map {
            it.apply {
                itemViewType = when (item_type) {
                    "video" -> HorizontalAdapter.FEATURE_CARD
                    else -> HorizontalAdapter.COVER_VIEW
                }
            }
        }

    class Item(
        val content_type: String = "",
        val cue_tag: String = "",
        val deeplink_playlist: String = "",
        val duration: Int = 0,
        val embed_bottom: List<Any> = listOf(),
        val embed_right: List<Any> = listOf(),
        val episode_number: Int = 0,
        val hide_corner_bug: Boolean = true,
        val id: String = "",
        val immersive: Boolean = true,
        val links: List<Link> = listOf(),
        val long_description: String = "",
        val maturity: String = "",
        val next_playlist: String = "",
        val overrides: List<Override>? = null,
        val parent_id: String = "",
        val playable: Boolean = true,
        val resources: List<String>? = listOf(),
        val season_number: Int = 0,
        val share_url: String = "",
        val short_description: String = "",
        val show_name: String = "",
        val status: Status? = null,
        val subchannel_type: String = "",
        val subheading: String = "",
        val tags: List<String> = listOf(),
        val title: String = "",
        val type: String = "",
        val year: Int = 0
    ) {
        val previewVideoUrl
            get() =
                "https://resources-qa.redbull.tv/$id/short_preview_mp4_medium/video.mp4?namespace=rbtv"

        val previewImageUrl
            get() =
                "https://resources-qa.redbull.tv/$id/rbtv_display_art_square/im:i:w_250,c_fill,q_35?namespace=rbtv"

        val thumbnailImageUrl
            get() =
                "https://resources-qa.redbull.tv/$id/rbtv_display_art_square/im:i:w_200,c_fill,q_65?namespace=rbtv"

        val titleImageUrl
            get() = "https://resources-qa.redbull.tv/$id/rbtv_title_treatment_landscape?namespace=rbtv"

        val coverImageUrl
            get() = "https://resources-qa.redbull.tv/$id/rbtv_cover_art_portrait/im:i:w_300,c_fill,q_65?namespace=rbtv"

        val getResource
            get() = resources?.map { resource ->
                Resource.values().find { it.name.equals(resource, true) }
            }

        var isSkeleton: Boolean = false

        var itemViewType = HorizontalAdapter.FEATURE_CARD


        companion object {
            fun getSkeleton(): List<Item> {
                val list = mutableListOf<Item>()
                repeat(5) { list.add(Item().apply { isSkeleton = true }) }
                return list
            }
        }

        data class Override(
            val collection_id: String,
            val `field`: String,
            val value: String
        )

        data class Status(
            val asset_id: String,
            val code: String,
            val confirmed: Boolean,
            val cue_tag: String,
            val embed_bottom: List<Any>,
            val embed_right: List<Any>,
            val end_time: String,
            val event_bundle_title: String,
            val label: String,
            val message: String,
            val play: String,
            val product_id: String,
            val show_lineup: Boolean,
            val start_time: String,
            val subtitle: String,
            val ttl: Int
        )

        data class Link(
            val action: String,
            val icon: String,
            val id: String,
            val label: String,
            val type: String
        )
    }

    data class Meta(
        val limit: Int,
        val offset: Int,
        val total: Int
    )

    enum class Type {
        FEATURED,
        CONTINUE_WATCHING,
        COMPACT,
        GENERIC,
        GENERIC_HORIZONTAL,
        SCHEDULE,
        LINEAR_SCHEDULE,
        LINEAR_PLAYLIST,
        UNIVERSAL,
        UNIVERSAL_HORIZONTAL,
        LINEUP_HORIZONTAL_SCHEDULE,
        TV_TOP,
        TV_BOTTOM,
        LINEAR_CHANNELS,
        VIEW_MORE_PRODUCT,
        STORIES,
        HERO_CARDS
    }

}