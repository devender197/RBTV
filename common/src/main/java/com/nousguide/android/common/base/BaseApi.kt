package com.nousguide.android.common.base

import retrofit2.Response
import retrofit2.http.*

interface BaseApi {

    @POST
    suspend fun post(
        @Url url: String,
        @Body params: HashMap<String, Any>
    ): Response<Any>

    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap options: HashMap<String, Any>
    ): Response<Any>

    @PUT
    suspend fun put(
        @Url url: String,
        @Body params: HashMap<String, Any>
    ): Response<Any>

    @HTTP(method = "DELETE", hasBody = true)
    suspend fun delete(
        @Url url: String,
        @Body params: HashMap<String, Any>
    ): Response<Any>

}
