package com.breakit.delivery.data.location

import androidx.annotation.WorkerThread
import com.breakit.delivery.model.GenericResponseBase
import com.breakit.delivery.utils.async.ThreadManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for session operations
 */
class LocationRepository @Inject constructor(
    private val api: LocationApi,
    private val threadManager: ThreadManager
) {

    /**
     * Update location on remote
     *
     */
    @WorkerThread
    @Throws(Exception::class)
    suspend fun updateLocation(
        lat: String,
        lon: String
    ): Pair<GenericResponseBase<Any>?, Boolean> {
        val params = HashMap<String, String>()
        return withContext(threadManager.io) {
            params[LAT] = lat
            params[LON] = lon
            val response = api.updateLocation(params)
            response.body() to response.isSuccessful
        }
    }

    companion object {
        private const val LAT = "lat"
        private const val LON = "lon"
    }
}
