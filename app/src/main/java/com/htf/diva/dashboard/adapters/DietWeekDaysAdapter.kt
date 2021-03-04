package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowDietWeekdaysBinding
import com.htf.diva.databinding.RowPersonalTrainersBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.DietWeekdayModel
import com.htf.eyenakhr.callBack.IListItemClickListener

class DietWeekDaysAdapter(
    private var currActivity: Activity,
    private var arrDietWeekDays:ArrayList<DietWeekdayModel>,
    private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<DietWeekDaysAdapter.MyViewHolder>(){

    var rowDietWeekDayBinding: RowDietWeekdaysBinding?=null

    inner class MyViewHolder(itemView: RowDietWeekdaysBinding): RecyclerView.ViewHolder(itemView.root){
        init {

            rowDietWeekDayBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrDietWeekDays[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietWeekDaysAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_diet_weekdays
            ,parent,false)
        val bindingUtil= RowDietWeekdaysBinding.bind(itemView);
        return MyViewHolder(bindingUtil)


    }

    override fun getItemCount(): Int {
        return arrDietWeekDays.size
    }

    override fun onBindViewHolder(holder: DietWeekDaysAdapter.MyViewHolder, position: Int) {
        val model=arrDietWeekDays[position]
        rowDietWeekDayBinding!!.dietWeekDays =model
    }

}