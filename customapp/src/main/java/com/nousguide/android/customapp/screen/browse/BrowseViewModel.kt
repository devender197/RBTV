package com.nousguide.android.customapp.screen.browse

import com.nousguide.android.common.base.BaseViewModel
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.manager.BackendManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(private val backendManager: BackendManager) :
    BaseViewModel(), CardClickListener {

    override fun onItemClickListener(item: Any) {}
}