package com.htf.diva.dashboard.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.R
import com.htf.diva.base.BaseFragment
import com.htf.diva.dashboard.adapters.CenterAdapter
import com.htf.diva.dashboard.adapters.HomeDataAdapter
import com.htf.diva.dashboard.adapters.TrainerAdapter
import com.htf.diva.dashboard.viewModel.HomeViewModel
import com.htf.diva.databinding.FragmentHomeBinding
import com.htf.diva.models.Banner
import com.htf.diva.models.Branch
import com.htf.diva.models.Dashboard
import com.htf.diva.models.Trainers
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.layout_our_fitness_center.view.*
import kotlinx.android.synthetic.main.layout_store_banner.view.*
import kotlinx.android.synthetic.main.layout_top_personal_trainers.view.*

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class.java) {
    private lateinit var currActivity: Activity
    lateinit var binding: FragmentHomeBinding
    private var dashBoardData = Dashboard()
    private lateinit var trainerAdapter:TrainerAdapter
    private lateinit var centerAdapter:CenterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        currActivity=requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeFragmentViewModel=viewModel
        initRecycler(dashBoardData)
        return binding.root
    }

    private fun initRecycler(dashBoardData: Dashboard) {
        val mLayout=LinearLayoutManager(currActivity)
        binding.root.recycler.layoutManager=mLayout
        val adapter=HomeDataAdapter(currActivity,this,dashBoardData)
        binding.root.recycler.adapter=adapter

    }

    fun populateBanner(holder: HomeDataAdapter.MyViewHolder, arrBanner: ArrayList<Banner>) {
        arrBanner.clear()
        arrBanner.add(Banner())
        arrBanner.add(Banner())
        arrBanner.add(Banner())
        val adapter = ViewPagerAdapter(childFragmentManager)
        for (banner in arrBanner)
            adapter.addFragment(BannerFragment.create(banner), "")
        holder.itemView.vpBanner.adapter = adapter

    }

    fun populatePersonalTrainers(holder: HomeDataAdapter.MyViewHolder, trainers: ArrayList<Trainers>) {
        trainers.clear()
        trainers.add(Trainers())
        trainers.add(Trainers())
        trainers.add(Trainers())
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
        holder.itemView.rvPersonalTrainers.layoutManager = mLayout
        trainerAdapter = TrainerAdapter(currActivity, trainers)
        holder.itemView.rvPersonalTrainers.adapter = trainerAdapter


    }

    fun populateCenter(holder: HomeDataAdapter.MyViewHolder, branchCentre: ArrayList<Branch>) {
        branchCentre.clear()
        branchCentre.add(Branch())
        branchCentre.add(Branch())
        branchCentre.add(Branch())
        branchCentre.add(Branch())
        val mLayout=GridLayoutManager(currActivity,2)
        holder.itemView.rvFitnessCenter.layoutManager=mLayout
        centerAdapter=CenterAdapter(currActivity,branchCentre)
        holder.itemView.rvFitnessCenter.adapter=centerAdapter


    }


}