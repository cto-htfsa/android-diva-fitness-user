package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.models.DietWeekdayModel
import kotlinx.android.synthetic.main.row_diet_weekdays.view.*

class DietWeekDaysAdapter(
    private var currActivity: Activity,
    private var arrDietWeekDays:ArrayList<DietWeekdayModel>,
    private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<DietWeekDaysAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {

            itemView.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrDietWeekDays[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietWeekDaysAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_diet_weekdays
            ,parent,false)
        return MyViewHolder(itemView)


    }

    override fun getItemCount(): Int {
        return arrDietWeekDays.size
    }

    override fun onBindViewHolder(holder: DietWeekDaysAdapter.MyViewHolder, position: Int) {
        val model=arrDietWeekDays[position]
        holder.itemView.tvDietWeekday_name.text=model.name

    }

}