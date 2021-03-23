package com.htf.diva.dashboard.fitnessCenters

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.CenterAdapter
import com.htf.diva.dashboard.viewModel.FitnessCenterDetailBookingViewModel
import com.htf.diva.databinding.ActivityCenterBinding
import com.htf.diva.models.*
import com.htf.diva.utils.AppSession
import com.htf.diva.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*

class CenterActivity  : BaseDarkActivity<ActivityCenterBinding, FitnessCenterDetailBookingViewModel>(
    FitnessCenterDetailBookingViewModel::class.java), IListItemClickListener<Any>{
    private var currActivity: Activity = this
    private var arrFitnessCenter=ArrayList<AppDashBoard.FitnessCenter>()
    private lateinit var mFitnessCenterAdapter: CenterAdapter

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, CenterActivity::class.java)
            currActivity.startActivity(intent)
              }
          }


    override var layout = R.layout.activity_center
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.choose_center)
        binding.centerViewModel = viewModel


      if(AppSession.appDashBoard !=null) {
            setOutFitnessCenter(AppSession.appDashBoard!!.fitnessCenters)
        }

    }

    /* set fitness center recyclerview here*/
    private fun setOutFitnessCenter(fitnessCenters: ArrayList<AppDashBoard.FitnessCenter>?) {
        val mLayout = GridLayoutManager(currActivity, 2)
        rvFitnessCenter.layoutManager = mLayout
        rvFitnessCenter.itemAnimator = null
        mFitnessCenterAdapter = CenterAdapter(currActivity, fitnessCenters!!, this)
        rvFitnessCenter.adapter = mFitnessCenterAdapter
    }


    override fun onItemClickListener(data: Any) {
        if (data is AppDashBoard.FitnessCenter){
            CenterDetailBookingActivity.open(currActivity,data)
        }
    }

}