package com.breakit.delivery.ui.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.breakit.delivery.data.login.LoginRepository
import com.breakit.delivery.model.Session
import com.breakit.delivery.ui.common.UiStateViewModel
import com.breakit.delivery.utils.UiStateManager.UiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel for login view
 */
class LoginBottomSheetViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : UiStateViewModel() {

    var hideOtpLayout = ObservableBoolean(true)
    var submitButtonText = ObservableField("Get OTP")

    /**
     * Store session
     */
    private fun storeSession(session: Session) {
        loginRepository.storeSession(session)
    }

    fun requestOtp(mobile: String) {
        uiState = UiState.LOADING
        viewModelScope.launch {
            val (session, result) = try {
                loginRepository.requestOtp(mobile)
            } catch (exception: IOException) {
                uiState = UiState.ERROR
                null to false
            } catch (exception: HttpException) {
                uiState = UiState.ERROR
                null to false
            }
            if (result) session?.let {
                if (it.ok == true) {
                    hideOtpLayout.set(false)
                    submitButtonText.set("Submit OTP")
                    uiState = UiState.LOADED
                }
            }
        }
    }

    fun verifyOtp(mobile: String, otp: String) {
        uiState = UiState.LOADING
        viewModelScope.launch {
            val (session, result) = try {
                loginRepository.verifyOtp(mobile, otp)
            } catch (exception: IOException) {
                uiState = UiState.ERROR
                null to false
            } catch (exception: HttpException) {
                uiState = UiState.ERROR
                null to false
            }
            if (result) session?.let {
                storeSession(session)
            } else {
                error.value = -1
            }
        }
    }
}
