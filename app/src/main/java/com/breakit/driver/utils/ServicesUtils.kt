package com.breakit.driver.utils

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import com.breakit.driver.Constants

private const val TAG = "SERVICE"

object ServicesUtils {
    private fun isServiceRunning(
        ctx: Context,
        serviceClass: Class<*>
    ): Boolean {
        val manager =
            ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.debug(TAG, serviceClass.canonicalName + ": true")
                return true
            }
        }
        Log.debug(TAG, serviceClass.canonicalName + ": false")
        return false
    }

    fun startBackgroundService(ctx: Application) {
        var intent: Intent
        for (item in Constants.SERVICES_TO_START.indices) {
            if (isServiceRunning(ctx, Constants.SERVICES_TO_START[item])) {
                continue
            }
            intent = Intent(ctx, Constants.SERVICES_TO_START[item])
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ctx.startForegroundService(intent)
            } else {
                ctx.startService(intent)
            }
        }
    }

    fun stopBackgroundServices(ctx: Context) {
        var intent: Intent
        for (item in Constants.SERVICES_TO_START.indices) {
            intent = Intent(ctx, Constants.SERVICES_TO_START[item])
            if (isServiceRunning(ctx, Constants.SERVICES_TO_START[item])) {
                ctx.stopService(intent)
            }
        }
    }
}