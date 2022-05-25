package com.nousguide.android.common.models

data class UserModel(
    val userId: String,
    val userName: String,
    val idToken: String,
    val refreshToken: String,
    val accessToken: String
) {
    var deviceId: String = "03253139aa364cc1"
    var AuthorizationUim: String? =
        "eyJ0eXAiOiJKV1QiLCJraWQiOiJyYnNpbG8tZGVzaWduLTIwMjIwMTEyIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiI5MTI4MjM2NC01MGEwLTRmZjAtODg3Yi1hM2UxMjIxNGQ1NDciLCJhdWQiOiI1OWVkYTQwZmY0OWJmNTNjYmRiMGQ5NjAiLCJleHAiOjE2NTI3NzAwMjYsImlhdCI6MTY1Mjc2OTcyNiwianRpIjoiNWI1NzYwODgtMGEyMC00MjNiLWJiMTMtMjRkMTg4YjRlYzFkIiwiYXBwbGljYXRpb25faWQiOiI1OWVkYTQwZmY0OWJmNTNjYmRiMGQ5NjAiLCJ2ZXJzaW9uIjoidjYiLCJpc3MiOiJSQi1VSU0iLCJkaXNwbGF5bmFtZSI6IkRldmVuZGVyIiwicHJvZmlsZV9maWVsZHMiOlt7ImZpZWxkSWQiOiI1NWE2MWEzMDk0NWRkMjFjZWM5ZjMwMDUiLCJ1bmlxdWVBbGlhcyI6ImVtYWlsIiwidmFsdWUiOiJkZXZlbmRlcjE5N0BnbWFpbC5jb20ifSx7ImZpZWxkSWQiOiI1NWE2MWEzMDk0NWRkMjFjZWM5ZjMwMDMiLCJ1bmlxdWVBbGlhcyI6Imxhc3RfbmFtZSIsInZhbHVlIjoiU2luZ2gifSx7ImZpZWxkSWQiOiI1NWE2MWEzMDk0NWRkMjFjZWM5ZjMwMDIiLCJ1bmlxdWVBbGlhcyI6ImZpcnN0X25hbWUiLCJ2YWx1ZSI6IkRldmVuZGVyIn1dLCJ2ZXJpZmljYXRpb25fc3RhdHVzIjoidmVyaWZpZWQiLCJpbml0aWFsX3BvbGljaWVzX2FjY2VwdGVkIjp0cnVlLCJ1c2VyX2FjY291bnRzIjp7Imhhc05hdGl2ZSI6dHJ1ZSwicHJvdmlkZXJzIjpbXX0sImNvdW50cnlfb2ZfcmVzaWRlbmNlIjoiVVMiLCJzb2NpYWxQcm92aWRlcklkcyI6e30sInVzZXJfcHJvZmlsZSI6eyI1NWE2MWEzMDk0NWRkMjFjZWM5ZjMwMDUiOiJkZXZlbmRlcjE5N0BnbWFpbC5jb20iLCI1NWE2MWEzMDk0NWRkMjFjZWM5ZjMwMDMiOiJTaW5naCIsIjU1YTYxYTMwOTQ1ZGQyMWNlYzlmMzAwMiI6IkRldmVuZGVyIn0sInVudmVyaWZpZWRfcHJvZmlsZV9maWVsZHMiOnt9LCJ1c2VyX2lkIjoiNjI0ZGY3NGJmNzJmMTc1MjdmMmFhOWEwIiwic2lsb191c2VyX2lkIjoiNjI0ZGY3NGJmNzJmMTc1MjdmMmFhOWExIiwidWlkIjoiNjI0ZGY3NGJmNzJmMTc1MjdmMmFhOWExIn0.cttWZiWOPj0RoL01rWR3E-ulD_JVgU5ctC-2W0-GhoVNm4ObBKoGONMXwqBuFnohW3jvJM67F6kWlSmUs3D1l_oZYH-b0hCDVnYtlWLuNjorQnDoMPoLU5nsLeqEuS7ANgdnxvxZJZRZQqDZTOdTxAt8dxciJzMJ7RsNzugaFbBfiYC-fDe_yt_RY9Zquy6GGuHT77HLhpZyeRxkTIDx01B2ZLyT1iDUf4BHEne3sq_YRjs2BNzt-mJZZ5inytR2DYQRsSY_9FIdTdqqalK196PSCqPsj1h0UDAeufDALDxoqOedqgHHCkxJ505G4rfwg-sxtp5Swlrwa9r2mVFoMQ"

    companion object {
        const val UID = "uid"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val ID_TOKEN = "id_token"



        val demoUser = UserModel(
            userId = "624df74bf72f17527f2aa9a1",
            userName = "Devender",
            idToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhYl90ZXN0aW5nX2dyb3VwIjo5NCwiY2F0ZWdvcnkiOiJzbWFydHBob25lIiwiY291bnRyeV9jb2RlIjoidXMiLCJleHBpcmVzIjoiMjAyMi0wNS0xNVQwNDoxMzoyMi43Mjg2NjMyMzhaIiwibG9jYWxlIjoiZW5fVVMiLCJvc19mYW1pbHkiOiJhbmRyb2lkIiwicmVtb3RlX2lwIjoiOTguNDUuMTk0LjEzMiIsInVhIjoiQW5kcm9pZC80LjEzLjEuNSIsInVpZCI6IjIzZTFhNmQ4LWNiMDEtNDQzYS1iNDBlLTFkOWQzMjM2YTI0MiJ9.MIekaLO9eFTlUfhSoNvXwoQCEpWCJJ3vbsKpT_2Z_JE",
            refreshToken = "d20f14bfae2df10212caa640dbe4cffd",
            accessToken = "22450ec01f1519ec6f7c32887912695f"
        ).apply { deviceId = "03253139aa364cc1" }
    }
}