package com.breakit.delivery.data.delivery

import com.breakit.delivery.model.GenericResponseBase
import com.breakit.delivery.model.Order
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Service to make API calls for Deliveries by driver
 */
interface DeliveryApi {

    /**
     * Get active deliveries by driver
     */
    @POST("api/v1/delivery/list")
    @Throws(Exception::class)
    suspend fun deliveriesToday(): Response<GenericResponseBase<List<Order>>>

    /**
     * Get past deliveries by driver
     */
    @POST("api/v1/order/next")
    @Throws(Exception::class)
    suspend fun deliveriesHistory(): Response<GenericResponseBase<List<Order>>>

    /**
     * Mark order as picked and en route
     */
    @FormUrlEncoded
    @POST("api/v1/delivery/picked")
    @Throws(Exception::class)
    suspend fun deliveryPicked(
        @FieldMap payload: Map<String, String>
    ): Response<GenericResponseBase<Any>>

    /**
     * Mark order as completed
     */
    @FormUrlEncoded
    @POST("api/v1/delivery/complete")
    @Throws(Exception::class)
    suspend fun deliveryComplete(
        @FieldMap payload: Map<String, String>
    ): Response<GenericResponseBase<Any>>

    /**
     * Mark delivery as failed
     */
    @FormUrlEncoded
    @POST("api/v1/delivery/fail")
    @Throws(Exception::class)
    suspend fun failDelivery(
        @FieldMap payload: Map<String, String>
    ): Response<GenericResponseBase<Any>>

    companion object {
        /**
         * Factory function for [DeliveryApi]
         */
        fun create(retroFit: Retrofit): DeliveryApi = retroFit.create(
            DeliveryApi::class.java
        )
    }
}
