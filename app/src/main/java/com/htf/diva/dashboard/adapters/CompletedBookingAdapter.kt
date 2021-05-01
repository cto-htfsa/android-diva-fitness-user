package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.databinding.RowCompletedBookingBinding
import com.htf.diva.models.CompletedBookingModel
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.DateUtilss
import kotlinx.android.synthetic.main.row_completed_booking.view.*

class CompletedBookingAdapter(
        private var currActivity: Activity,
        private var arrTopTrainer:ArrayList<CompletedBookingModel>,
        private var iListItemClickListener: IListItemClickListener<CompletedBookingModel>):
        RecyclerView.Adapter<CompletedBookingAdapter.MyViewHolder>(){

    var rowPersonalTrainerBinding: RowCompletedBookingBinding?=null

    inner class MyViewHolder(itemView: RowCompletedBookingBinding): RecyclerView.ViewHolder(itemView.root){
        init {
            rowPersonalTrainerBinding=itemView
            itemView.root.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrTopTrainer[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedBookingAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
                R.layout.row_completed_booking
                ,parent,false)
        val bindingUtil= RowCompletedBookingBinding.bind(itemView);
        return MyViewHolder(bindingUtil)


    }

    override fun getItemCount(): Int {
        return arrTopTrainer.size
    }

    override fun onBindViewHolder(holder: CompletedBookingAdapter.MyViewHolder, position: Int) {
        val model=arrTopTrainer[position]
        if (model.trainerId!=null){
            holder.itemView.lnrTrainerDeatil.visibility= View.VISIBLE }else{
            holder.itemView.lnrTrainerDeatil.visibility= View.GONE }

        rowPersonalTrainerBinding!!.completedBookingModel =model
        holder.itemView.tvBookingId.text=model.trackingId
        holder.itemView.tvFitnessCenterName.text=model.fitnessCenterName
        holder.itemView.tvTenureName.text=model.tenureName
        holder.itemView.tvBookingPrice.text=currActivity.getString(R.string.sar)+" "+model.payableAmount!!.toString()

        holder.itemView.tvBookingDate.text= DateUtilss.convertDateFormat(model.createdAt,
                DateUtilss.serverChatUTCDateTimeFormat, DateUtilss.targetDateTimeFormat)

        val str = currActivity.getString(R.string.package_membership).replace("[X]", model.packageName.toString())
        holder.itemView.tvPackage.text=str

        Glide.with(currActivity).load(Constants.Urls.FITNESS_CENTER_IMAGE_URL +
                model.fitnessCenterImage)
                .placeholder(R.drawable.user).into(holder.itemView.ivCenterImage)

        if (model.trainerId!=null){
            holder.itemView.lnrTrainerDeatil.visibility= View.VISIBLE
        }else{
            holder.itemView.lnrTrainerDeatil.visibility= View.GONE
        }

    }
}