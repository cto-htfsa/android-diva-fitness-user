package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.dashboard.ui.FilterActivity
import com.htf.diva.databinding.RowFilterCenterBinding
import com.htf.diva.models.AppDashBoard
import kotlinx.android.synthetic.main.row_filter_center.view.*

class FilterCenterAdapter (
    private var currActivity: Activity,
    private var arrFitnessCenter:ArrayList<AppDashBoard.FitnessCenter>): RecyclerView.Adapter<FilterCenterAdapter.MyViewHolder>(){
    var rowIndex = -1

    var rowFitnessCenterBinding: RowFilterCenterBinding?=null

    inner class MyViewHolder(itemView: RowFilterCenterBinding): RecyclerView.ViewHolder(itemView.root){
        init {
            rowFitnessCenterBinding=itemView
            itemView.root.setOnClickListener {
                val model = arrFitnessCenter[adapterPosition]
                rowIndex=adapterPosition
                notifyDataSetChanged()
                if(currActivity is FilterActivity){
                    (currActivity as FilterActivity).selectedGymCenter(model,adapterPosition)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterCenterAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_filter_center
            ,parent,false)
        val bindingUtil= RowFilterCenterBinding.bind(itemView);
        return MyViewHolder(bindingUtil)
    }

    override fun getItemCount(): Int {
        return arrFitnessCenter.size
    }

    override fun onBindViewHolder(holder: FilterCenterAdapter.MyViewHolder, position: Int) {
        val model=arrFitnessCenter[position]
        rowFitnessCenterBinding!!.fitnessCenter =model
        holder.itemView.filterCenter.backgroundTintList = ColorStateList.valueOf(Color.parseColor(model.text_color))
        if (rowIndex == position) {
            holder.itemView.lnr_selected_item.setBackgroundResource(R.drawable.rect_gym_center_selected)
            if(currActivity is FilterActivity){
                (currActivity as FilterActivity).selectedGymCenter(model, position)
            }
        }else{
            holder.itemView.lnr_selected_item.setBackgroundResource(R.drawable.rect_gym_center_un_selected)
        }

    }

}