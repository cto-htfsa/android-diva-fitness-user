package com.htf.diva.dashboard.homeWorkoutPlan

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.models.WorkoutWeekDaysModel
import kotlinx.android.synthetic.main.row_workout_weekdays.view.*

class WorkoutWeekDaysAdapter(
        private var currActivity: Activity,
        private var arrWorkoutWeekDays: ArrayList<WorkoutWeekDaysModel>,
        private var iListItemClickListener: IListItemClickListener<Any>,
       private var comeFrom: String?): RecyclerView.Adapter<WorkoutWeekDaysAdapter.MyViewHolder>(){


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {

            itemView.llDayRest.setOnClickListener {
                val model = arrWorkoutWeekDays[adapterPosition]
                when (currActivity) {
                    is CreateWorkoutPlanActivity -> {
                        (currActivity as CreateWorkoutPlanActivity).selectDayRest(model, adapterPosition)
                    }
                }
            }

            itemView.llOnRest.setOnClickListener {
                val model = arrWorkoutWeekDays[adapterPosition]
                when (currActivity) {
                    is CreateWorkoutPlanActivity -> {
                        (currActivity as CreateWorkoutPlanActivity).selectOnDayRest(model, adapterPosition)
                    }
                }
            }

            itemView.llWorkoutAddWeekDay.setOnClickListener{
                WorkoutDayActivity.open(currActivity,arrWorkoutWeekDays[adapterPosition],comeFrom)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutWeekDaysAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
                R.layout.row_workout_weekdays, parent, false)
        return MyViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: WorkoutWeekDaysAdapter.MyViewHolder, position: Int) {
        val model=arrWorkoutWeekDays[position]

        if (comeFrom=="comeFromNoWorkout"){
            if (model.isDayRest==0){
                holder.itemView.llDayRest.visibility=View.VISIBLE
                holder.itemView.llOnRest.visibility=View.GONE
            }else{
                holder.itemView.llDayRest.visibility=View.GONE
                holder.itemView.llOnRest.visibility=View.VISIBLE
            }
        }else{
            holder.itemView.llWorkoutAddWeekDay.visibility=View.VISIBLE
        }


        holder.itemView.tvWeekDayName.text=model.name
    }


    override fun getItemCount(): Int {
        return arrWorkoutWeekDays.size
    }

}







