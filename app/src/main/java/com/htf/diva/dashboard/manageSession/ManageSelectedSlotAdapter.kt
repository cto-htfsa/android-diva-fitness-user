package com.htf.diva.dashboard.manageSession

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.models.BookingDetailModel
import com.htf.diva.models.Slot
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.DateUtilss
import kotlinx.android.synthetic.main.row_manage_slot.view.*
import kotlinx.android.synthetic.main.row_selected_slots.view.*
import java.util.ArrayList

class ManageSelectedSlotAdapter(
    private var currActivity: Activity,
    private var arrSelectedSlots: ArrayList<Slot>): RecyclerView.Adapter<ManageSelectedSlotAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageSelectedSlotAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_manage_selected_slot, parent, false
        )
        return MyViewHolder(itemView)


    }

    override fun getItemCount(): Int {
        return arrSelectedSlots.size
    }

    override fun onBindViewHolder(holder: ManageSelectedSlotAdapter.MyViewHolder, position: Int) {
        val model=arrSelectedSlots[position]
        val startDate= DateUtilss.convertDateFormat(model.startAt, DateUtilss.serverTimeFormat, DateUtilss.targetTimeFormat)
        val endDate= DateUtilss.convertDateFormat(model.endAt, DateUtilss.serverTimeFormat, DateUtilss.targetTimeFormat)
        holder.itemView.tvSelectedTime.text=startDate+"-"+endDate
        holder.itemView.tvSelectedDate.text= DateUtilss.convertDateFormat(model.date, DateUtilss.serverDateFormat, DateUtilss.targetDateFormat)

    }

    fun removeItem(position: Int) {
        arrSelectedSlots.removeAt(position)
        notifyItemRemoved(position)
    }


    fun getData(): ArrayList<Slot> {
        return arrSelectedSlots
    }



}