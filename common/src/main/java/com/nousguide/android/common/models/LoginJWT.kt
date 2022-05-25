package com.nousguide.android.common.models

data class LoginJWT(
    val application_id: String,
    val aud: String,
    val country_of_residence: String,
    val displayname: String,
    val exp: Int,
    val iat: Int,
    val initial_policies_accepted: Boolean,
    val iss: String,
    val jti: String,
    val nonce: String,
    val profile_fields: List<ProfileField>,
    val silo_user_id: String,
    val socialProviderIds: SocialProviderIds,
    val sub: String,
    val uid: String,
    val user_accounts: UserAccounts,
    val user_id: String,
    val verification_status: String,
    val version: String
)

data class ProfileField(
    val fieldId: String,
    val uniqueAlias: String,
    val value: String
)

class SocialProviderIds

data class UserAccounts(
    val hasNative: Boolean,
    val providers: List<Any>
)