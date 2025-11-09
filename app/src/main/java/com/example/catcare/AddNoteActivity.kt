package com.example.catcare

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class AddNoteActivity : ComponentActivity() {

    private lateinit var noteType: Spinner
    private lateinit var btnSaveNote: Button
    private lateinit var btnCancel: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        noteType = findViewById(R.id.noteType)
        btnSaveNote = findViewById(R.id.btnSaveNote)
        btnCancel = findViewById(R.id.btnCancel)

        val adapter = object : ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            resources.getStringArray(R.array.note_type)
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(Color.GRAY)
                } else {
                    tv.setTextColor(Color.BLACK)
                }
                return view
            }
        }

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        noteType.adapter = adapter

        // ตั้งค่า default ให้เลือก placeholder
        noteType.setSelection(0)

        noteType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    val selected = parent.getItemAtPosition(position).toString()
                    Toast.makeText(this@AddNoteActivity, "คุณเลือก: $selected", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnSaveNote.setOnClickListener {
            finish()
        }
        btnCancel.setOnClickListener {
            finish()
        }
    }
}
