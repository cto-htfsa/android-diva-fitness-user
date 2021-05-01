package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowSelectedSlotsBinding
import com.htf.diva.models.Slot
import com.htf.diva.utils.DateUtilss
import com.htf.diva.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.row_selected_slots.view.*

class SelectedSlotAdapter(
    private var currActivity: Activity,
    private var arrSlots: List<Slot>,
    private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<SelectedSlotAdapter.MyViewHolder>(){
    var rowFitnessCenterBinding: RowSelectedSlotsBinding?=null
    var rowIndex = -1
    inner class MyViewHolder(itemView: RowSelectedSlotsBinding): RecyclerView.ViewHolder(itemView.root){
        init {
            rowFitnessCenterBinding=itemView
            itemView.root.setOnClickListener {
                val model = arrSlots[adapterPosition]
                iListItemClickListener.onItemClickListener(arrSlots[adapterPosition])
                rowIndex=adapterPosition
                notifyDataSetChanged()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedSlotAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_selected_slots, parent, false)
        val bindingUtil= RowSelectedSlotsBinding.bind(itemView)
        return MyViewHolder(bindingUtil)
    }

    override fun getItemCount(): Int {
        return arrSlots.size
    }

    override fun onBindViewHolder(holder: SelectedSlotAdapter.MyViewHolder, position: Int) {
        val model=arrSlots[position]
        rowFitnessCenterBinding!!.slotsModel =model
        val startDate= DateUtilss.convertDateFormat(model.startAt, DateUtilss.serverTimeFormat, DateUtilss.targetTimeFormat)
        val endDate= DateUtilss.convertDateFormat(model.endAt, DateUtilss.serverTimeFormat, DateUtilss.targetTimeFormat)
        holder.itemView.tvSelectedTime.text=startDate+"-"+endDate
        holder.itemView.tvSelectedDate.text= DateUtilss.convertDateFormat(model.date, DateUtilss.serverDateFormat, DateUtilss.targetDateFormat)

    }
}