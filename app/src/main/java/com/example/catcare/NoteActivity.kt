package com.example.catcare

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity

class NoteActivity: ComponentActivity() {

    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }
    }
}
