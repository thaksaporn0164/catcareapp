package com.example.catcare.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.catcare.MainActivity
import com.example.catcare.R
import com.example.catcare.util.NotificationUtils
import com.example.catcare.util.Prefs

class FeedingAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        NotificationUtils.ensureChannels(context)

        val openIntent = Intent(context, MainActivity::class.java)
        val pi = PendingIntent.getActivity(
            context, 1001, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notif = NotificationCompat.Builder(context, NotificationUtils.CHANNEL_FEED)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("ถึงเวลาให้อาหารแมวแล้ว")
            .setContentText("กดเพื่อเปิดแอป")
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(1001, notif)

        // ตั้งครั้งถัดไปตามช่วงเวลา (reschedule แบบ exact)
        AlarmHelper.scheduleNextFeeding(context)
    }
}
