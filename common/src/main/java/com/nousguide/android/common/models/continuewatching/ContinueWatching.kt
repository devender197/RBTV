package com.nousguide.android.common.models.continuewatching

data class ContinueWatching(
    val content_type: String,
    val id: String,
    val item_type: String,
    val items: List<ContinueWatchingItem>,
    val label: String,
    val list_type: String,
    val meta: ContinueWatchingMeta,
    val publish_state: String,
    val short_label: String,
    val type: String,
    val whitelist_countries: List<String>
)

data class ContinueWatchingItem(
    val content_type: String,
    val deeplink_playlist: String,
    val duration: Int,
    val id: String,
    val immersive: Boolean,
    val links: List<Link>,
    val long_description: String,
    val maturity: String,
    val next_playlist: String,
    val overrides: List<Override>,
    val parent_id: String,
    val playable: Boolean,
    val resources: List<String>,
    val share_url: String,
    val short_description: String,
    val subheading: String,
    val title: String,
    val type: String
)

data class ContinueWatchingMeta(
    val limit: Int,
    val offset: Int,
    val total: Int
)

data class Link(
    val action: String,
    val icon: String,
    val id: String,
    val label: String,
    val type: String
)

data class Override(
    val collection_id: String,
    val `field`: String,
    val value: String
)