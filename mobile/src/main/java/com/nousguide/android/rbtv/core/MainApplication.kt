package com.nousguide.android.rbtv.core

import android.app.Application
import com.nousguide.android.rbtv.BuildConfig
import com.nousguide.android.common.onetrust.ConsentManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var consentManager: ConsentManager

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        //configManager.configure(this)
    }

    companion object {
        lateinit var instance: MainApplication
            private set
    }

}