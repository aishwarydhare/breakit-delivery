package com.breakit.delivery.network

interface AuthInterceptor {
    fun updateAuthToken(apiToken: String)
    fun clear()
}
