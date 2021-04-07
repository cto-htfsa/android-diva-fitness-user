package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.models.AppDashBoard
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.bookingFitnessCenters.CenterDetailBookingActivity
import kotlinx.android.synthetic.main.row_detail_fitness_center.view.*

class DetailFitnessCenterAdapter (private var currActivity: Activity, private var arrDetailCenter: List<AppDashBoard.FitnessCenter>,
                                  private var fitnessCenter:AppDashBoard.FitnessCenter,
                                  private var iListItemClickListener: IListItemClickListener<Any>
):
    RecyclerView.Adapter<DetailFitnessCenterAdapter.MyViewHolder>(){
    var rowIndex = -1
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            itemView.rltCenter.setOnClickListener {
                val model = arrDetailCenter[adapterPosition]
                rowIndex=adapterPosition
                iListItemClickListener.onItemClickListener(model)
                if(currActivity is CenterDetailBookingActivity){
                    (currActivity as CenterDetailBookingActivity).selectFitnessCenter(model, adapterPosition)
                }
                notifyDataSetChanged()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailFitnessCenterAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_detail_fitness_center, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrDetailCenter.size
    }

    override fun onBindViewHolder(holder: DetailFitnessCenterAdapter.MyViewHolder, position: Int) {
        val model=arrDetailCenter[position]
        holder.itemView.tvFitnessCenterName.text=model.name
        if (position==rowIndex){
            holder.itemView.rltCenter.setBackgroundResource(R.drawable.package_bg)
            holder.itemView.tvFitnessCenterName.setTextColor(ContextCompat.getColor(currActivity, R.color.colorPrimary))
            if(currActivity is CenterDetailBookingActivity){
                (currActivity as CenterDetailBookingActivity).selectFitnessCenter(model, position)
            }
        }else{
            holder.itemView.rltCenter.setBackgroundResource(R.drawable.rect_unselected_fitness_center)
            holder.itemView.tvFitnessCenterName.setTextColor(ContextCompat.getColor(currActivity, R.color.colorText))
        }

    }
}