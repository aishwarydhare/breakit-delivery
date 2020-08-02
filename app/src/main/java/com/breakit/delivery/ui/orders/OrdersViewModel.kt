package com.breakit.delivery.ui.orders

import com.breakit.delivery.data.delivery.DeliveryRepository
import com.breakit.delivery.ui.common.UiStateViewModel
import javax.inject.Inject

/**
 * ViewModel for login view
 */
class OrdersViewModel @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) : UiStateViewModel() {
}
