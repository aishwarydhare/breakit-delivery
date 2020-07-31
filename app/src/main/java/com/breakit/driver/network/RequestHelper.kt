package com.breakit.driver.network

import androidx.collection.ArrayMap
import com.breakit.driver.BuildConfig

data class RequestHelper @JvmOverloads constructor(
    private var apiAuthToken: String = ""
) {

    fun getAuthParams(): ArrayMap<String, String> {
        val params = ArrayMap<String, String>()
        params[APIKEY] = BuildConfig.APIKEY
        params[AUTH_TOKEN] = apiAuthToken
        return params
    }

    fun clear() {
        apiAuthToken = ""
    }

    companion object {
        private const val APIKEY = "Api-Key"
        private const val AUTH_TOKEN = "Auth-Token"
    }
}
