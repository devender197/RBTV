package com.nousguide.android.common.manager


import com.nousguide.android.common.BuildConfig
import timber.log.Timber

class LogManager {

    companion object {

        //private val crashlyticsInstance = FirebaseCrashlytics.getInstance()

        fun log(message: String) {
            if (BuildConfig.DEBUG) Timber.d(message)
            //else crashlyticsInstance.log(message)
        }

        /*fun log(exception: Exception) {
            if (BuildConfig.DEBUG) Timber.d(exception)
            else crashlyticsInstance.recordException(exception)
        }*/
    }
}
