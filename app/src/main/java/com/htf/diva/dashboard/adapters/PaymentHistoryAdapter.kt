package com.htf.diva.dashboard.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.databinding.RowPaymentHistoryBinding
import com.htf.diva.models.PaymentHistoryModel
import com.htf.diva.utils.DateUtilss
import kotlinx.android.synthetic.main.row_payment_history.view.*

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
                R.layout.row_payment_history,parent,false)
        val bindingUtil= RowPaymentHistoryBinding.bind(itemView);
        return MyViewHolder(bindingUtil)
    }

    override fun getItemCount(): Int {
        return arrTopTrainer.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PaymentHistoryAdapter.MyViewHolder, position: Int) {
        val model=arrTopTrainer[position]
        rowPaymentHistoryBinding!!.paymentHistoryViewModel =model

        /*val str = currActivity.getString(R.string.package_membership).replace("[X]", model.bookings!![position].tenureName.toString())
        holder.itemView.tv_tenure.text = str*/
        holder.itemView.tvAmount.text=currActivity.getString(R.string.sar)+" "+ model.amount!!.toString()
        holder.itemView.payment_mode.text=model.paymentMode
        holder.itemView.payment_status.text=model.status
        holder.itemView.payment_date.text= DateUtilss.convertDateFormat(model.createdAt,
                DateUtilss.serverChatUTCDateTimeFormat, DateUtilss.targetDateFormat)
    }

}