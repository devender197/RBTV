package com.nousguide.android.common.models

data class Product(
    val collections: List<Collection>,
    val content_type: String,
    val id: String,
    val links: List<Any>,
    val overrides: List<Any>,
    val resources: List<String>,
    val title: String,
    val type: String
)

data class Collection(
    val id: String,
    val label: String,
    val list_type: String,
    val request_data: RequestData,
    val short_label: String
)

data class RequestData(
    val params: List<Param>,
    val placeholders: List<Placeholder>,
    val request_type: String,
    val url: String
)

data class Param(
    val name: String,
    val type: String,
    val value: String
)

data class Placeholder(
    val id: String,
    val required: Boolean
)

