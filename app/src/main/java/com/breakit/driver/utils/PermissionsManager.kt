package com.breakit.driver.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.util.*

object PermissionsManager {
    fun listPermissionsWhichAreRequired(
        activity: Activity
    ): MutableList<String> {

        val internetPermission =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET)
        val coarseLocationPermission =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
        val fineLocationPermission =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        val foreGroundService =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.FOREGROUND_SERVICE)

        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (internetPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET)
        }
        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (foreGroundService != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.FOREGROUND_SERVICE)
        }

        return listPermissionsNeeded
    }

    fun isAllPermissionsGranted(activity: Activity): Boolean {
        return listPermissionsWhichAreRequired(activity).size == 0
    }


}