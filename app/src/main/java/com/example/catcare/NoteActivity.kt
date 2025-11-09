package com.example.catcare

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity

class NoteActivity: ComponentActivity() {

    private lateinit var btnBack: ImageButton

    private lateinit var btnAddNote: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        btnBack = findViewById(R.id.btnBack)
        btnAddNote = findViewById(R.id.btnAddNote)

        btnBack.setOnClickListener {
            finish()
        }

        btnAddNote.setOnClickListener {
            startActivity(android.content.Intent(this, AddNoteActivity::class.java))        }
    }
}
