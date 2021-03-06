package com.nousguide.android.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancelChildren

open class BaseViewModel : ViewModel() {

    fun clearJobs() {
        viewModelScope.coroutineContext.cancelChildren()
    }
}
