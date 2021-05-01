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
import com.htf.diva.models.Workout
import com.htf.diva.netUtils.Constants
import kotlinx.android.synthetic.main.row_workout_days.view.*

class WorkoutDaysAdapter(
    private var currActivity: Activity,
    private var arrWorkoutDays: ArrayList<Workout>,
    private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<WorkoutDaysAdapter.MyViewHolder>(){


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

        holder.itemView.tvWorkoutName.text=model.name

        holder.itemView.tvRepetition.text=model.repetitions.toString()
        holder.itemView.tvSets.text=model.sets.toString()

        val array = intArrayOf(2, 5, 6, 8, 0, 7, 9, 4)

        holder.itemView.lnrReps.setOnClickListener {
            holder.itemView.rcvRepQty.visibility= View.VISIBLE
            holder.itemView.rcvSetQty.visibility= View.GONE
            val adapter = RepQuantityAdapter(currActivity, array, position,model)
            holder.itemView.rcvRepQty.layoutManager = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
            holder.itemView.rcvRepQty.adapter = adapter
        }

        holder.itemView.lnrSet.setOnClickListener {
            holder.itemView.rcvSetQty.visibility= View.VISIBLE
            holder.itemView.rcvRepQty.visibility= View.GONE
            val adapter = SetWorkoutQtyAdapter(currActivity, array, position,model)
            holder.itemView.rcvSetQty.layoutManager = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
            holder.itemView.rcvSetQty.adapter = adapter
        }

    }

}