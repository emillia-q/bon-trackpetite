package pl.emilia.kura.bontrackpetite.adapter

import android.graphics.Color
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.emilia.kura.bontrackpetite.R
import pl.emilia.kura.bontrackpetite.model.CalendarDay

class CalendarAdapter(
    private val daysOfMonth: ArrayList<CalendarDay>,
    private val onItemListener:(day:String)->Unit)
    : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(){
    class CalendarViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val tvDay:TextView=itemView.findViewById<TextView>(R.id.tvDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell,parent,false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val day=daysOfMonth[position]
        holder.tvDay.text=day.date.dayOfMonth.toString()

        //If it is not current month the cell will be gray
        if(!day.isCurrentMonth){
            holder.tvDay.setTextColor(Color.GRAY)
            holder.itemView.alpha=0.3f
        }
        holder.itemView.setOnClickListener {
            onItemListener(day.date.toString())
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }
}