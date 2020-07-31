package com.breakit.driver.data.login

import com.breakit.driver.model.GenericResponseBase
import com.breakit.driver.model.Session
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Service to make API calls respective to sessions
 */
interface LoginApi {

    /**
     * Request an otp for login or signup
     */
    @FormUrlEncoded
    @POST("/api/v1/auth/request-otp")
    @Throws(Exception::class)
    suspend fun requestOtp(@FieldMap payload: Map<String, String>): Response<GenericResponseBase<Any>>

    /**
     * Verify otp to receive auth token
     */
    @FormUrlEncoded
    @POST("/api/v1/auth/verify-otp")
    @Throws(Exception::class)
    suspend fun verifyOtp(@FieldMap payload: Map<String, String>): Response<GenericResponseBase<Session>>

    companion object {

        /**
         * Factory function for [LoginApi]
         */
        fun create(retroFit: Retrofit): LoginApi = retroFit.create(
            LoginApi::class.java
        )
    }
}
