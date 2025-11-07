package com.example.catcare

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.catcare.alarm.AlarmHelper
import com.example.catcare.util.Prefs
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class VaccineActivity : ComponentActivity() {

    private lateinit var btnPickDate: Button
    private lateinit var btnSave: Button
    private lateinit var tvChosen: TextView
    private lateinit var tvCountdown: TextView

    private var chosenTimeMillis: Long = 0L
    private val fmt = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccine)

        btnPickDate = findViewById(R.id.btnPickDate)
        btnSave = findViewById(R.id.btnSaveDate)
        tvChosen = findViewById(R.id.tvChosenDate)
        tvCountdown = findViewById(R.id.tvCountdown)

        // โหลดวันเก่า (ถ้ามี)
        val saved = Prefs.getNextVaccineTime(this)
        if (saved > 0) {
            chosenTimeMillis = saved
            showDateAndCountdown(saved)
        }

        btnPickDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dlg = DatePickerDialog(
                this,
                { _, y, m, d ->
                    cal.set(y, m, d, 9, 0, 0) // เตือน 9:00 เช้า
                    cal.set(Calendar.MILLISECOND, 0)
                    chosenTimeMillis = cal.timeInMillis
                    showDateAndCountdown(chosenTimeMillis)
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            dlg.show()
        }

        btnSave.setOnClickListener {
            if (chosenTimeMillis <= 0L) return@setOnClickListener
            Prefs.setNextVaccineTime(this, chosenTimeMillis)
            AlarmHelper.cancelVaccine(this)
            AlarmHelper.scheduleVaccine(this, chosenTimeMillis)
            showDateAndCountdown(chosenTimeMillis)
        }
    }

    private fun showDateAndCountdown(time: Long) {
        tvChosen.text = "กำหนดครั้งถัดไป: ${fmt.format(time)}"
        val now = System.currentTimeMillis()
        val diff = time - now
        if (diff <= 0) {
            tvCountdown.text = "ถึงกำหนดแล้ว"
        } else {
            val days = TimeUnit.MILLISECONDS.toDays(diff)
            tvCountdown.text = "เหลืออีก $days วัน"
        }
    }
}
