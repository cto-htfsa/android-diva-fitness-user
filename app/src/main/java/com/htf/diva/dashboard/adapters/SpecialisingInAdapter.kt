package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowSpecialisingInBinding
import com.htf.diva.models.Specialization
import com.htf.eyenakhr.callBack.IListItemClickListener

class SpecialisingInAdapter(
    private var currActivity: Activity,
    private var arrSpecialising: ArrayList<Specialization>,
    private var iListItemClickListener: IListItemClickListener<Any>): RecyclerView.Adapter<SpecialisingInAdapter.MyViewHolder>(){
    var rowFitnessCenterBinding: RowSpecialisingInBinding?=null

    inner class MyViewHolder(itemView: RowSpecialisingInBinding): RecyclerView.ViewHolder(itemView.root){
        init {
            rowFitnessCenterBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrSpecialising[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialisingInAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_specialising_in, parent, false)
        val bindingUtil= RowSpecialisingInBinding.bind(itemView)
        return MyViewHolder(bindingUtil)
    }

    override fun getItemCount(): Int {
        return arrSpecialising.size
    }

    override fun onBindViewHolder(holder: SpecialisingInAdapter.MyViewHolder, position: Int) {
        val model=arrSpecialising[position]
        rowFitnessCenterBinding!!.specialisingModel =model
    //    holder.itemView.lnrCenter.backgroundTintList = ColorStateList.valueOf(Color.parseColor(model.text_color))
    }

}