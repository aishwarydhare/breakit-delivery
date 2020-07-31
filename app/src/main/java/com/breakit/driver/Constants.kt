package com.breakit.driver

import android.Manifest
import com.breakit.driver.service.LocationService

object Constants {

    val SERVICES_TO_START = arrayOf<Class<*>>(
        LocationService::class.java
    )
    val PERMISSIONS_REQUIRED = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.FOREGROUND_SERVICE
    )
    const val NEED_ATTENTION_SERVICE_NOTIFICATION_ID = 421
    const val ONGOING_SERVICE_NOTIFICATION_ID = 511
    const val CRITICAL_NOTIFICATION_CHANNEL_NAME = "BreakItCriticalService";
    const val REQUEST_ID_MULTIPLE_PERMISSIONS = 123

    const val ORDER_STATUS_BOOKED = 1
    const val ORDER_STATUS_OUT_FOR_DELIVERY = 2
    const val ORDER_STATUS_COMPLETED = 3
    const val ORDER_STATUS_CANCELLED = 4
    const val ORDER_STATUS_FAILED = 5

    const val MEAL_DETAIL_CHECKOUT_MODE = 1
    const val MEAL_DETAIL_VIEW_MODE = 2
    const val MEAL_DETAIL_MODIFY_BOOKING_MODE = 3

    const val SIMPLE_DATE_TIME_FORMATTER_VERBOSE = "dd MMM yyyy HH:mm:ss:SSS Z"
    const val ENABLE_GPS_REQUEST_CHECK_SETTINGS = 134
}