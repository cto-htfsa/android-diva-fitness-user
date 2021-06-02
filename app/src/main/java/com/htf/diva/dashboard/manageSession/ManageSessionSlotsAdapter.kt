package com.htf.diva.dashboard.manageSession

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.SlotsAdapter
import com.htf.diva.models.Slot
import com.htf.diva.utils.DateUtilss
import kotlinx.android.synthetic.main.row_slots.view.*

class ManageSessionSlotsAdapter  (
    private var currActivity: Activity,
    private var arrSlots: List<Slot>,
    private var iListItemClickListener: IListItemClickListener<Any>
     ): RecyclerView.Adapter<ManageSessionSlotsAdapter.MyViewHolder>(){
     var rowIndex = -1
     inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            itemView.rltSlots.setOnClickListener {
                val model = arrSlots[adapterPosition]
                rowIndex=adapterPosition
                iListItemClickListener.onItemClickListener(model)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageSessionSlotsAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_slots, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrSlots.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ManageSessionSlotsAdapter.MyViewHolder, position: Int) {
        val model=arrSlots[position]
        /*  rowFitnessCenterBinding!!.slotsModel =model*/
        val startDate= DateUtilss.convertDateFormat(model.startAt, DateUtilss.serverTimeFormat, DateUtilss.targetTimeFormat)
        val endDate= DateUtilss.convertDateFormat(model.endAt, DateUtilss.serverTimeFormat, DateUtilss.targetTimeFormat)
        holder.itemView.tvTime.text= "$startDate-$endDate"
        if (position==rowIndex){
            holder.itemView.rltSlots.setBackgroundResource(R.drawable.package_bg)
            holder.itemView.tvTime.setTextColor(ContextCompat.getColor(currActivity, R.color.colorPrimary))
            holder.itemView.ivSelected.setBackgroundResource(R.drawable.cancel)
        }else{
            holder.itemView.rltSlots.setBackgroundResource(R.drawable.slots_unselected)
            holder.itemView.tvTime.setTextColor(ContextCompat.getColor(currActivity, R.color.colorText))
        }

    }
}