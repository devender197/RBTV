package com.nousguide.android.common.models.common

import com.nousguide.android.common.BuildConfig.*

enum class EndpointModel {
    PRODUCT,
    COLLECTION,
    LINEAR_CHANNEL, CONTINUE_WATCHING,
    IMAGE_PREVIEW, COVER_IMAGE, TITLE_IMAGE, VIDEO_PREVIEW;

    enum class Type {
        GET,
        POST,
        PUT,
        DELETE
    }

    val type: Type
        get() {
            return when (this) {
                COLLECTION, PRODUCT, LINEAR_CHANNEL, CONTINUE_WATCHING -> Type.GET
                else -> Type.GET
            }
        }

    val isAuthenticated: Boolean
        get() {
            return when (this) {
                COLLECTION, LINEAR_CHANNEL, CONTINUE_WATCHING -> false
                PRODUCT, IMAGE_PREVIEW, COVER_IMAGE, TITLE_IMAGE, VIDEO_PREVIEW -> true
            }
        }

    val path: String
        get() {
            val prefix = kBaseUrl
            val historyUrl = kHistoryUrl
            return when (this) {
                PRODUCT -> "$prefix/products/v4"
                COLLECTION -> "$prefix/collections/v4"
                LINEAR_CHANNEL -> "$prefix/v3/collections/linear-channels"
                CONTINUE_WATCHING -> "$historyUrl/history/v1.0/continueWatching"
                IMAGE_PREVIEW -> kResourceUrl
                COVER_IMAGE -> kResourceUrl
                TITLE_IMAGE -> kResourceUrl
                VIDEO_PREVIEW -> kResourceUrl
            }
        }

    fun getResourceUrl(resourceId: String, dim: Int = 300, quality: Int = 65) = when (this) {
        VIDEO_PREVIEW -> "$path/$resourceId/short_preview_mp4_medium/video.mp4?namespace=rbtv"
        IMAGE_PREVIEW -> "$path/$resourceId/rbtv_display_art_square/im:i:w_$dim,c_fill,q_$quality?namespace=rbtv"
        TITLE_IMAGE -> "$path/$resourceId/rbtv_title_treatment_landscape?namespace=rbtv"
        COVER_IMAGE -> "$path/$resourceId/rbtv_cover_art_portrait/im:i:w_$dim,c_fill,q_$quality?namespace=rbtv"
        else -> ""
    }

}