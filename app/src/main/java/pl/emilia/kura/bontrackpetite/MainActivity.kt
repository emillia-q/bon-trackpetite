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
import pl.emilia.kura.bontrackpetite.model.CalendarDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    //Custom calendar view
    private lateinit var calendarRecyclerView: RecyclerView
    private var selectedDate:java.time.LocalDate=java.time.LocalDate.now()
    private lateinit var btnPrev: TextView
    private lateinit var btnNext: TextView
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
        btnPrev=findViewById<TextView>(R.id.btnPrevMonth)
        btnNext=findViewById<TextView>(R.id.btnNextMonth)
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
            val formattedDate = day.format(formatter)

            val intent= Intent(this, DataLogDetails::class.java).apply {
                putExtra("CHOSEN_DATE",formattedDate)
            }
            startActivity(intent)
        }
        calendarRecyclerView.adapter=calendarAdapter
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<CalendarDay>{
        val daysInMonthArray= ArrayList<CalendarDay>()
        val yearMonth=java.time.YearMonth.from(date)
        val daysInMonth=yearMonth.lengthOfMonth()

        val firstOfMonth=selectedDate.withDayOfMonth(1)
        val dayOfWeek=firstOfMonth.dayOfWeek.value //1-MON, 7-SUN

        val prevMonth=selectedDate.minusMonths(1)
        val daysInPrevMonth=prevMonth.lengthOfMonth()
        val nextMonth=selectedDate.plusMonths(1)

        var longerLayout: Boolean
        if(35>=daysInMonth+dayOfWeek-1)
            longerLayout=false
        else
            longerLayout=true

        for(i in 1..42){
            if(i<dayOfWeek) { //Previous month
                val dayNum=daysInPrevMonth-dayOfWeek+i+1
                val prevMonthDate=prevMonth.withDayOfMonth(dayNum)
                daysInMonthArray.add(CalendarDay(prevMonthDate,false))
            }else if(i>=daysInMonth+dayOfWeek){ //Next month
                val dayNum=i-daysInMonth-dayOfWeek+1
                val nextMonthDate=nextMonth.withDayOfMonth(dayNum)
                daysInMonthArray.add(CalendarDay(nextMonthDate,false))
            }else { //Current month
                val dayNum=i-dayOfWeek+1
                val currentMonthDate=date.withDayOfMonth(dayNum)
                daysInMonthArray.add(CalendarDay(currentMonthDate,true))
            }
            if(i==35 && !longerLayout)
                break
        }
        return daysInMonthArray
    }
}