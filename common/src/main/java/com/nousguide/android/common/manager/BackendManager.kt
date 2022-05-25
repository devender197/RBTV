package com.nousguide.android.common.manager

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.nousguide.android.common.base.BaseApi
import com.nousguide.android.common.models.LinearChannel
import com.nousguide.android.common.models.Product
import com.nousguide.android.common.models.ProductCollection
import com.nousguide.android.common.models.UserModel
import com.nousguide.android.common.models.common.EndpointModel
import com.nousguide.android.common.models.continuewatching.ContinueWatchingItem
import com.nousguide.android.common.models.core.RequestModel
import com.nousguide.android.common.models.core.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject


class BackendManager @Inject constructor(
    private val api: BaseApi,
    private val cacheManager: CacheManager
) {

    suspend fun getProduct(id: String): Product? {
        val path = " ${EndpointModel.PRODUCT.path}/$id?namespace=rbtv"
        return call(RequestModel(EndpointModel.PRODUCT, path, null))
    }

    suspend fun getCollectionForProductId(id: String): ProductCollection? {
        val path = " ${EndpointModel.COLLECTION.path}/$id?namespace=rbtv"
        return call(RequestModel(EndpointModel.COLLECTION, path, null))
    }

    suspend fun getContinueWatching(userId: String = UserModel.demoUser.userId): ContinueWatchingItem? {
        val path = " ${EndpointModel.CONTINUE_WATCHING.path}/$userId?namespace=rbtv"
        return call(RequestModel(EndpointModel.CONTINUE_WATCHING, path, null))
    }

    suspend fun getLinearChannel(): LinearChannel? {
        val path = " ${EndpointModel.LINEAR_CHANNEL.path}/?namespace=rbtv"
        return call(RequestModel(EndpointModel.LINEAR_CHANNEL, path, null))
    }

    private suspend inline fun <reified T : Any> call(request: RequestModel): T? {
        return withContext(Dispatchers.IO) {
            val endpoint = request.endpoint
            val url = request.path
            val params = request.params ?: hashMapOf()
            val user = cacheManager.user()
            val accessToken = UserModel.demoUser.idToken
            val uim = UserModel.demoUser.AuthorizationUim
            val gson = GsonBuilder().create()
            user?.let {
                params.apply {
                    set("client_id", user.deviceId)
                    set("user_id", user.userId)
                    set("platform_id", "androidphone")
                }
            }

            var queryPath = request.path
            if (params.keys.isNotEmpty() || endpoint.isAuthenticated) queryPath += "?"
            accessToken.let { aToken ->
                if (endpoint.isAuthenticated) {
                    queryPath += "access_token=$aToken"
                }
            }

            if(endpoint == EndpointModel.CONTINUE_WATCHING) {
                queryPath += "Authorization-Uim=$uim"
            }
            for (key in params.keys) {
                val value = params[key]
                queryPath += "&"
                queryPath += "$key=$value"
            }
            val responseModel = ResponseModel.parse(
                when (endpoint.type) {
                    EndpointModel.Type.POST -> api.post(url, params)
                    EndpointModel.Type.GET -> api.get(url, params)
                    EndpointModel.Type.PUT -> api.put(url, params)
                    EndpointModel.Type.DELETE -> api.delete(url, params)
                }
            )
            LogManager.log(
                "Response: [${endpoint.type.name}] $queryPath \n${responseModel.responseString}"
            )
            try {
                if (responseModel.isSuccess) {
                    val type: Type = object : TypeToken<T>() {}.type
                    val response = gson.fromJson<T>(responseModel.responseString, type)
                    response
                } else null
            } catch (e: Exception) {
                null
            }
        }
    }
}