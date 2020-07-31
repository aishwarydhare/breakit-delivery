package com.breakit.driver.network

interface AuthInterceptor {
    fun updateAuthToken(apiToken: String)
    fun clear()
}
