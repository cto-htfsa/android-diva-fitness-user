package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.models.Trainers

class TrainerAdapter (
    private var currActivity: Activity,
    private var arrTrainer:ArrayList<Trainers>
):RecyclerView.Adapter<TrainerAdapter.MyViewHolder>(){
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_personal_trainers,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrTrainer.size

    }

    override fun onBindViewHolder(holder: TrainerAdapter.MyViewHolder, position: Int) {
        val model=arrTrainer[position]

    }

}