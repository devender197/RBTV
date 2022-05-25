package com.nousguide.android.rbtv.core

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moczul.ok2curl.CurlInterceptor
import com.nousguide.android.common.BuildConfig.kBaseUrl
import com.nousguide.android.common.R
import com.nousguide.android.common.base.BaseApi
import com.nousguide.android.common.manager.BackendManager
import com.nousguide.android.common.manager.CacheManager
import com.nousguide.android.common.manager.LogManager
import com.nousguide.android.common.manager.LoginManager
import com.nousguide.android.common.models.UserModel
import com.nousguide.android.common.onetrust.ConsentManager
import com.nousguide.android.common.typeface.TypefaceProvider
import com.nousguide.android.common.utils.NetworkMonitor
import com.nousguide.android.rbtv.BuildConfig
import com.onetrust.otpublishers.headless.Public.DataModel.OTSdkParams
import com.onetrust.otpublishers.headless.Public.DataModel.OTUXParams
import com.onetrust.otpublishers.headless.Public.OTPublishersHeadlessSDK
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    private const val REGULAR_TIMEOUT = 60L

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache =
        Cache(application.cacheDir, 20L * 1024 * 1024)

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: Interceptor,
        curlInterceptor: CurlInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(REGULAR_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REGULAR_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(REGULAR_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(requestInterceptor)
            .addInterceptor(curlInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRequestInterceptor(@Named("header") header: String): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val newBuilder = request.newBuilder().addHeader("Authorization", header)
                .addHeader("Authorization-Uim", UserModel.demoUser.AuthorizationUim!!)
            try {
                chain.proceed(newBuilder.build())
            } catch (exception: Exception) {
                Response.Builder()
                    .request(chain.request())
                    .addHeader("Authorization", header)
                    .addHeader("Authorization-Uim", UserModel.demoUser.AuthorizationUim!!)
                    .protocol(Protocol.HTTP_1_1)
                    .code(408) // status code
                    .message("Request Timeout")
                    .body(
                        ResponseBody.create("application/json; charset=utf-8".toMediaType(), "{}")
                    )
                    .build()
            }
        }

    @Provides
    @Singleton
    fun provideCurlRequestInterceptor(): CurlInterceptor =
        CurlInterceptor { curl -> LogManager.log("Request: $curl") }

    @Provides
    @Singleton
    @Named("header")
    fun provideHeader() = UserModel.demoUser.idToken

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory =
        RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io())

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(kBaseUrl)
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): BaseApi =
        retrofit.create(BaseApi::class.java)


    @Provides
    @Singleton
    fun provideOneTrustSDK(@ApplicationContext context: Context) = OTPublishersHeadlessSDK(context)

    @Provides
    @Singleton
    fun providesOneTrustParams(
        @ApplicationContext context: Context
    ): OTSdkParams {
        val otUIJsonString = try {
            val otUIJsonString =
                context.applicationContext
                    .assets?.open("ot_ui_params.json")?.bufferedReader()
                    .use {
                        it?.readText()
                    }
            otUIJsonString.orEmpty()
        } catch (ex: java.lang.Exception) {
            Timber.e(ex, "Error getting OneTrust JSON UI")
            ""
        }

        // Create OTUXParams object
        val otuxParams =
            OTUXParams.OTUXParamsBuilder.newInstance()
                .setUXParams(JSONObject(otUIJsonString)) // added !! for kt-lint
                .build()

        // Add to OTSdkParams object
        return OTSdkParams.SdkParamsBuilder.newInstance()
            .setOTUXParams(otuxParams)
            .build()
    }

    @Provides
    @Singleton
    fun providesNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor =
        NetworkMonitor(context)

    @Provides
    @Singleton
    @Named("oneTrustDomainId")
    fun providesOneTrustDomainId() = if (BuildConfig.DEBUG) {
        "b5eff907-266a-4a9a-941c-da7a73daa129-test"
    } else {
        "b5eff907-266a-4a9a-941c-da7a73daa129"
    }

    @Provides
    @Singleton
    fun providesMainTypefaceFamilyNameProvider(): TypefaceProvider {
        return object : TypefaceProvider {
            override fun getMainTypeface(): Int {
                return R.font.bull_heavy
            }

            override fun getRegularTypeface(): Int {
                return R.font.bull_regular
            }

            override fun getBoldTypeface(): Int {
                return R.font.bull_bold
            }
        }
    }

    @Provides
    @Singleton
    fun providesConsentManager(
        otPublishersHeadlessSDK: OTPublishersHeadlessSDK,
        oneTrustSdkParams: OTSdkParams,
        networkMonitor: NetworkMonitor,
        typefaceProvider: TypefaceProvider,
        @Named("oneTrustDomainId") oneTrustDomainId: String
    ): ConsentManager =
        ConsentManager(
            otPublishersHeadlessSDK,
            oneTrustSdkParams,
            networkMonitor,
            typefaceProvider,
            oneTrustDomainId
        )

    @Provides
    @Singleton
    fun provideLoginManager(@ApplicationContext context: Context) = LoginManager(context)

    @Provides
    @Singleton
    fun provideBackendManager(api: BaseApi, cacheManager: CacheManager): BackendManager = BackendManager(api,cacheManager)

    @Provides
    @Singleton
    fun provideCacheManager(@ApplicationContext context: Context): CacheManager =
        CacheManager(context)
}
