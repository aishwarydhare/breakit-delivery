package com.breakit.driver.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.breakit.driver.notifications.ForegroundServiceNotificationCreator
import com.breakit.driver.utils.GpsUtils
import com.breakit.driver.utils.GpsUtils.getGoogleLocationLastKnown
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import fr.quentinklein.slt.LocationTracker
import fr.quentinklein.slt.TrackerSettings

private const val TAG = "LOCATION"

class LocationService: Service(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    private var callback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null
    private var tracker: LocationTracker? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var isLocationTrackingActive = false
    private var shouldRestart = true

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
        callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                Log.i(TAG, "onLocationResult: ")
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    GpsUtils.handleLocation(location)
                }
            }
        }
        locationRequest = LocationRequest.create()
        locationRequest!!.interval = 1000
        locationRequest!!.fastestInterval = 1000
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.smallestDisplacement = 1f



        Log.i(
            TAG,
            "onStartCommand: location service start, $IS_SERVICE_RUNNING, $shouldRestart"
        )
        if (IS_SERVICE_RUNNING) {
            stopSelf()
            return START_NOT_STICKY
        }

        if (!GpsUtils.isGpsEnabled(this)) {
            stopSelf()
            return START_NOT_STICKY
        }

        IS_SERVICE_RUNNING = true

        // check if permissions are granted for location and also check if gps is active

        // start location tracking using google fused client
        startGoogleLocationTracking(fusedLocationClient)

        // get last known location from google fused client
        fusedLocationClient?.let { getGoogleLocationLastKnown(it) }

        // start location tracking using native client
        tracker!!.startListening()
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(
            ForegroundServiceNotificationCreator.notificationId,
            ForegroundServiceNotificationCreator.getNotification(this)
        )
    }

    private fun stopGoogleLocationTracking() {
        Log.i(TAG, "stopGoogleLocationTracking: ")
        isLocationTrackingActive = false
        fusedLocationClient?.removeLocationUpdates(callback)
    }

    private fun terminateGoogleLocationTracking() {
        Log.i(TAG, "terminateGoogleLocationTracking: ")
        isLocationTrackingActive = false
    }

    @SuppressLint("MissingPermission")
    private fun startGoogleLocationTracking(fusedClientParam: FusedLocationProviderClient?) {
        if (isLocationTrackingActive) {
            return
        }
        isLocationTrackingActive = true
        fusedClientParam!!.requestLocationUpdates(locationRequest, callback, null)
        Log.i(TAG, "startGoogleLocationTracking: done")
    }

    /*
    GoogleApiClient.ConnectionCallbacks
     */
    override fun onConnected(bundle: Bundle?) {
        Log.i(TAG, "onConnected: ")
    }

    override fun onConnectionSuspended(i: Int) {
        Log.i(TAG, "onConnectionSuspended: ")
    }

    /*
    GoogleApiClient.onConnectionSuspended
     */
    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(TAG, "onConnectionFailed: ")
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: service  exit")
        stopGoogleLocationTracking()
        terminateGoogleLocationTracking()
        tracker?.stopListening()
        IS_SERVICE_RUNNING = false
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private var IS_SERVICE_RUNNING = false
    }
}