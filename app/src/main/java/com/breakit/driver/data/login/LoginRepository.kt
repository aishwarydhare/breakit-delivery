package com.breakit.driver.data.login

import androidx.annotation.WorkerThread
import com.breakit.driver.Common
import com.breakit.driver.model.GenericResponseBase
import com.breakit.driver.model.Session
import com.breakit.driver.network.AuthInterceptor
import com.breakit.driver.utils.async.ThreadManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for session operations
 */
class LoginRepository @Inject constructor(
    private val api: LoginApi,
    private val threadManager: ThreadManager,
    private val loginPrefs: LoginPrefs,
    private val authInterceptor: AuthInterceptor
) {

    /**
     * Check if user is logged in
     *
     * @return True if user is logged in, otherwise False
     */
    fun isLoggedIn(): Boolean = loginPrefs.isLoggedIn()

    /**
     * Store the user session to preferences
     *
     * @param session [Session] details
     */
    fun storeSession(session: Session) {
        loginPrefs.storeUser(session)
        authInterceptor.updateAuthToken(loginPrefs.authToken())
        Common.isLoggedIn.value = true
    }

    /**
     * Delete saved user session details
     *
     */
    fun deleteSession() {
        loginPrefs.clear()
        authInterceptor.clear()
        Common.isLoggedIn.value = false
    }

    /**
     * Login or signup using mobile at same time
     *
     * @param mobile username i.e. phone number to send otp on
     *
     * @return Pair of response [Session] and True/False if request was succeeded/failed
     */
    @WorkerThread
    @Throws(Exception::class)
    suspend fun requestOtp(
        mobile: String
    ): Pair<GenericResponseBase<Any>?, Boolean> {
        val params = HashMap<String, String>()
        params[MOBILE] = mobile

        return withContext(threadManager.io) {
            val response = api.requestOtp(params)
            response.body() to response.isSuccessful
        }
    }

    /**
     * Login or signup using mobile at same time
     *
     * @param mobile username i.e. phone number to send otp on
     *
     * @return Pair of response [Session] and True/False if request was succeeded/failed
     */
    @WorkerThread
    @Throws(Exception::class)
    suspend fun verifyOtp(
        mobile: String,
        otp: String
    ): Pair<Session?, Boolean> {
        val params = HashMap<String, String>()
        params[MOBILE] = mobile
        params[OTP] = otp

        return withContext(threadManager.io) {
            val response = api.verifyOtp(params)
            response.body()!!.data to response.isSuccessful
        }
    }

    companion object {
        private const val MOBILE = "mobile"
        private const val OTP = "otp"
    }
}
