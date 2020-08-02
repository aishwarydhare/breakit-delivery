package com.breakit.delivery.di.modules

import android.app.Application
import android.content.ContentValues
import android.location.Location
import android.util.Log
import com.breakit.delivery.utils.GpsUtils
import com.google.android.gms.location.*
import dagger.Module
import dagger.Provides
import fr.quentinklein.slt.LocationTracker
import fr.quentinklein.slt.TrackerSettings
import javax.inject.Singleton

private const val TAG = "LOCATION"

@Module
class GpsModule {
    @Provides
    @Singleton
    fun provideLocationTracker(application: Application?): LocationTracker {
        return object : LocationTracker(
            application!!,
            TrackerSettings()
                .setUseGPS(true)
                .setUseNetwork(true)
                .setUsePassive(true)
                .setTimeBetweenUpdates(1000)
                .setMetersBetweenUpdates(1f)
        ) {
            override fun onLocationFound(location: Location) {
                GpsUtils.handleLocation(location)
            }

            override fun onTimeout() {
                Log.e(TAG, "onTimeout: ")
            }
        }
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }


    @Provides
    @Singleton
    fun provideLocationCallback(): LocationCallback {
        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                Log.i(ContentValues.TAG + " GoogleGeo", "onLocationResult: ")
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    GpsUtils.handleLocation(location)
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.smallestDisplacement = 1f
        return locationRequest
    }
}