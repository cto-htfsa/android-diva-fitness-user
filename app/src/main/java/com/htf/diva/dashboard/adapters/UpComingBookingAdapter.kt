package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.databinding.RowUpComingBookingBinding
import com.htf.diva.models.UpComingBookingModel
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.DateUtilss
import kotlinx.android.synthetic.main.row_up_coming_booking.view.*

class UpComingBookingAdapter(
    private var currActivity: Activity,
    private var arrTopTrainer:ArrayList<UpComingBookingModel>,
    private var iListItemClickListener: IListItemClickListener<UpComingBookingModel>):
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
        if (model.trainerId!=null){
            holder.itemView.lnrTrainerDeatil.visibility= View.VISIBLE }else{
            holder.itemView.lnrTrainerDeatil.visibility= View.GONE }

        rowPersonalTrainerBinding!!.upComingBookingModel =model
        holder.itemView.tvBookingId.text=model.trackingId
        holder.itemView.tvFitnessCenterName.text=model.fitnessCenterName
        holder.itemView.tvTenureName.text=model.tenureName
        holder.itemView.tvBookingPrice.text=currActivity.getString(R.string.sar)+" "+model.payableAmount!!.toString()

        holder.itemView.tvBookingDate.text=DateUtilss.convertDateFormat(model.createdAt,
            DateUtilss.serverChatUTCDateTimeFormat,DateUtilss.targetDateTimeFormat)

        val str = currActivity.getString(R.string.package_membership).replace("[X]", model.packageName.toString())
        holder.itemView.tvPackage.text=str

        Glide.with(currActivity).load(Constants.Urls.FITNESS_CENTER_IMAGE_URL +
                model.fitnessCenterImage)
            .placeholder(R.drawable.user).into(holder.itemView.ivCenterImage)

         if (model.trainerId!=null){
             holder.itemView.lnrTrainerDeatil.visibility=View.VISIBLE
         }else{
             holder.itemView.lnrTrainerDeatil.visibility=View.GONE
         }

    }
}