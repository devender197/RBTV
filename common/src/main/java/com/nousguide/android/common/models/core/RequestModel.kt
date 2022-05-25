package com.nousguide.android.common.models.core

import com.nousguide.android.common.models.common.EndpointModel


data class RequestModel(
    val endpoint: EndpointModel,
    val path: String,
    val params: HashMap<String, Any>?
)
