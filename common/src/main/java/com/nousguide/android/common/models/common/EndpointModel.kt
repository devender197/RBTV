package com.nousguide.android.common.models.common

import com.nousguide.android.common.BuildConfig.kBaseUrl
import com.nousguide.android.common.BuildConfig.kHistoryUrl

enum class EndpointModel {
    PRODUCT,
    COLLECTION,
    LINEAR_CHANNEL,CONTINUE_WATCHING;

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
                else -> Type.POST
            }
        }

    val isAuthenticated: Boolean
        get() {
            return when (this) {
                COLLECTION, LINEAR_CHANNEL, CONTINUE_WATCHING -> false
                PRODUCT -> true
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
            }
        }
}