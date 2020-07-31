package com.breakit.driver.notifications

import android.app.Notification
import android.content.Context
import android.content.Intent
import com.breakit.driver.Constants
import com.breakit.driver.MainActivity
import com.breakit.driver.R
import com.breakit.driver.utils.NotificationUtils.createNotificationChannel
import com.breakit.driver.utils.NotificationUtils.getNotificationBuilder

object ForegroundServiceNotificationCreator {
    private var notification: Notification? = null
    fun getNotification(context: Context?): Notification? {
        if (notification == null) {
            val notificationIntent = Intent(context, MainActivity::class.java)
            notificationIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            createNotificationChannel(context!!)
            notification =
                getNotificationBuilder(context)
                    .setContentTitle("BreakIt Driver App")
                    .setContentText("")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setTicker("")
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .build()
        }
        return notification
    }

    val notificationId: Int
        get() = Constants.ONGOING_SERVICE_NOTIFICATION_ID
}
