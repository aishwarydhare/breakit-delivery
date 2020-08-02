package com.breakit.delivery

import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.breakit.delivery.model.Order
import com.breakit.delivery.utils.extensions.showErrorSnackbar
import com.breakit.delivery.utils.extensions.showWarningSnackbar

object Common {

    var slat: String = ""
    var slon: String = ""

    var orderEnRoute: Order? = null

    val isLoggedIn = MutableLiveData<Boolean>(false)

    fun handleCommonApiErrorResponse(it: Int?, activity: MainActivity) {
        if (it != null) {
            when (it) {
                -1000 -> activity.showWarningSnackbar("Please check your connection and try again")
                -404 -> activity.showWarningSnackbar("No Data Found")
                -500 -> activity.showErrorSnackbar("Something went wrong")
                -401 -> {
                    activity.showWarningSnackbar("Please sign in again to continue")
                    isLoggedIn.value = false
                    activity.findNavController(R.id.nav_host_fragment)
                        .popBackStack(R.id.navigation_splash, true)
                }
            }
        }
    }

}
