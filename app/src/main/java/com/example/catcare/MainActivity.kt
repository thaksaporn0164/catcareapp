package com.example.catcare

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.catcare.alarm.AlarmHelper
import com.example.catcare.util.Prefs

class MainActivity : ComponentActivity() {

    private lateinit var hoursPicker: NumberPicker
    private lateinit var minutesPicker: NumberPicker
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnOpenVaccine: Button
    private lateinit var btnOpenCatInfo: Button
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hoursPicker = findViewById(R.id.hoursPicker)
        minutesPicker = findViewById(R.id.minutesPicker)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnOpenVaccine = findViewById(R.id.btnOpenVaccine)
        btnOpenCatInfo = findViewById(R.id.btnOpenCatInfo)
        tvStatus = findViewById(R.id.tvStatus)

        hoursPicker.minValue = 0
        hoursPicker.maxValue = 24
        minutesPicker.minValue = 0
        minutesPicker.maxValue = 59

        // แสดงค่าที่ตั้งไว้ล่าสุด (ถ้ามี)
        val ms = Prefs.getFeedIntervalMs(this)
        if (ms > 0) {
            val h = (ms / 3600000L).toInt()
            val m = ((ms % 3600000L) / 60000L).toInt()
            hoursPicker.value = h
            minutesPicker.value = m
            tvStatus.text = "กำลังแจ้งเตือนทุก ๆ ${h} ชม. ${m} นาที"
        }

        btnStart.setOnClickListener {
            val h = hoursPicker.value
            val m = minutesPicker.value
            require(h > 0 || m > 0) { "ต้องตั้งเวลาอย่างน้อย 1 นาที" }
            Prefs.setFeedInterval(this, h, m)
            AlarmHelper.scheduleNextFeeding(this)
            tvStatus.text = "เริ่มแจ้งเตือนทุก ๆ ${h} ชม. ${m} นาที"
        }

        btnStop.setOnClickListener {
            AlarmHelper.cancelFeeding(this)
            Prefs.setFeedInterval(this, 0, 0)
            tvStatus.text = "หยุดการแจ้งเตือนให้อาหาร"
        }

        btnOpenVaccine.setOnClickListener {
            startActivity(android.content.Intent(this, VaccineActivity::class.java))
        }

        btnOpenCatInfo.setOnClickListener {
            startActivity(android.content.Intent(this, CatInfoActivity::class.java))
        }
    }
}
