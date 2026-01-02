package pl.emilia.kura.bontrackpetite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import pl.emilia.kura.bontrackpetite.R
import pl.emilia.kura.bontrackpetite.model.WeightData

class RowAdapter(private val dataSet: List<WeightData>): RecyclerView.Adapter<RowAdapter.RowViewHolder>() {
    //A container that holds references to the views for each row
    class RowViewHolder(view:View): RecyclerView.ViewHolder(view){
        val tvTime=view.findViewById<TextView>(R.id.tvTime)
        val tvWeight=view.findViewById<TextView>(R.id.tvWeight)
    }

    //Called when RecyclerView needs a new row visual structure
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RowViewHolder {
        //Convert xml into a real View object
        val view= LayoutInflater.from(parent.context).inflate(R.layout.row_record_layout,parent,false)
        //Wrap the view inside viewHolder
        return RowViewHolder(view)
    }

    //Called to display data at a specific position
    override fun onBindViewHolder(
        holder: RowViewHolder,
        position: Int
    ) {
        val item=dataSet[position]
        holder.tvTime.text=item.time
        holder.tvWeight.text="${item.weight}g"
    }

    //Tells the RecyclerView how many items are in data set
    override fun getItemCount(): Int {
        return dataSet.size
    }
}