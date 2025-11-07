package com.example.catcare.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.catcare.R
import com.example.catcare.VaccineActivity
import com.example.catcare.util.NotificationUtils
import com.example.catcare.util.Prefs

class VaccineReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        NotificationUtils.ensureChannels(context)

        val pi = PendingIntent.getActivity(
            context, 2001, Intent(context, VaccineActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "ถึงกำหนดวัคซีนแมวแล้ว"
        val content = "เปิดดูรายละเอียดและบันทึกวันถัดไป"

        val notif = NotificationCompat.Builder(context, NotificationUtils.CHANNEL_VACCINE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(2001, notif)

        // ไม่ตั้งซ้ำอัตโนมัติ ให้ผู้ใช้บันทึกวันใหม่ใน VaccineActivity
        Prefs.setNextVaccineTime(context, 0L)
    }
}
