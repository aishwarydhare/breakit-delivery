package com.breakit.driver.ui.orders

import com.breakit.driver.data.delivery.DeliveryRepository
import com.breakit.driver.ui.common.UiStateViewModel
import javax.inject.Inject

/**
 * ViewModel for login view
 */
class OrdersViewModel @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) : UiStateViewModel() {
}
