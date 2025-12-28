package pl.emilia.kura.bontrackpetite

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var calendarView: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        calendarView=findViewById<CalendarView>(R.id.calendarView)

        calendarView.setOnDateChangeListener (
            OnDateChangeListener { view, year, month, dayOfMonth ->
                val date="$dayOfMonth-${month+1}-$year"
                val intent= Intent(this, DataLogDetails::class.java).apply {
                    putExtra("CHOSEN_DATE",date)
                }
                startActivity(intent)
            }
        )
    }
}