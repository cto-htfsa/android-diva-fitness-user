package com.htf.diva.dashboard.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.R
import com.htf.diva.dashboard.fragments.HomeFragment
import com.htf.diva.models.Dashboard

class HomeDataAdapter (
    private var currActivity: Activity,
    var currFragment: Fragment,
    private var dashboard: Dashboard
):RecyclerView.Adapter<HomeDataAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return when (viewType) {
            0 -> MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_store_banner, parent, false))
            1 -> MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_top_personal_trainers, parent, false))
            2 -> MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_our_fitness_center, parent, false))
            else-> MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_top_personal_trainers, parent, false))
        }

    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (position) {
            0 -> (currFragment as HomeFragment).populateBanner(holder,dashboard.topBanners)
            1 -> (currFragment as HomeFragment).populatePersonalTrainers(holder,dashboard.trainers)
            2 -> (currFragment as HomeFragment).populateCenter(holder,dashboard.branchCentre)


        }

    }
}