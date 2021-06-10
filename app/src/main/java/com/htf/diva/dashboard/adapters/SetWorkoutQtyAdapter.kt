package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.dashboard.homeWorkoutPlan.WorkoutDayActivity
import com.htf.diva.models.UserWorkouts
import com.htf.diva.models.Workout
import kotlinx.android.synthetic.main.row_reps_qty.view.*

class SetWorkoutQtyAdapter (
    private var currActivity: Activity,
    private var arrMaxQty: IntArray,
    private var selectSetItemPosition: Int,
    private var userWorkoutModel: UserWorkouts) :
    RecyclerView.Adapter<SetWorkoutQtyAdapter.MyViewHolder>() {
    private var rowIndex = -1
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val model = arrMaxQty[adapterPosition]
                rowIndex=adapterPosition
                notifyDataSetChanged()
                when (currActivity) {
                    is WorkoutDayActivity -> {
                       // (currActivity as WorkoutDayActivity).selectedWorkoutSet(model, adapterPosition,selectSetItemPosition,userWorkoutModel)
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_reps_qty, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SetWorkoutQtyAdapter.MyViewHolder, position: Int) {
        val model = arrMaxQty[position]
        holder.itemView.tvQty.text=model.toString()
        if(rowIndex == position) {
            holder.itemView.lnr_rect_max_qty.setBackgroundResource(R.drawable.rect_select_cart_qty)
            if(currActivity is WorkoutDayActivity){
                holder.itemView.tvQty.setTextColor(ContextCompat.getColor(currActivity, R.color.white))
                //(currActivity as WorkoutDayActivity).selectedWorkoutSet(model, position, selectSetItemPosition, userWorkoutModel)
            }
        }else{
            holder.itemView.tvQty.setTextColor(ContextCompat.getColor(currActivity, R.color.black))
            holder.itemView.lnr_rect_max_qty.setBackgroundResource(R.drawable.rect_address_border)
        }
    }

    override fun getItemCount(): Int {
        return arrMaxQty.size
    }
}