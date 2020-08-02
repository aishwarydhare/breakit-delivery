package com.breakit.delivery.notifications

import android.app.Notification
import android.content.Context
import android.content.Intent
import com.breakit.delivery.Constants
import com.breakit.delivery.MainActivity
import com.breakit.delivery.R
import com.breakit.delivery.utils.NotificationUtils.createNotificationChannel
import com.breakit.delivery.utils.NotificationUtils.getNotificationBuilder

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
