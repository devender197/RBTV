package com.nousguide.android.tv.screen.screen.splash

import androidx.lifecycle.viewModelScope
import com.nousguide.android.common.base.BaseViewModel
import com.nousguide.android.common.commom.CommonLiveEvent
import com.nousguide.android.common.manager.CacheManager
import com.nousguide.android.common.onetrust.ConsentManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val cacheManager: CacheManager
    ) : BaseViewModel() {

    val eventLoadView = CommonLiveEvent<Void>()
    val eventLogoDisplayFinish = CommonLiveEvent<Void>()
    val eventShowLogin = CommonLiveEvent<Void>()
    val eventShowHome = CommonLiveEvent<Void>()
    val eventTapLogin = CommonLiveEvent<Void>()
    val eventTapGuest = CommonLiveEvent<Void>()

    fun doLoadView() {
        eventLoadView.call()
    }

    fun onLogoDisplayFinished() {
        eventLogoDisplayFinish.call()
    }

    fun handleLoginFlow(){
        viewModelScope.launch {
            if(cacheManager.isLoggedIn()) eventShowHome.call() else eventShowLogin.call()
        }
    }

}
