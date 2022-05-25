package com.nousguide.android.tv.screen.screen.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.nousguide.android.common.R
import com.nousguide.android.common.base.BaseViewModel
import com.nousguide.android.common.commom.CommonLiveEvent
import com.nousguide.android.common.manager.CacheManager
import com.nousguide.android.common.manager.LoginManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginManager: LoginManager,
    private val cacheManager: CacheManager
) : BaseViewModel() {

    val eventLoadView = CommonLiveEvent<Void>()
    val eventLoadHud = CommonLiveEvent<Boolean>()
    val eventLoadDescription = CommonLiveEvent<Int>()
    val eventLoadVideo = CommonLiveEvent<String>()
    val eventEmailClick = CommonLiveEvent<Void>()
    val eventFbClick = CommonLiveEvent<Void>()
    val eventGoogleClick = CommonLiveEvent<Intent>()
    val eventHandleCloseClick = CommonLiveEvent<Void>()
    val eventOnSuccessLogin = CommonLiveEvent<Void>()

    var isLandScape: Boolean = false
    var createOrSignin: Boolean = false

    fun loadView(context: Context) {
        val path =
            "android.resource://" + context.packageName.toString() + "/" + R.raw.login_background
        eventLoadVideo.postValue(path)
        eventLoadView.call()
        loadDescription()
    }

    fun onEmailClick() {
        eventEmailClick.call()
    }

    fun onFbClick() {
        eventFbClick.call()
    }

    fun onCloseClick() {
        eventHandleCloseClick.call()
    }
    fun showHud(shouldShow: Boolean) {
        eventLoadHud.postValue(shouldShow)
    }

    fun onGoogleClick() {
        eventLoadHud.postValue(true)
        eventGoogleClick.postValue(loginManager.getGoogleSignInIntent())
    }

    fun onSuccessLogin(uri: Uri) {
        viewModelScope.launch {
            val userModel = loginManager.getUserMode(uri)
            cacheManager.user(userModel)
            userModel.accessToken.let { token -> cacheManager.accessToken(token) }
            eventOnSuccessLogin.call()
        }
    }

    private fun loadDescription() {
        when {
            isLandScape && createOrSignin -> eventLoadDescription.postValue(R.string.account_create)
            isLandScape && !createOrSignin -> eventLoadDescription.postValue(R.string.account_description_signin)
            !isLandScape && createOrSignin -> eventLoadDescription.postValue(R.string.account_create)
            else -> eventLoadDescription.postValue(R.string.account_description_signin)
        }
    }
}