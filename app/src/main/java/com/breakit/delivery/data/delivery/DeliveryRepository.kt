package com.breakit.delivery.data.delivery

import androidx.annotation.MainThread
import com.breakit.delivery.model.GenericResponseBase
import com.breakit.delivery.model.Order
import com.breakit.delivery.utils.async.ThreadManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for bookmark operations
 */
class DeliveryRepository @Inject constructor(
    private val api: DeliveryApi,
    private val threadManager: ThreadManager
) {

    /**
     * Get todays deliveries to do by driver
     *
     */
    @MainThread
    suspend fun getDeliveriesToday(): Pair<GenericResponseBase<List<Order>>?, Boolean> {
        return withContext(threadManager.io) {
            val response = api.deliveriesToday()
            response.body() to response.isSuccessful
        }
    }

    /**
     * Get deliveries history by driver
     *
     */
    @MainThread
    suspend fun getDeliveriesHistory(): Pair<GenericResponseBase<List<Order>>?, Boolean> {
        return withContext(threadManager.io) {
            val response = api.deliveriesHistory()
            response.body() to response.isSuccessful
        }
    }

    /**
     * Change order status to picked up
     *
     */
    @MainThread
    suspend fun pickupDelivery(
        orderId: Int
    ): Pair<GenericResponseBase<Any>?, Boolean> {
        return withContext(threadManager.io) {
            val params = HashMap<String, String>()
            params["order_id"] = orderId.toString()
            val response = api.deliveryPicked(params)
            response.body() to response.isSuccessful
        }
    }

    /**
     * Change order status to completed
     *
     */
    @MainThread
    suspend fun completeDelivery(
        orderId: Int
    ): Pair<GenericResponseBase<Any>?, Boolean> {
        return withContext(threadManager.io) {
            val params = HashMap<String, String>()
            params["order_id"] = orderId.toString()
            val response = api.deliveryComplete(params)
            response.body() to response.isSuccessful
        }
    }

    /**
     * Change order status to failed, provide reason
     *
     */
    @MainThread
    suspend fun failDelivery(
        orderId: Int,
        reason: String
    ): Pair<GenericResponseBase<Any>?, Boolean> {
        return withContext(threadManager.io) {
            val params = HashMap<String, String>()
            params["order_id"] = orderId.toString()
            params["reason"] = reason
            val response = api.failDelivery(params)
            response.body() to response.isSuccessful
        }
    }

}
