package com.htf.diva.dashboard.homeWorkoutPlan

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.models.UserWorkout
import com.htf.diva.netUtils.Constants
import kotlinx.android.synthetic.main.row_my_workout.view.*

class MyWorkoutAdapter(
        private var currActivity: Activity,
        private var arrWorkout: ArrayList<UserWorkout>,
        var currentFragment: Fragment):
        RecyclerView.Adapter<MyWorkoutAdapter.MyViewHolder>(){


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            itemView.ivCheckGrey.setOnClickListener {
                val model = arrWorkout[adapterPosition]
                if (currentFragment is WorkoutFragment) {
                        (currentFragment as WorkoutFragment).updateCompletedWorkout(model,adapterPosition)

                }
            }

            itemView.ivCheckGreen.setOnClickListener {
                val model = arrWorkout[adapterPosition]
                when (currActivity) {
                    /*  is CreateWorkoutPlanActivity -> {
                          (currActivity as CreateWorkoutPlanActivity).selectDayRest(model, adapterPosition)
                      }*/
                }
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWorkoutAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_my_workout
            ,parent,false)
        return MyViewHolder(itemView)


    }

    override fun getItemCount(): Int {
        return arrWorkout.size
    }

    override fun onBindViewHolder(holder: MyWorkoutAdapter.MyViewHolder, position: Int) {
        val model=arrWorkout[position]

        Glide.with(currActivity).load(Constants.Urls.WORKOUT_DAY_IMAGE_URL + model.image)
                .placeholder(R.drawable.user).into(holder.itemView.ivWorkout)

        holder.itemView.tvWorkoutName.text=model.name
        holder.itemView.tvCaloriesBurn.text=model.caloriesBurn+" "+ currActivity.getString(R.string.burns)
        holder.itemView.tvWorkoutRepetition.text=currActivity.getString(R.string.reps)+" "+ model.repetitions
        holder.itemView.tvWorkoutSet.text=currActivity.getString(R.string.set)+" "+ model.sets

        if(model.workoutCompleted==1){
            holder.itemView.ivCheckGreen.visibility=View.VISIBLE
            holder.itemView.ivCheckGrey.visibility=View.GONE
        }else{
            holder.itemView.ivCheckGreen.visibility=View.GONE
            holder.itemView.ivCheckGrey.visibility=View.VISIBLE
        }

    }

}