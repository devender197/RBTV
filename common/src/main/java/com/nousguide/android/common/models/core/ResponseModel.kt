package com.nousguide.android.common.models.core

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.nousguide.android.common.manager.LogManager
import retrofit2.Response
import java.net.HttpURLConnection

data class ResponseModel(
    var statusCode: Int = 0,
    var isSuccess: Boolean = false,
    var errorMessage: String = "",
    var responseString: String = "",
    var status: String? = ""
) {

    companion object {
        const val parseErrorMessage = "Couldn't parse response!"
        const val genericErrorMessage = "Something went wrong!"

        fun parse(response: Response<Any>): ResponseModel {
            val code = response.code()
            return if (response.isSuccessful) {
                val body = Gson().toJson(response.body())
                val json = JsonParser().parse(body).asJsonObject
                val status = json.get("status")?.asString ?: ""
                if (isSuccess(code, body)) {
                    LogManager.log("Success: $body")
                    ResponseModel(
                        statusCode = code,
                        responseString = body,
                        isSuccess = true,
                        status = status
                    )
                } else {
                    parseError(code, body, status)
                }
            } else {
                val errorBody = response.errorBody()?.charStream()?.readText() ?: ""
                parseError(code, errorBody, status = null)
            }
        }

        private fun parseError(code: Int, errorBody: String, status: String?): ResponseModel {
            return try {
                val json = JsonParser().parse(errorBody).asJsonObject
                val message = json.get("message").asString
                errorHandler(code, message, status)
            } catch (e: Exception) {
                LogManager.log(e.toString())
                errorHandler(code, parseErrorMessage, status)
            }
        }

        private fun isSuccess(code: Int, responseString: String): Boolean {
            try {
                val json = JsonParser().parse(responseString).asJsonObject
                val success = json.get("success").asBoolean
                if (!success) return false
            } catch (e: Exception) {
                LogManager.log(e.toString())
            }
            return code == HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_CREATED
        }

        private fun errorHandler(code: Int? = null, message: String? = null, status: String?): ResponseModel {
            val genericErrorCode = 666
            val failureMessage = message ?: genericErrorMessage
            LogManager.log("Failure: $failureMessage")
            return ResponseModel(
                statusCode = code ?: genericErrorCode,
                isSuccess = false,
                errorMessage = failureMessage,
                responseString = "",
                status = status
            )
        }
    }
}
