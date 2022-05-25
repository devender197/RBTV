package com.nousguide.android.common.onetrust

import com.onetrust.otpublishers.headless.Public.OTEventListener

/**
 * A simple adapter class to limit the number of functions we need to override when
 * creating a OneTrust event listener.
 */
open class OTEventListenerAdapter : OTEventListener() {
    override fun onShowBanner() { }

    override fun onHideBanner() { }

    override fun onBannerClickedAcceptAll() { }

    override fun onBannerClickedRejectAll() { }

    override fun onShowPreferenceCenter() { }

    override fun onHidePreferenceCenter() { }

    override fun onPreferenceCenterAcceptAll() { }

    override fun onPreferenceCenterRejectAll() { }

    override fun onPreferenceCenterConfirmChoices() { }

    override fun onShowVendorList() { }

    override fun onHideVendorList() { }

    override fun onVendorConfirmChoices() { }

    override fun allSDKViewsDismissed(p0: String?) { }

    override fun onVendorListVendorConsentChanged(p0: String?, p1: Int) { }

    override fun onVendorListVendorLegitimateInterestChanged(p0: String?, p1: Int) { }

    override fun onPreferenceCenterPurposeConsentChanged(purposeId: String?, consentStatus: Int) { }

    override fun onPreferenceCenterPurposeLegitimateInterestChanged(p0: String?, p1: Int) { }
}
