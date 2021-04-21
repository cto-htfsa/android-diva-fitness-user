package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.databinding.RowAllTrainersBinding
import com.htf.diva.databinding.RowPaymentHistoryBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.PaymentHistoryModel
import kotlinx.android.synthetic.main.row_all_trainers.view.*

class PaymentHistoryAdapter (
        private var currActivity: Activity,
        private var arrTopTrainer:ArrayList<PaymentHistoryModel>,
        private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<PaymentHistoryAdapter.MyViewHolder>(){

    var rowPaymentHistoryBinding: RowPaymentHistoryBinding?=null

    inner class MyViewHolder(itemView: RowPaymentHistoryBinding): RecyclerView.ViewHolder(itemView.root){
        init {

            rowPaymentHistoryBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrTopTrainer[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentHistoryAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
                R.layout.row_payment_history
                ,parent,false)
        val bindingUtil= RowPaymentHistoryBinding.bind(itemView);
        return MyViewHolder(bindingUtil)


    }

    override fun getItemCount(): Int {
        return arrTopTrainer.size
    }

    override fun onBindViewHolder(holder: PaymentHistoryAdapter.MyViewHolder, position: Int) {
        val model=arrTopTrainer[position]
        rowPaymentHistoryBinding!!.paymentHistoryViewModel =model
        if(model.amount=="0.0"){
            holder.itemView.llTrainerReview.visibility= View.GONE
        } else{
            holder.itemView.llTrainerReview.visibility= View.VISIBLE
        }
    }

}