package com.breakit.driver.data.location

import com.breakit.driver.model.GenericResponseBase
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Service to make API calls respective to driver location
 */
interface LocationApi {

    /**
     * Update location of driver
     */
    @FormUrlEncoded
    @POST("/api/v1/location/update")
    @Throws(Exception::class)
    suspend fun updateLocation(
        @FieldMap payload: Map<String, String>
    ): Response<GenericResponseBase<Any>>

    companion object {

        /**
         * Factory function for [LocationApi]
         */
        fun create(retroFit: Retrofit): LocationApi = retroFit.create(
            LocationApi::class.java
        )
    }
}
