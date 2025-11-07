package com.example.catcare.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationUtils {
    const val CHANNEL_FEED = "feed_channel"
    const val CHANNEL_VACCINE = "vaccine_channel"

    fun ensureChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val feed = NotificationChannel(
                CHANNEL_FEED, "Feeding Reminders", NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "แจ้งเตือนให้อาหารแมวตามช่วงเวลาที่กำหนด" }
            val vaccine = NotificationChannel(
                CHANNEL_VACCINE, "Vaccine Reminders", NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "แจ้งเตือนพาแมวไปฉีดวัคซีน" }

            nm.createNotificationChannel(feed)
            nm.createNotificationChannel(vaccine)
        }
    }
}
