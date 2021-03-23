package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.databinding.RowPersonalTrainersBinding
import com.htf.diva.databinding.RowUpComingBookingBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.UpComingBookingModel

class UpComingBookingAdapter(
    private var currActivity: Activity,
    private var arrTopTrainer:ArrayList<UpComingBookingModel>,
    private var iListItemClickListener: IListItemClickListener<Any>):
    RecyclerView.Adapter<UpComingBookingAdapter.MyViewHolder>(){

    var rowPersonalTrainerBinding: RowUpComingBookingBinding?=null

    inner class MyViewHolder(itemView: RowUpComingBookingBinding): RecyclerView.ViewHolder(itemView.root){
        init {

            rowPersonalTrainerBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrTopTrainer[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingBookingAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.row_up_coming_booking
            ,parent,false)
        val bindingUtil= RowUpComingBookingBinding.bind(itemView);
        return MyViewHolder(bindingUtil)


    }

    override fun getItemCount(): Int {
        return arrTopTrainer.size
    }

    override fun onBindViewHolder(holder: UpComingBookingAdapter.MyViewHolder, position: Int) {
        val model=arrTopTrainer[position]
        rowPersonalTrainerBinding!!.upComingBookingModel =model
    }

}