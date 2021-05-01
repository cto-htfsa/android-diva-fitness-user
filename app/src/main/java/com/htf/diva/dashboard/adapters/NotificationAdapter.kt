package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.models.Notifications
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DateUtilss
import kotlinx.android.synthetic.main.row_notifications.view.*
import kotlinx.android.synthetic.main.toolbar.view.tvTitle

class NotificationAdapter(
    private var currActivity: Activity,
    private var arrNoti:ArrayList<Notifications>): RecyclerView.Adapter<NotificationAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        init {
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.row_notifications,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrNoti.size
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val model=arrNoti[position]
        holder.itemView.tvTitle.text=if (AppSession.locale=="en") model.title else model.title
        holder.itemView.tvTime.text=
            DateUtilss.convertDateFormat(model.createdAt,DateUtilss.serverDateTimeFormat,DateUtilss.targetDateTimeFormat)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}