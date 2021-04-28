package com.htf.diva.dashboard.homeWorkout

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.databinding.RowMyWorkoutBinding
import com.htf.diva.databinding.RowWorkoutWeekdaysBinding
import com.htf.diva.models.UserWorkout
import com.htf.diva.models.WorkoutWeekDaysModel
import kotlinx.android.synthetic.main.row_workout_weekdays.view.*

class MyWorkoutAdapter (
    private var currActivity: Activity,
    private var arrWorkoutWeekDays:ArrayList<UserWorkout>,
    private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<MyWorkoutAdapter.MyViewHolder>(){

    var rowWorkoutWeekDayBinding: RowMyWorkoutBinding?=null

    inner class MyViewHolder(itemView: RowMyWorkoutBinding): RecyclerView.ViewHolder(itemView.root){
        init {

            rowWorkoutWeekDayBinding=itemView

            /*itemView.root.llWorkoutAddWeekDay.setOnClickListener{
               *//* WorkoutDayActivity.open(currActivity,arrWorkoutWeekDays[adapterPosition])*//*
            }*/

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWorkoutAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_my_workout
            ,parent,false)
        val bindingUtil= RowMyWorkoutBinding.bind(itemView);
        return MyViewHolder(bindingUtil)


    }

    override fun getItemCount(): Int {
        return arrWorkoutWeekDays.size
    }

    override fun onBindViewHolder(holder: MyWorkoutAdapter.MyViewHolder, position: Int) {
        val model=arrWorkoutWeekDays[position]
        rowWorkoutWeekDayBinding!!.myWorkout =model
    }

}