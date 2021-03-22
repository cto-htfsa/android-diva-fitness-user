package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.dashboard.fitnessCenters.CenterDetailBookingActivity
import com.htf.diva.dashboard.ui.TrainerDetailActivity
import com.htf.diva.databinding.RowPackgesBinding
import com.htf.diva.models.Packages
import com.htf.diva.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.row_packges.view.*

class PackagesAdapter (
    private var currActivity: Activity,
    private var arrPackges: List<Packages>,
    private var iListItemClickListener: IListItemClickListener<Any>
):
    RecyclerView.Adapter<PackagesAdapter.MyViewHolder>(){
    var rowFitnessCenterBinding: RowPackgesBinding?=null
    var rowIndex = 0
    inner class MyViewHolder(itemView: RowPackgesBinding): RecyclerView.ViewHolder(itemView.root){
        init {
            rowFitnessCenterBinding=itemView
            itemView.root.setOnClickListener {
                val model = arrPackges[adapterPosition]
                iListItemClickListener.onItemClickListener(arrPackges[adapterPosition])
                rowIndex=adapterPosition
                notifyDataSetChanged()
                when (currActivity) {
                    is TrainerDetailActivity -> {
                        (currActivity as TrainerDetailActivity).selectedPackage(model,adapterPosition)
                    }
                    is CenterDetailBookingActivity -> {
                        (currActivity as CenterDetailBookingActivity).selectedPackage(model,adapterPosition)
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagesAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.row_packges, parent, false)
        val bindingUtil= RowPackgesBinding.bind(itemView)
        return MyViewHolder(bindingUtil)
    }

    override fun getItemCount(): Int {
        return arrPackges.size
    }

    override fun onBindViewHolder(holder: PackagesAdapter.MyViewHolder, position: Int) {
        val model=arrPackges[position]
        rowFitnessCenterBinding!!.packagesModel =model
        when (rowIndex) {
            position -> {
                holder.itemView.rltPackage.setBackgroundResource(R.drawable.package_bg)
                holder.itemView.tvSelected.setTextColor(ContextCompat.getColor(currActivity, R.color.colorPrimary))
                holder.itemView.ivSelected.setBackgroundResource(R.drawable.cancel)
                if(currActivity is TrainerDetailActivity){
                    (currActivity as TrainerDetailActivity).selectedPackage(model, position)
                }
                if(currActivity is CenterDetailBookingActivity)
                    (currActivity as CenterDetailBookingActivity).selectedPackage(model,position)
            }
            else -> {
                holder.itemView.rltPackage.setBackgroundResource(R.drawable.package_bg_unselected)
                holder.itemView.tvSelected.setTextColor(ContextCompat.getColor(currActivity, R.color.gray3))
                holder.itemView.ivSelected.setBackgroundResource(R.drawable.tick)
            }
        }
    }

}