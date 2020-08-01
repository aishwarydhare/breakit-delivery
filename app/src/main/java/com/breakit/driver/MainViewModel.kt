package com.breakit.driver

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.breakit.driver.data.login.LoginRepository
import javax.inject.Inject

/**
 * Parent Viewmodel for all fragment
 *
 * @param loginRepository [LoginRepository] to verify/get user login details
 */
class MainViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    var arePermissionsGranted = ObservableBoolean(false)

}
