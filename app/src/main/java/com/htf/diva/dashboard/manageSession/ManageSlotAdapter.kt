package com.htf.diva.dashboard.manageSession

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.models.BookingDetailModel
import com.htf.diva.netUtils.Constants
import kotlinx.android.synthetic.main.row_manage_slot.view.*

class ManageSlotAdapter(
    private var currActivity: Activity,
    private var arrWorkoutDays: ArrayList<BookingDetailModel>): RecyclerView.Adapter<ManageSlotAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageSlotAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_manage_slot, parent, false
        )
        return MyViewHolder(itemView)


    }

    override fun getItemCount(): Int {
        return arrWorkoutDays.size
    }

    override fun onBindViewHolder(holder: ManageSlotAdapter.MyViewHolder, position: Int) {
        val model=arrWorkoutDays[position]

        Glide.with(currActivity).load(Constants.Urls.TRAINER_IMAGE_URL + model.trainerImage)
            .placeholder(R.drawable.user).into(holder.itemView.ivTrainerImage)

        holder.itemView.tvTrainerName.text=model.trainerName.toString()

        holder.itemView.tvManageSession.setOnClickListener {
            ManageSessionActivity.open(currActivity,model)
        }

    }

}