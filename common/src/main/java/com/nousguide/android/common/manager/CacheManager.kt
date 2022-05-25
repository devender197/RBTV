package com.nousguide.android.common.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.google.gson.Gson
import com.nousguide.android.common.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CacheManager(val context: Context) {

    private val dataStore = context.createDataStore("XpassDataStore")

    companion object {
        val kBackendAccessToken = preferencesKey<String>("BackendAccessToken")
        val kBackendUserId = preferencesKey<String>("BackendUserId")
        val kShouldShowPromo = preferencesKey<Boolean>("ShouldShowPromo")
        val kIsLocationEnabled = preferencesKey<Boolean>("IsLocationEnabled")
        val kDidShowLocationPermission = preferencesKey<Boolean>("DidShowLocationPermission")
        val kDidLaunchFromNotification = preferencesKey<String>("DidLaunchFromNotification")
        val kLastFetchFirebaseDate = preferencesKey<String>("LastFetchFirebaseDate")
        val KLastFetchStudioDate = preferencesKey<String>("LastFetchStudiosDate")
        val kCurrentUser = preferencesKey<String>("CurrentUser")
        val kStudios = preferencesKey<String>("Studios")
        val kPurchase = preferencesKey<String>("Purchase")
        val kDashboardModel = preferencesKey<String>("Dashboard")
        val kDidShowReview = preferencesKey<Boolean>("ShouldShowReview")
        val kReviewSession = preferencesKey<String>("UserSession")
    }

    suspend fun isLoggedIn(): Boolean {
        return withContext(Dispatchers.IO) {
            dataStore.data.map {
                val token = Gson().fromJson(it[kBackendAccessToken], String::class.java)
                !token.isNullOrEmpty()
            }.firstOrNull() ?: false
        }
    }

    suspend fun accessToken(): String? {
        return withContext(Dispatchers.IO) {
            dataStore.data.map {
                Gson().fromJson(it[kBackendAccessToken], String::class.java) ?: null
            }.firstOrNull()
        }
    }

    suspend fun accessToken(value: String?) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[kBackendAccessToken] = Gson().toJson(value)
            }
        }
    }

    suspend fun user(value: UserModel?) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[kCurrentUser] = Gson().toJson(value)
            }
        }
    }

    suspend fun user(): UserModel? {
        return withContext(Dispatchers.IO) {
            dataStore.data.map {
                Gson().fromJson(it[kCurrentUser], UserModel::class.java) ?: null
            }.firstOrNull()
        }
    }

    private suspend fun setValue(key: String, value: String) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[preferencesKey<String>(key)] = value
            }
        }
    }

    private suspend fun getValue(key: String): Flow<String> {
        return withContext(Dispatchers.IO) {
            dataStore.data.map {
                it[preferencesKey<String>(key)] ?: ""
            }
        }
    }
}
