package com.breakit.delivery.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import com.breakit.delivery.Constants
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "LOCATION"

object GpsUtils {
    var isValidationInProgress = false

    fun isGpsEnabled(ctx: Context): Boolean {
        return try {
            val service =
                ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            service.isProviderEnabled(LocationManager.GPS_PROVIDER) && service.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        } catch (e: Exception) {
            Log.e(TAG, "isGpsEnabled: ", e)
            false
        }
    }

    fun checkOrRequestForGpsEnabled(
        ctx: Activity?,
        locationRequest: LocationRequest?
    ) {
        val TAG = "mCus GoogleGeo"
        Log.i(TAG, "checkOrRequestForGpsEnabled: ")
        if (isValidationInProgress) {
            return
        }
        isValidationInProgress = true
        val builder =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true)
        val settingsClient = LocationServices.getSettingsClient(ctx!!)
        val locationSettingsResponseTask =
            settingsClient.checkLocationSettings(builder.build())
        locationSettingsResponseTask.addOnSuccessListener { locationSettingsResponse: LocationSettingsResponse ->
            Log.i(
                TAG,
                "onSuccess: " + locationSettingsResponse.locationSettingsStates
            )
            isValidationInProgress = false
        }
        locationSettingsResponseTask.addOnFailureListener { e: Exception ->
            Log.i(TAG, "onFailure: " + e.message)
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(
                        ctx,
                        Constants.ENABLE_GPS_REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: SendIntentException) {
                    isValidationInProgress = false
                    // Ignore the error.
                }
            }
        }
    }

    fun getGoogleLocationLastKnown(fusedLocationClient: FusedLocationProviderClient) {
        Log.i(TAG, "getGoogleLocationLastKnown: ")
        @SuppressLint("MissingPermission") val locationFetchTask =
            fusedLocationClient.lastLocation
        locationFetchTask.addOnSuccessListener { obj: Location? -> handleLocation(obj) }
        locationFetchTask.addOnCanceledListener {
            Log.i(TAG, "onCanceled: ")
        }
        locationFetchTask.addOnCompleteListener { task: Task<Location>? ->
            Log.i(TAG, "onComplete: ")
        }
        locationFetchTask.addOnFailureListener { e: Exception? ->
            Log.i(TAG, "onFailure: ")
        }
    }

    fun getLocationDistance(
        lastKnownLat: Double,
        lastKnownLon: Double,
        currentLat: Double,
        currentLon: Double
    ): Double {
        val startPoint = Location("locationA")
        startPoint.latitude = lastKnownLat
        startPoint.longitude = lastKnownLon
        val endPoint = Location("locationB")
        endPoint.latitude = currentLat
        endPoint.longitude = currentLon
        return startPoint.distanceTo(endPoint).toDouble()
    }

    fun handleLocation (location: Location?) {
        if (location == null) {
            return
        }
        val simple: DateFormat =
            SimpleDateFormat(Constants.SIMPLE_DATE_TIME_FORMATTER_VERBOSE)
        val dt = Date(location.time)
        val str = "getAccuracy: " + location.accuracy +
                " getProvider: " + location.provider +  //                " getAltitude: " + location.getAltitude() +
                //                " getBearing: " + location.getBearing() +
                " getLatitude: " + location.latitude +
                " getLongitude: " + location.longitude +  //                " getElapsedRealtimeNanos: " + location.getElapsedRealtimeNanos() +
                //                " getExtras: " + location.getExtras() +
                //                " getSpeed: " + location.getSpeed() +
                //                " getElapsedRealtimeNanos: " + location.getElapsedRealtimeNanos() +
                " getTime: " + simple.format(dt)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            str += " getBearingAccuracyDegrees: " + location.getBearingAccuracyDegrees() +
//                   " getSpeedAccuracyMetersPerSecond: " + location.getSpeedAccuracyMetersPerSecond() +
//                   " getVerticalAccuracyMeters: " + location.getVerticalAccuracyMeters();
        }
        Log.i(TAG, str)
    }
}