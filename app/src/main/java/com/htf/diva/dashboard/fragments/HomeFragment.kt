package com.htf.diva.dashboard.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseFragment
import com.htf.diva.dashboard.adapters.CenterAdapter
import com.htf.diva.dashboard.adapters.TrainerAdapter
import com.htf.diva.dashboard.viewModel.HomeViewModel
import com.htf.diva.databinding.FragmentHomeBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.Banner
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils.showToast
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class.java) {
    private lateinit var currActivity: Activity
    lateinit var binding: FragmentHomeBinding
    private lateinit var trainerAdapter: TrainerAdapter
    private lateinit var centerAdapter: CenterAdapter
    private var dots = ArrayList<ImageView>()
    private var arrBanner = ArrayList<Banner>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeFragmentViewModel = viewModel
        println("onCreate:->SHUBHAM")
        viewModel.appDashBoard(AppSession.locale, AppSession.deviceId, AppSession.deviceType, BuildConfig.VERSION_NAME)
        viewModelInitialize()
        return binding.root
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mDashBoardData,this::onHandleAppDashBoardSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }

    private fun onHandleAppDashBoardSuccessResponse(appDashBoard: AppDashBoard?){
        appDashBoard?.let {

        }
    }


}