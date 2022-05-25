package com.nousguide.android.common.manager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Base64
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.nousguide.android.common.models.LoginJWT
import com.nousguide.android.common.models.UserModel
import timber.log.Timber


class LoginManager(val context: Context) {

   fun getGoogleSignInIntent(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso).signInIntent
    }

    fun getUserMode(uri: Uri): UserModel {
        val userId = uri.getQueryParameter(UserModel.UID)!!
        val idToken = uri.getQueryParameter(UserModel.ID_TOKEN)!!
        val refreshToken = uri.getQueryParameter(UserModel.REFRESH_TOKEN)!!
        val accessToken = uri.getQueryParameter(UserModel.ACCESS_TOKEN)!!
        val displayName = decodeLoginJWT(idToken).displayname
        return UserModel(userId, displayName, idToken, refreshToken, accessToken)
    }

    @Throws(Exception::class)
    fun decodeLoginJWT(idToken: String): LoginJWT {
        val decodedString = String(Base64.decode(idToken.split("\\.".toRegex())[1], Base64.URL_SAFE))
        Timber.w("Decoded User Id Token: $idToken\n$decodedString")
        return  Gson().fromJson(decodedString, LoginJWT::class.java)
    }
}