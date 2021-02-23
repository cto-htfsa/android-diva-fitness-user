package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.models.Branch

class CenterAdapter (
    private var currActivity: Activity,
    private var arrCenter:ArrayList<Branch>
):RecyclerView.Adapter<CenterAdapter.MyViewholder>(){

    inner class MyViewholder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_center,parent,false)
        return MyViewholder(itemView)

    }

    override fun getItemCount(): Int {
        return arrCenter.size

    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val model=arrCenter[position]

    }
}