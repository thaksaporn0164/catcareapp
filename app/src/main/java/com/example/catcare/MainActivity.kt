package com.example.catcare

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.catcare.alarm.AlarmHelper
import com.example.catcare.util.Prefs

class MainActivity : ComponentActivity() {

    private lateinit var btnOpenCatInfo: Button
    private lateinit var btnTimer: Button
    private lateinit var btnVac:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTimer = findViewById(R.id.btnTimer)
        btnVac = findViewById(R.id.btnVac)

        btnTimer.setOnClickListener {
            startActivity(android.content.Intent(this, TimerActivity::class.java))
        }

        btnVac.setOnClickListener {
            startActivity(android.content.Intent(this, VaccineActivity::class.java))
        }



//        btnOpenVaccine.setOnClickListener {
//            startActivity(android.content.Intent(this, VaccineActivity::class.java))
//        }
//
//        btnOpenCatInfo.setOnClickListener {
//            startActivity(android.content.Intent(this, CatInfoActivity::class.java))
//        }
    }
}
