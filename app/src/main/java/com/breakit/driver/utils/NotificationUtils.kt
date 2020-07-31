package com.breakit.driver.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.breakit.driver.Constants

object NotificationUtils {
    fun showNotification(
        context: Context,
        notificationId: Int,
        notification: Notification?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isNotificationVisible(notificationId, context)) {
                return
            }
        }
        val notificationManagerCompat =
            NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(notificationId, notification!!)
    }

    fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence =
                Constants.CRITICAL_NOTIFICATION_CHANNEL_NAME
            val description =
                "Notifications for critical services of repos driver app"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                Constants.CRITICAL_NOTIFICATION_CHANNEL_NAME,
                name,
                importance
            )
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager =
                context.getSystemService(
                    NotificationManager::class.java
                )
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun getNotificationBuilder(context: Context): Notification.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context)
            Notification.Builder(
                context,
                Constants.CRITICAL_NOTIFICATION_CHANNEL_NAME
            )
        } else {
            Notification.Builder(context)
        }
    }

    fun clearNotification(context: Context?, notificationId: Int) {
        if (context != null) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notificationId)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun isNotificationVisible(
        notificationId: Int,
        context: Context
    ): Boolean {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifications =
            notificationManager.activeNotifications
        for (notification in notifications) {
            if (notification.id == notificationId) {
                return true
            }
        }
        return false
    }
}