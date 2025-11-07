package com.example.catcare.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.catcare.util.Prefs

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // รีตั้งให้อาหารถ้ามีช่วงเวลาเก็บไว้
        if (Prefs.getFeedIntervalMs(context) > 0) {
            AlarmHelper.scheduleNextFeeding(context)
        }
        // รีตั้งวัคซีนถ้าวันนัดยังอยู่ในอนาคต
        val next = Prefs.getNextVaccineTime(context)
        if (next > System.currentTimeMillis()) {
            AlarmHelper.scheduleVaccine(context, next)
        }
    }
}
