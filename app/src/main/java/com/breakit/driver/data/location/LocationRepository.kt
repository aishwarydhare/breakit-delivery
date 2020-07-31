package com.breakit.driver.data.location

import androidx.annotation.WorkerThread
import com.breakit.driver.model.GenericResponseBase
import com.breakit.driver.utils.async.ThreadManager
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
        params[lat] = lat
        params[lon] = lon
        return withContext(threadManager.io) {
            val response = api.updateLocation(params)
            response.body() to response.isSuccessful
        }
    }

    companion object {
        private const val LAT = "lat"
        private const val LON = "lon"
    }
}
