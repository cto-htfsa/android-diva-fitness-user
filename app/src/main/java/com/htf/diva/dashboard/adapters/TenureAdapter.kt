package com.htf.diva.dashboard.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.dashboard.ui.FilterActivity
import com.htf.diva.dashboard.ui.TrainerDetailActivity
import com.htf.diva.databinding.RowSpecialisingInBinding
import com.htf.diva.databinding.RowTenureBinding
import com.htf.diva.models.Specialization
import com.htf.diva.models.Tenure
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.row_filter_center.view.*
import kotlinx.android.synthetic.main.row_tenure.view.*

class TenureAdapter (
    private var currActivity: Activity,
    private var arrTenure: ArrayList<Tenure>,
    private var iListItemClickListener: IListItemClickListener<Any>): RecyclerView.Adapter<TenureAdapter.MyViewHolder>(){
    var rowFitnessCenterBinding: RowTenureBinding?=null
    var rowIndex = 0
    inner class MyViewHolder(itemView: RowTenureBinding): RecyclerView.ViewHolder(itemView.root){
        init {
            rowFitnessCenterBinding=itemView
            itemView.root.setOnClickListener {
                val model = arrTenure[adapterPosition]
                iListItemClickListener.onItemClickListener(arrTenure[adapterPosition])
                rowIndex=adapterPosition
                notifyDataSetChanged()
                when (currActivity) {
                    is TrainerDetailActivity -> {
                        (currActivity as TrainerDetailActivity).selectedTenure(model,adapterPosition)
                    }
                }

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenureAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_tenure, parent, false)
        val bindingUtil= RowTenureBinding.bind(itemView)
        return MyViewHolder(bindingUtil)
    }

    override fun getItemCount(): Int {
        return arrTenure.size
    }

    override fun onBindViewHolder(holder: TenureAdapter.MyViewHolder, position: Int) {
        val model=arrTenure[position]
        val days =model.days
        val month =(days!!/30)
        holder.itemView.tvMonth.text= month.toString()
        rowFitnessCenterBinding!!.tenureModel =model
        when (rowIndex) {
            position -> {
                holder.itemView.rltTenure.setBackgroundResource(R.drawable.rect_selected_tenure)
                holder.itemView.tvMonth.setTextColor(ContextCompat.getColor(currActivity, R.color.white))
                if(currActivity is TrainerDetailActivity){
                    (currActivity as TrainerDetailActivity).selectedTenure(model, position)
                }
            }
            else -> {
                holder.itemView.rltTenure.setBackgroundResource(R.drawable.rect_unselected_tenure)
                holder.itemView.tvMonth.setTextColor(ContextCompat.getColor(currActivity, R.color.colorPrimary))
            }
        }
    }

}