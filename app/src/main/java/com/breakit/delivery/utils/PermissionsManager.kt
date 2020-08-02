package com.breakit.delivery.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.breakit.delivery.Constants
import java.util.*

object PermissionsManager {
    private fun listRequiredPermissions(
        activity: Activity
    ): MutableList<String> {
        val listPermissionsNeeded: MutableList<String> = ArrayList()

        for (item in Constants.PERMISSIONS_REQUIRED) {
            val selfPermission = ContextCompat.checkSelfPermission(activity, item.toString())
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.INTERNET)
            }
        }
        return listPermissionsNeeded
    }

    fun arePermissionsGranted(activity: Activity): Boolean {
        return listRequiredPermissions(activity).size == 0
    }
}