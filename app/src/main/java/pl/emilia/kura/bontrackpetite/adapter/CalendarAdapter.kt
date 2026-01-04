package pl.emilia.kura.bontrackpetite.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.emilia.kura.bontrackpetite.R

class CalendarAdapter(
    private val daysOfMonth: ArrayList<String>,
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
        holder.tvDay.text=day

        holder.itemView.setOnClickListener {
            if(day.isNotEmpty())
                onItemListener(day)
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }
}