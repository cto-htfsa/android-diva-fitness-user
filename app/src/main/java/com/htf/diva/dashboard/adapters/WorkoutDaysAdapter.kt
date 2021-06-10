package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.homeWorkoutPlan.WorkoutDayActivity
import com.htf.diva.models.UserWorkouts
import com.htf.diva.models.Workout
import com.htf.diva.netUtils.Constants
import kotlinx.android.synthetic.main.dialog_reps_sets.view.*
import kotlinx.android.synthetic.main.row_workout_days.view.*

class WorkoutDaysAdapter(
        private var currActivity: Activity,
        private var arrWorkoutDays: ArrayList<Workout>,
        private var iListItemClickListener: IListItemClickListener<Any>,
        private var comeFrom: String?): RecyclerView.Adapter<WorkoutDaysAdapter.MyViewHolder>(){
        var setRepsDialog:AlertDialog?=null
        private val TAG = "NumberPicker"
        var numberPicker=""

       inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {

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
                holder.itemView.ll_remove_workout_plan.visibility=View.VISIBLE
                holder.itemView.llAdd_workout_plan.visibility=View.GONE
                holder.itemView.ll_reps_sats.visibility=View.VISIBLE
                holder.itemView.tvRepetition.text=model.userWorkouts!!.repetitions.toString()
                holder.itemView.tvSets.text=model.userWorkouts!!.sets.toString()
            }else{
                holder.itemView.ll_remove_workout_plan.visibility=View.GONE
                holder.itemView.llAdd_workout_plan.visibility=View.VISIBLE
                holder.itemView.ll_reps_sats.visibility=View.GONE
                holder.itemView.tvRepetition.text=model.repetitions.toString()
                holder.itemView.tvSets.text=model.sets.toString()
            }

        }else{
            if(model.userWorkouts!=null){
                holder.itemView.ll_remove_workout_plan.visibility=View.VISIBLE
                holder.itemView.llAdd_workout_plan.visibility=View.GONE
                holder.itemView.ll_reps_sats.visibility=View.VISIBLE
                holder.itemView.tvRepetition.text=model.userWorkouts!!.repetitions.toString()
                holder.itemView.tvSets.text=model.userWorkouts!!.sets.toString()
            }else{
                holder.itemView.ll_remove_workout_plan.visibility=View.GONE
                holder.itemView.llAdd_workout_plan.visibility=View.VISIBLE
                holder.itemView.ll_reps_sats.visibility=View.GONE
                holder.itemView.tvRepetition.text=model.repetitions.toString()
                holder.itemView.tvSets.text=model.sets.toString()
            }
        }


        holder.itemView.tvWorkoutName.text=model.name

        val arrayRepetition = intArrayOf(5, 10, 20, 25, 30, 50, 75, 100, 150, 200, 500, 100)
        val arraySet = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13,14,15)

        holder.itemView.llAdd_workout_plan.setOnClickListener {
            val model=arrWorkoutDays[position]
            val userModel= UserWorkouts()
            userModel.repetitions=5
            userModel.sets=1
            model.userWorkouts=userModel
            notifyDataSetChanged()
            if(currActivity is WorkoutDayActivity){
                (currActivity as WorkoutDayActivity).addWorkout(model,position)
            }
            holder.itemView.ll_reps_sats.visibility=View.VISIBLE
            holder.itemView.ll_remove_workout_plan.visibility=View.VISIBLE
            holder.itemView.llAdd_workout_plan.visibility=View.GONE
        }

        holder.itemView.ll_remove_workout_plan.setOnClickListener {
            val model=arrWorkoutDays[position]
            model.userWorkouts=null
            notifyDataSetChanged()
            if(currActivity is WorkoutDayActivity){
                (currActivity as WorkoutDayActivity).removeWorkout(model,position)
            }
            holder.itemView.ll_reps_sats.visibility=View.GONE
            holder.itemView.llAdd_workout_plan.visibility=View.VISIBLE
            holder.itemView.ll_remove_workout_plan.visibility=View.GONE
        }


     holder.itemView.lnrReps.setOnClickListener {
         setWorkoutRepetitions(model,position,"Reps")
      }


    holder.itemView.lnrSet.setOnClickListener {
         setWorkoutRepetitions(model,position,"sets")
      }

    /*holder.itemView.lnrReps.setOnClickListener {
       setWorkoutRepetitions()
    }*/




 /*
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
        }*/

    }


    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    private fun setWorkoutRepetitions(workoutModel: Workout?, position: Int, come_from: String) {

        val builder = AlertDialog.Builder(currActivity)
        val dialogView = currActivity.layoutInflater.inflate(R.layout.dialog_reps_sets, null)

        builder.setView(dialogView)
        builder.setCancelable(true)
        setRepsDialog = builder.create()
        setRepsDialog!!.show()

        numberPicker= dialogView.number_picker.value.toString()
        dialogView.btnConfirmReps.setOnClickListener {
            if (come_from=="Reps"){
                Log.d(TAG, numberPicker)
                if (numberPicker!=""){
                    dialogView.tv_title.text=currActivity.getString(R.string.select_repetition)
                    if(currActivity is WorkoutDayActivity){
                        (currActivity as WorkoutDayActivity).selectedReps(numberPicker.toInt(),workoutModel!!,position) }
                    setRepsDialog!!.dismiss()
                }
            }else{
                if (numberPicker!=""){
                    Log.d(TAG, numberPicker)
                    dialogView.tv_title.text=currActivity.getString(R.string.select_sets)
                    if(currActivity is WorkoutDayActivity){
                        (currActivity as WorkoutDayActivity).selectedWorkoutSet(numberPicker.toInt(),workoutModel!!,position)
                    }
                    setRepsDialog!!.dismiss()
                }
            }

        }


        dialogView.number_picker.setOnScrollListener { picker, scrollState ->
            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                numberPicker = picker.value.toString()
            }
        }


        val window = setRepsDialog!!.window
        window!!.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }



}