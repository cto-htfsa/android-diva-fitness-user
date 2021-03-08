package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.databinding.RowAllTrainersBinding
import com.htf.diva.databinding.RowPersonalTrainersBinding
import com.htf.diva.models.AppDashBoard
import com.htf.eyenakhr.callBack.IListItemClickListener

class AllTrainersAdapter (
    private var currActivity: Activity,
    private var arrTopTrainer:ArrayList<AppDashBoard.TopTrainer>,
    private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<AllTrainersAdapter.MyViewHolder>(){

    var rowPersonalTrainerBinding: RowAllTrainersBinding?=null

    inner class MyViewHolder(itemView: RowAllTrainersBinding): RecyclerView.ViewHolder(itemView.root){
        init {

            rowPersonalTrainerBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrTopTrainer[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTrainersAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_all_trainers
            ,parent,false)
        val bindingUtil= RowAllTrainersBinding.bind(itemView);
        return MyViewHolder(bindingUtil)


    }

    override fun getItemCount(): Int {
        return arrTopTrainer.size
    }

    override fun onBindViewHolder(holder: AllTrainersAdapter.MyViewHolder, position: Int) {
        val model=arrTopTrainer[position]
        rowPersonalTrainerBinding!!.allTrainer =model
    }

}