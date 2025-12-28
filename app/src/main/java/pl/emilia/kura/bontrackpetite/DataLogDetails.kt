package pl.emilia.kura.bontrackpetite

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DataLogDetails : AppCompatActivity() {
    lateinit var btnBack: Button
    lateinit var passedDate:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_log_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnBack=findViewById<Button>(R.id.btnBack)
        passedDate=findViewById<TextView>(R.id.passedDate)

        val date=intent.getStringExtra("CHOSEN_DATE")

        passedDate.text=date

        btnBack.setOnClickListener {
            finish()
        }
    }
}