package com.nousguide.android.common.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.ConnectivityManager.TYPE_WIFI
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class NetworkMonitor @Inject constructor(private val appContext: Context) : BroadcastReceiver() {

    private val networkChangeListeners = HashSet<NetworkStatusChangeListener>()

    private var currentNetworkType = -1

    init {
        val intentFilter = IntentFilter().apply {
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction("android.net.wifi.WIFI_STATE_CHANGED")
        }
        appContext.registerReceiver(this, intentFilter)
    }

    val connectedToMobile get() = checkNetworkConnection() && currentNetworkType == TYPE_MOBILE

    fun checkNetworkConnection(callback: NetworkStatusChangeListener) =
        if (checkNetworkConnection()) callback.onNetworkUp() else callback.onNetworkDown()

    // ACCESS_NETWORK_STATE is declared in the applib and tv manifests
    @SuppressLint("MissingPermission")
    @JvmOverloads
    fun checkNetworkConnection(silent: Boolean = false): Boolean {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = connectivityManager?.activeNetworkInfo
        if (networkInfo?.isConnectedOrConnecting == true) {
            val newNetworkType = networkInfo.type
            val alreadyHadConnection = currentNetworkType >= 0
            val differentNetworkType = newNetworkType != currentNetworkType
            currentNetworkType = newNetworkType
            if (!alreadyHadConnection) {
                Timber.d("Network(${networkInfo.typeName}) Connected")
                if (!silent) {
                    networkChangeListeners.forEach { it.onNetworkUp() }
                }
            } else if (newNetworkType == TYPE_WIFI && differentNetworkType) {
                // only notify of joinedWifi, i.e. 3G-> WiFi
                Timber.d("Joined WiFi")
                if (!silent) {
                    networkChangeListeners.forEach { it.onJoinedWiFi() }
                }
            }
            return true
        } else {
            currentNetworkType = -1
            Timber.v("Network Down")
            if (!silent) {
                networkChangeListeners.forEach { it.onNetworkDown() }
            }
            return false
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        checkNetworkConnection()
    }

    // Need to register in onResume()
    fun registerForNetworkChanges(listener: NetworkStatusChangeListener) {
        if (!networkChangeListeners.contains(listener)) {
            networkChangeListeners += listener
            Timber.v("Registering $listener")
        }
    }

    // Need to unregister in onPause()
    fun unregisterForNetworkChanges(listener: NetworkStatusChangeListener) {
        networkChangeListeners -= listener
        Timber.v("Unregistered $listener")
    }

    interface NetworkStatusChangeListener {

        fun onNetworkUp() {}

        fun onNetworkDown() {}

        fun onJoinedWiFi() {}
    }
}
