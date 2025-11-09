package com.example.catcare

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat


class MainActivity : ComponentActivity() {
    private lateinit var btnTimer: Button
    private lateinit var btnVac: Button
    private lateinit var btnNote: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestNotificationPermissionIfNeeded()

        btnTimer = findViewById(R.id.btnTimer)
        btnVac = findViewById(R.id.btnVac)
        btnNote = findViewById(R.id.btnNote)

        btnTimer.setOnClickListener {
            startActivity(android.content.Intent(this, TimerActivity::class.java))
        }

        btnVac.setOnClickListener {
            startActivity(android.content.Intent(this, VaccineActivity::class.java))
        }

        btnNote.setOnClickListener {
            startActivity(android.content.Intent(this, NoteActivity::class.java))
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }
}
