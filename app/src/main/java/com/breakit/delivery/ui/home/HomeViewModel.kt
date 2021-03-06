package com.breakit.delivery.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.breakit.delivery.MainActivity
import com.breakit.delivery.data.delivery.DeliveryRepository
import com.breakit.delivery.model.Order
import com.breakit.delivery.ui.common.UiStateViewModel
import com.breakit.delivery.utils.UiStateManager.UiState
import com.breakit.delivery.utils.extensions.showErrorSnackbar
import com.breakit.delivery.utils.extensions.showSuccessSnackbar
import com.breakit.delivery.utils.extensions.showWarningSnackbar
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel for home view
 */
class HomeViewModel @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) : UiStateViewModel() {

    val todaysDeliveries = MutableLiveData<List<Order>>(listOf())

    fun getTodayDeliveriesFromRemote() {
        uiState = UiState.LOADING
        viewModelScope.launch {
            val (response, result) = try {
                deliveryRepository.getDeliveriesToday()
            } catch (exception: IOException) {
                uiState = UiState.ERROR
                null to false
            } catch (exception: HttpException) {
                uiState = UiState.ERROR
                null to false
            }
            if (result) response?.let {
                if (it.ok == true) {
                    todaysDeliveries.value = it.data
                    uiState = UiState.LOADED
                } else {
                    error.value = -1
                }
            } else {
                error.value = -11
            }
        }
    }

    private fun refreshDeliveries() {
        getTodayDeliveriesFromRemote()
    }

    fun pickUpDelivery(order: Order, activity: MainActivity) {
        uiState = UiState.LOADING
        viewModelScope.launch {
            val (response, result) = try {
                deliveryRepository.pickupDelivery(orderId = order.id!!)
            } catch (exception: IOException) {
                uiState = UiState.ERROR
                null to false
            } catch (exception: HttpException) {
                uiState = UiState.ERROR
                null to false
            }
            if (result) response?.let {
                if (it.ok == true) {
                    uiState = UiState.LOADED
                    activity.showSuccessSnackbar("Order Picked Up")
                    refreshDeliveries()
                } else {
                    val msg = it.data
                    activity.showErrorSnackbar("err : $msg")
                }
            } else {
                error.value = -2
            }
        }
    }

    fun completeDelivery(order: Order, activity: MainActivity) {
        uiState = UiState.LOADING
        viewModelScope.launch {
            val (response, result) = try {
                deliveryRepository.completeDelivery(orderId = order.id!!)
            } catch (exception: IOException) {
                uiState = UiState.ERROR
                null to false
            } catch (exception: HttpException) {
                uiState = UiState.ERROR
                null to false
            }
            if (result) response?.let {
                if (it.ok == true) {
                    uiState = UiState.LOADED
                    activity.showSuccessSnackbar("Order Delivered")
                    refreshDeliveries()
                } else {
                    val msg = it.data
                    activity.showErrorSnackbar("err : $msg")
                }
            } else {
                error.value = -3
            }
        }
    }

    fun abortDelivery(order: Order, reason: String, activity: MainActivity) {
        uiState = UiState.LOADING
        viewModelScope.launch {
            val (response, result) = try {
                deliveryRepository.failDelivery(orderId = order.id!!, reason = reason)
            } catch (exception: IOException) {
                uiState = UiState.ERROR
                null to false
            } catch (exception: HttpException) {
                uiState = UiState.ERROR
                null to false
            }
            if (result) response?.let {
                if (it.ok == true) {
                    uiState = UiState.LOADED
                    activity.showWarningSnackbar("Order Aborted")
                    refreshDeliveries()
                } else {
                    val msg = it.data
                    activity.showErrorSnackbar("err : $msg")
                }
            } else {
                error.value = -4
            }
        }
    }

}
