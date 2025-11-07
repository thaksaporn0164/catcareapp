package com.example.catcare

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.catcare.util.Prefs
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CatInfoActivity : ComponentActivity() {

    private lateinit var etName: EditText
    private lateinit var etBreed: EditText
    private lateinit var btnBirth: Button
    private lateinit var btnSave: Button
    private lateinit var tvSummary: TextView

    private var birthMillis: Long = 0L
    private val fmt = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_info)

        etName = findViewById(R.id.etName)
        etBreed = findViewById(R.id.etBreed)
        btnBirth = findViewById(R.id.btnBirth)
        btnSave = findViewById(R.id.btnSaveCat)
        tvSummary = findViewById(R.id.tvSummary)

        // โหลดค่าที่เคยบันทึก
        etName.setText(Prefs.getCatName(this))
        etBreed.setText(Prefs.getCatBreed(this))
        val savedBirth = Prefs.getCatBirth(this)
        if (savedBirth > 0) {
            birthMillis = savedBirth
            btnBirth.text = "วันเกิด: ${fmt.format(savedBirth)}"
        }

        btnBirth.setOnClickListener {
            val cal = Calendar.getInstance()
            val dlg = DatePickerDialog(this, { _, y, m, d ->
                cal.set(y, m, d, 0, 0, 0)
                cal.set(Calendar.MILLISECOND, 0)
                birthMillis = cal.timeInMillis
                btnBirth.text = "วันเกิด: ${fmt.format(birthMillis)}"
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
            dlg.show()
        }

        btnSave.setOnClickListener {
            Prefs.setCatInfo(this,
                name = etName.text.toString(),
                breed = etBreed.text.toString(),
                birth = birthMillis
            )
            tvSummary.text = "บันทึกแล้ว ✔️"
        }
    }
}
