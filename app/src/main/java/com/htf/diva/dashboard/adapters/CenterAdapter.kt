package com.htf.diva.dashboard.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowCenterBinding
import com.htf.diva.models.AppDashBoard
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.row_center.view.*
import java.text.NumberFormat

class CenterAdapter(
    private var currActivity: Activity,
    private var arrFitnessCenter: ArrayList<AppDashBoard.FitnessCenter>,
    private var iListItemClickListener: IListItemClickListener<Any>): RecyclerView.Adapter<CenterAdapter.MyViewHolder>(){
    var rowFitnessCenterBinding: RowCenterBinding?=null

    inner class MyViewHolder(itemView: RowCenterBinding): RecyclerView.ViewHolder(itemView.root){
        init {
            rowFitnessCenterBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrFitnessCenter[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_center, parent, false)
        val bindingUtil= RowCenterBinding.bind(itemView)
        return MyViewHolder(bindingUtil)
    }

    override fun getItemCount(): Int {
        return arrFitnessCenter.size
    }

    override fun onBindViewHolder(holder: CenterAdapter.MyViewHolder, position: Int) {
        val model=arrFitnessCenter[position]
        rowFitnessCenterBinding!!.fitnessCenter =model
        holder.itemView.lnrCenter.backgroundTintList = ColorStateList.valueOf(Color.parseColor(model.text_color))

    }

}