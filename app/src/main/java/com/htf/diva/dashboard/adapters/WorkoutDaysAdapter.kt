package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.models.UserDietPlans
import com.htf.diva.models.UserWorkouts
import com.htf.diva.models.Workout
import com.htf.diva.netUtils.Constants
import kotlinx.android.synthetic.main.row_workout_days.view.*
import kotlinx.android.synthetic.main.row_workout_weekdays.view.*

class WorkoutDaysAdapter(
        private var currActivity: Activity,
        private var arrWorkoutDays: ArrayList<Workout>,
        private var iListItemClickListener: IListItemClickListener<Any>,
        private var comeFrom: String?): RecyclerView.Adapter<WorkoutDaysAdapter.MyViewHolder>(){


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {

      /*      itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrWorkoutDays[adapterPosition])
            }
*/

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutDaysAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_workout_days, parent, false
        )
        return MyViewHolder(itemView)


    }

    override fun getItemCount(): Int {
        return arrWorkoutDays.size
    }

    override fun onBindViewHolder(holder: WorkoutDaysAdapter.MyViewHolder, position: Int) {
        val model=arrWorkoutDays[position]
        Glide.with(currActivity).load(
            Constants.Urls.WORKOUT_DAY_IMAGE_URL +
                    model.image)
            .placeholder(R.drawable.user).into(holder.itemView.ivWorkoutDay)

        if (comeFrom=="comeFromNoWorkout"){
            if (model.userWorkouts!=null){
                holder.itemView.tvRepetition.text=model.userWorkouts!!.repetitions.toString()
                holder.itemView.tvSets.text=model.userWorkouts!!.sets.toString()
            }else{
                holder.itemView.tvRepetition.text=model.repetitions.toString()
                holder.itemView.tvSets.text=model.sets.toString()
            }

        }else{
            if(model.userWorkouts!=null){
                holder.itemView.tvRepetition.text=model.userWorkouts!!.repetitions.toString()
                holder.itemView.tvSets.text=model.userWorkouts!!.sets.toString()
            }else{
                holder.itemView.tvRepetition.text=model.repetitions.toString()
                holder.itemView.tvSets.text=model.sets.toString()
            }
        }

        holder.itemView.tvWorkoutName.text=model.name

        val arrayRepetition = intArrayOf(5, 10, 20, 25, 30, 50, 75, 100, 150, 200, 500, 100)
        val arraySet = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13,14,15)

        holder.itemView.lnrReps.setOnClickListener {
            holder.itemView.rcvRepQty.visibility= View.VISIBLE
            holder.itemView.rcvSetQty.visibility= View.GONE
            if (model.userWorkouts!=null){
                val adapter = RepQuantityAdapter(currActivity, arrayRepetition, position,model.userWorkouts!!)
                holder.itemView.rcvRepQty.layoutManager = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
                holder.itemView.rcvRepQty.adapter = adapter
            }else{
                val userModel= UserWorkouts()
                userModel.repetitions=1
                userModel.sets=1
                model.userWorkouts=userModel
                val adapter = RepQuantityAdapter(currActivity, arrayRepetition, position,model.userWorkouts!!)
                holder.itemView.rcvRepQty.layoutManager = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
                holder.itemView.rcvRepQty.adapter = adapter
            }

        }

        holder.itemView.lnrSet.setOnClickListener {
            holder.itemView.rcvSetQty.visibility= View.VISIBLE
            holder.itemView.rcvRepQty.visibility= View.GONE
            if (model.userWorkouts!=null){
                val adapter = SetWorkoutQtyAdapter(currActivity, arraySet, position,model.userWorkouts!!)
                holder.itemView.rcvSetQty.layoutManager = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
                holder.itemView.rcvSetQty.adapter = adapter
            }else{
                val userModel= UserWorkouts()
                userModel.sets=1
                userModel.repetitions=1
                model.userWorkouts=userModel
                val adapter = SetWorkoutQtyAdapter(currActivity, arraySet, position,model.userWorkouts!!)
                holder.itemView.rcvSetQty.layoutManager = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
                holder.itemView.rcvSetQty.adapter = adapter
            }
        }

    }

}