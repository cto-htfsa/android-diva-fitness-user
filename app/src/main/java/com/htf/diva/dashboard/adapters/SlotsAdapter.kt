package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowSlotsBinding
import com.htf.diva.databinding.RowSpecialisingInBinding
import com.htf.diva.models.Slot
import com.htf.diva.models.Specialization
import com.htf.diva.utils.DateUtils
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.row_slots.view.*

class SlotsAdapter (
    private var currActivity: Activity,
    private var arrSpecialising: List<Slot>,
    private var iListItemClickListener: IListItemClickListener<Any>): RecyclerView.Adapter<SlotsAdapter.MyViewHolder>(){
    var rowFitnessCenterBinding: RowSlotsBinding?=null

    inner class MyViewHolder(itemView: RowSlotsBinding): RecyclerView.ViewHolder(itemView.root){
        init {
            rowFitnessCenterBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrSpecialising[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotsAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_slots, parent, false)
        val bindingUtil= RowSlotsBinding.bind(itemView)
        return MyViewHolder(bindingUtil)
    }

    override fun getItemCount(): Int {
        return arrSpecialising.size
    }

    override fun onBindViewHolder(holder: SlotsAdapter.MyViewHolder, position: Int) {
        val model=arrSpecialising[position]
        rowFitnessCenterBinding!!.slotsModel =model
        val startDate= DateUtils.convertDateFormat(model.startAt, DateUtils.serverTimeFormat, DateUtils.targetTimeFormat)
        val endDate= DateUtils.convertDateFormat(model.endAt, DateUtils.serverTimeFormat, DateUtils.targetTimeFormat)
        holder.itemView.tvTime.text=startDate+"-"+endDate
    }
}