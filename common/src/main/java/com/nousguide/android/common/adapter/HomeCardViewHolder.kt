package com.nousguide.android.common.adapter

import com.nousguide.android.common.commom.CommonRecyclerViewAdapter
import com.nousguide.android.common.models.ProductCollection.Item
import kotlinx.coroutines.CoroutineScope

abstract class HomeRowAdapter(scopeProvider: () -> CoroutineScope, val items: List<Item>) :
    CommonRecyclerViewAdapter(scopeProvider)