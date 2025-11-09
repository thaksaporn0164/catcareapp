package com.example.catcare

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.catcare.alarm.AlarmHelper
import com.example.catcare.util.Prefs
import com.jakewharton.threetenabp.AndroidThreeTen
import java.text.SimpleDateFormat
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class VaccineActivity : ComponentActivity() {

    private lateinit var btnPickDate: Button
    private lateinit var btnSave: Button
    private lateinit var tvChosen: TextView
    private lateinit var tvCountdown: TextView
    private lateinit var btnBack: ImageButton

    private var chosenTimeMillis: Long = 0L
    private val fmt = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccine)
        AndroidThreeTen.init(this)

        btnPickDate = findViewById(R.id.btnPickDate)
        btnSave = findViewById(R.id.btnSaveDate)
        tvChosen = findViewById(R.id.tvChosenDate)
        tvCountdown = findViewById(R.id.tvCountdown)
        btnBack = findViewById(R.id.btnBack)


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
            // จำกัดให้เลือกวันพรุ่งนี้เป็นต้นไป
            val tomorrow = Calendar.getInstance()
            tomorrow.add(Calendar.DAY_OF_MONTH, 1)
            dlg.datePicker.minDate = tomorrow.timeInMillis

            dlg.show()
        }

        btnSave.setOnClickListener {
            if (chosenTimeMillis <= 0L) return@setOnClickListener
            Prefs.setNextVaccineTime(this, chosenTimeMillis)
            AlarmHelper.cancelVaccine(this)
            AlarmHelper.scheduleVaccine(this, chosenTimeMillis)
            showDateAndCountdown(chosenTimeMillis)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showDateAndCountdown(time: Long) {
        tvChosen.text = "กำหนดครั้งถัดไป: ${fmt.format(time)}"

        val nowDate = LocalDate.now()
        val chosenDate = Instant.ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val days = ChronoUnit.DAYS.between(nowDate, chosenDate)

        tvCountdown.text = if (days <= 0) "ถึงกำหนดแล้ว" else "เหลืออีก $days วัน"
    }

}
