package com.nousguide.android.common.onetrust

enum class SDKNames(val categoryId: String) {
    ONE_TRUST(ConsentManager.CONSENT_MANAGER_CATEGORY_ID),
    FACEBOOK(ConsentManager.PERFORMANCE_CATEGORY_ID),
    BRAZE_APPBOY(ConsentManager.MARKETING_CATEGORY_ID),
    APPS_FLYER(ConsentManager.PERFORMANCE_CATEGORY_ID),
    AD_MOB(ConsentManager.MARKETING_CATEGORY_ID),
    ANALYTICS(ConsentManager.PERFORMANCE_CATEGORY_ID),
    BUGSNAG(ConsentManager.PERFORMANCE_CATEGORY_ID),
    CONVIVA(ConsentManager.PERFORMANCE_CATEGORY_ID),
}
