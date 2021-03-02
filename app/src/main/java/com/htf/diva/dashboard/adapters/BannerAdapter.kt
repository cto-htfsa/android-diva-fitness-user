package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowBannerBinding
import com.htf.diva.models.Banner
import com.htf.eyenakhr.callBack.IListItemClickListener

class BannerAdapter (
    private var currActivity: Activity,
    private var arrBanner:ArrayList<Banner>,
    private var iListItemClickListener: IListItemClickListener<Any>): RecyclerView.Adapter<BannerAdapter.MyViewHolder>(){

    var rowPayrollBinding:RowBannerBinding?=null

    inner class MyViewHolder(itemView:RowBannerBinding): RecyclerView.ViewHolder(itemView.root){
        init {

            rowPayrollBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrBanner[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.row_banner,parent,false)
        val bindingUtil= RowBannerBinding.bind(itemView);
        return MyViewHolder(bindingUtil)


    }

    override fun getItemCount(): Int {
        return arrBanner.size
    }

    override fun onBindViewHolder(holder: BannerAdapter.MyViewHolder, position: Int) {
        val model=arrBanner[position]
        rowPayrollBinding!!.banner =model
    }

}