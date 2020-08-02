package com.breakit.delivery

import androidx.lifecycle.ViewModel
import com.breakit.delivery.data.login.LoginRepository
import javax.inject.Inject

/**
 * Parent Viewmodel for all fragment
 *
 * @param loginRepository [LoginRepository] to verify/get user login details
 */
class MainViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
}
