package pl.emilia.kura.bontrackpetite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import pl.emilia.kura.bontrackpetite.adapter.CalendarAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    //Custom calendar view
    private lateinit var calendarRecyclerView: RecyclerView
    private var selectedDate:java.time.LocalDate=java.time.LocalDate.now()
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var tvMonthYear: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Calendar initialization
        calendarRecyclerView=findViewById<RecyclerView>(R.id.rvCalendar)
        calendarRecyclerView.layoutManager=androidx.recyclerview.widget.GridLayoutManager(applicationContext,7)

        setMonthView()

        //Buttons initialization
        btnPrev=findViewById<Button>(R.id.btnPrevMonth)
        btnNext=findViewById<Button>(R.id.btnNextMonth)
        tvMonthYear=findViewById<TextView>(R.id.tvMonthYear)

        btnPrev.setOnClickListener {
            selectedDate=selectedDate.minusMonths(1)
            setMonthView()
        }

        btnNext.setOnClickListener {
            selectedDate=selectedDate.plusMonths(1)
            setMonthView()
        }
    }

    private fun setMonthView(){
        val tvMonthYear=findViewById<TextView>(R.id.tvMonthYear)
        val formatter=DateTimeFormatter.ofPattern("MMM yyyy", java.util.Locale.getDefault())
        tvMonthYear.text=selectedDate.format(formatter)
        val daysInMonth=daysInMonthArray(selectedDate)
        val calendarAdapter= CalendarAdapter(daysInMonth){day->
            val formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dateToPass = selectedDate.withDayOfMonth(day.toInt())
            val formattedDate = dateToPass.format(formatter)

            val intent= Intent(this, DataLogDetails::class.java).apply {
                putExtra("CHOSEN_DATE",formattedDate)
            }
            startActivity(intent)
        }
        calendarRecyclerView.adapter=calendarAdapter
    }

    private fun daysInMonthArray(date: java.time.LocalDate): ArrayList<String>{
        val daysInMonthArray= ArrayList<String>()
        val yearMonth=java.time.YearMonth.from(date)
        val daysInMonth=yearMonth.lengthOfMonth()

        val firstOfMonth=selectedDate.withDayOfMonth(1)
        val dayOfWeek=firstOfMonth.dayOfWeek.value //1-MON, 7-SUN

        for(i in 1..35){
            if(i<dayOfWeek||i>=daysInMonth+dayOfWeek)
                daysInMonthArray.add("")
            else
                daysInMonthArray.add((i-dayOfWeek+1).toString())
        }
        return daysInMonthArray
    }
}