package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowPackgesBinding
import com.htf.diva.models.Packages
import com.htf.eyenakhr.callBack.IListItemClickListener

class PackagesAdapter (
    private var currActivity: Activity,
    private var arrPackges: ArrayList<Packages>,
    private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<PackagesAdapter.MyViewHolder>(){
    var rowFitnessCenterBinding: RowPackgesBinding?=null

    inner class MyViewHolder(itemView: RowPackgesBinding): RecyclerView.ViewHolder(itemView.root){
        init {
            rowFitnessCenterBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrPackges[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagesAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_packges, parent, false)
        val bindingUtil= RowPackgesBinding.bind(itemView)
        return MyViewHolder(bindingUtil)
    }

    override fun getItemCount(): Int {
        return arrPackges.size
    }

    override fun onBindViewHolder(holder: PackagesAdapter.MyViewHolder, position: Int) {
        val model=arrPackges[position]
        rowFitnessCenterBinding!!.packagesModel =model
        //    holder.itemView.lnrCenter.backgroundTintList = ColorStateList.valueOf(Color.parseColor(model.text_color))
    }

}