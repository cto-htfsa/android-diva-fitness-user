package com.htf.diva.dashboard.homeWorkout

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.databinding.RowWorkoutWeekdaysBinding
import com.htf.diva.models.WorkoutWeekDaysModel
import kotlinx.android.synthetic.main.row_workout_weekdays.view.*

class WorkoutWeekDaysAdapter (
        private var currActivity: Activity,
        private var arrWorkoutWeekDays:ArrayList<WorkoutWeekDaysModel>,
        private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<WorkoutWeekDaysAdapter.MyViewHolder>(){

    var rowWorkoutWeekDayBinding: RowWorkoutWeekdaysBinding?=null

    inner class MyViewHolder(itemView: RowWorkoutWeekdaysBinding): RecyclerView.ViewHolder(itemView.root){
        init {

            rowWorkoutWeekDayBinding=itemView
          /*  itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrWorkoutWeekDays)
            }*/

            itemView.root.llWorkoutAddWeekDay.setOnClickListener{
                WorkoutDayActivity.open(currActivity,arrWorkoutWeekDays[adapterPosition])
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutWeekDaysAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
                R.layout.row_workout_weekdays
                ,parent,false)
        val bindingUtil= RowWorkoutWeekdaysBinding.bind(itemView);
        return MyViewHolder(bindingUtil)


    }

    override fun getItemCount(): Int {
        return arrWorkoutWeekDays.size
    }

    override fun onBindViewHolder(holder: WorkoutWeekDaysAdapter.MyViewHolder, position: Int) {
        val model=arrWorkoutWeekDays[position]
        rowWorkoutWeekDayBinding!!.workoutWeekDays =model
    }

}