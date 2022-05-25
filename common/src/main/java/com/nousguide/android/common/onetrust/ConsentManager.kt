package com.nousguide.android.common.onetrust

import android.content.Context
import android.webkit.WebView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.nousguide.android.common.commom.CommonLiveEvent
import com.nousguide.android.common.manager.LogManager
import com.nousguide.android.common.typeface.TypefaceProvider
import com.nousguide.android.common.utils.NetworkMonitor
import com.onetrust.otpublishers.headless.Public.DataModel.OTConfiguration
import com.onetrust.otpublishers.headless.Public.DataModel.OTSdkParams
import com.onetrust.otpublishers.headless.Public.OTCallback
import com.onetrust.otpublishers.headless.Public.OTPublishersHeadlessSDK
import com.onetrust.otpublishers.headless.Public.Response.OTResponse
import timber.log.Timber

class ConsentManager(
    private val oneTrustSDK: OTPublishersHeadlessSDK,
    private val oneTrustSdkParams: OTSdkParams,
    private val networkMonitor: NetworkMonitor,
    private val typefaceProvider: TypefaceProvider,
    private val oneTrustDomainId: String
) : OTEventListenerAdapter() {

    private val consentEvents = CommonLiveEvent<ConsentViewState>()
    private var _isInitialized: Boolean = false
    private var isInPrefsUIFromSplashScreen = true
    private var consentUpdatedInPrefsUI = false
    val isInitialized: Boolean
        get() = _isInitialized

    fun getConsentEvents() = consentEvents

    init {
        initializeSDK()
    }

    private fun initializeSDK() {
        if (!networkMonitor.checkNetworkConnection()) {
            log("No active internet connection to initialize OneTrust SDK")
            return
        }

        oneTrustSDK.startSDK(DOMAIN_URL_ENVIRONMENT, oneTrustDomainId, LOCALE, oneTrustSdkParams,
            object : OTCallback {
                override fun onSuccess(response: OTResponse) {
                    _isInitialized = true

                    consentEvents.postValue(ConsentViewState.InitSuccessful(response))
                    oneTrustSDK.addEventListener(this@ConsentManager)
                }

                override fun onFailure(response: OTResponse) {
                    _isInitialized = false
                    consentEvents.postValue(ConsentViewState.InitFailure(response))
                }
            }
        )
    }

    private fun prepareFontConfiguration(appContext: Context): OTConfiguration? {
        return try {
            val regularTypeface = ResourcesCompat.getFont(
                appContext,
                typefaceProvider.getRegularTypeface()
            )
            val boldTypeface = ResourcesCompat.getFont(
                appContext,
                typefaceProvider.getBoldTypeface()
            )
            return if (regularTypeface != null && boldTypeface != null) {
                val otConfigurationBuilder = OTConfiguration.OTConfigurationBuilder.newInstance()
                otConfigurationBuilder.addOTTypeFace("BullText-Regular", regularTypeface)
                otConfigurationBuilder.addOTTypeFace("BullText-Bold", boldTypeface)
                otConfigurationBuilder.build()
            } else {
                null
            }
        } catch (ex: Exception) {
            Timber.e(ex)
            null
        }
    }

    fun showConsentBannerUI(activity: FragmentActivity) {
        if (oneTrustSDK.domainGroupData.length() > 0) {
            prepareFontConfiguration(activity.applicationContext)?.let {
                oneTrustSDK.showBannerUI(activity, it)
            } ?: oneTrustSDK.showBannerUI(activity)
        } else {
            log("OneTrust SDK is not initialized")
        }
    }

    fun showConsentPreferenceCenterUI(activity: FragmentActivity) {
        if (oneTrustSDK.domainGroupData.length() > 0) {
            prepareFontConfiguration(activity.applicationContext)?.let {
                oneTrustSDK.showPreferenceCenterUI(activity, it)
            } ?: oneTrustSDK.showPreferenceCenterUI(activity)
        } else {
            log("OneTrust SDK is not initialized")
        }
    }

    fun log(message: String) {
        LogManager.log(message)
    }

    override fun onShowPreferenceCenter() {
        // populate this value based on whether user came Preference Center UI
        // from Splash Screen Banner or from Settings Screen
        isInPrefsUIFromSplashScreen = shouldShowConsentBanner()
    }

    override fun onPreferenceCenterPurposeConsentChanged(purposeId: String?, consentStatus: Int) {
        consentUpdatedInPrefsUI = true
    }

    override fun onBannerClickedAcceptAll() {
        consentEvents.postValue(ConsentViewState.AcceptAll)
    }

    override fun onBannerClickedRejectAll() {
        consentEvents.postValue(ConsentViewState.RejectAll)
    }

    override fun onPreferenceCenterAcceptAll() {
        if (isInPrefsUIFromSplashScreen) {
            // user came on Preference Center UI from Splash Screen Banner
            consentEvents.postValue(ConsentViewState.AcceptAll)
        } else {
            // user came on Preference Center UI from Settings Screen
            consentEvents.postValue(ConsentViewState.RebootRequired)
        }
    }

    override fun onPreferenceCenterRejectAll() {
        if (isInPrefsUIFromSplashScreen) {
            // user came on Preference Center UI from Splash Screen Banner
            consentEvents.postValue(ConsentViewState.AcceptAll)
        } else {
            // user came on Preference Center UI from Settings Screen
            consentEvents.postValue(ConsentViewState.AcceptAll)
        }
    }

    override fun onPreferenceCenterConfirmChoices() {
        if (isInPrefsUIFromSplashScreen) {
            // user came on Preference Center UI from Splash Screen Banner
            consentEvents.postValue(ConsentViewState.AcceptAll)
        } else if (consentUpdatedInPrefsUI) {
            // user came on Preference Center UI from Settings Screen
            consentUpdatedInPrefsUI = false
            consentEvents.postValue(ConsentViewState.RebootRequired)
        }
    }

    fun consentGrantedFor(sdkName: SDKNames, userInitiated: Boolean): Boolean {
        val consentGranted = oneTrustSDK.getConsentStatusForGroupId(sdkName.categoryId) > 0
        if (!consentGranted && userInitiated) {
            consentEvents.postValue(ConsentViewState.FeatureDisabled)
        }
        return consentGranted
    }

    /**
     * show consent banner to user only if
     * the user never opted for acceptance or rejection
     */
    fun shouldShowConsentBanner() = oneTrustSDK.shouldShowBanner()

    /**
     * this method sends out the consent that user has
     * set in the app to the webview in the
     * form of Javascript
     * shouldOverrideUrlLoading & onPageFinished
     * methods should be overrided for the webview
     * and this call should be on the top
     * @param webView The webview on which the consent
     *                needs to be passed
     */
    fun sendAppConsentToWebView(webView: WebView) {
        webView.evaluateJavascript(
            "javascript:${oneTrustSDK.otConsentJSForWebView}",
            null
        )
    }

    companion object {
        const val DOMAIN_URL_ENVIRONMENT = "cdn.cookielaw.org"
        const val LOCALE = "en"
        const val CONSENT_MANAGER_CATEGORY_ID = "00000"
        const val PERFORMANCE_CATEGORY_ID = "C0002"
        const val MARKETING_CATEGORY_ID = "C0004"
    }

    sealed class ConsentViewState {
        object AcceptAll : ConsentViewState()
        object RejectAll : ConsentViewState()
        object RebootRequired : ConsentViewState()
        object FeatureDisabled : ConsentViewState()
        data class InitSuccessful(val oTResponse: OTResponse) : ConsentViewState()
        data class InitFailure(val oTResponse: OTResponse) : ConsentViewState()
    }
}
