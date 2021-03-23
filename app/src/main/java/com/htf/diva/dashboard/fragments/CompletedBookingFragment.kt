package com.htf.diva.dashboard.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.htf.diva.R
import com.htf.diva.base.BaseFragment
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.databinding.FragmentCompletedBookingBinding
import com.htf.diva.models.CompletedBookingModel
import com.htf.diva.models.Listing
import com.htf.diva.models.UpComingBookingModel
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast

class CompletedBookingFragment : BaseFragment<BookingSummaryViewModel>(BookingSummaryViewModel::class.java) ,
    IListItemClickListener<Any> {
    private lateinit var currActivity: Activity
    lateinit var binding: FragmentCompletedBookingBinding
    private var page=1
    private var isProgressBar=true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_completed_booking, container, false)
        viewModel.onCompletedBookingListing(page = page,isProgressBar = isProgressBar)

        return binding.root
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mCompletedBookingResponse,this::onHandleUpComingBookingSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }


    private fun onHandleUpComingBookingSuccessResponse(upComingBooking: Listing<CompletedBookingModel>?) {
        upComingBooking?.let {
            if(upComingBooking.data!!.size>0){
                /*val mLayout= LinearLayoutManager(currActivity)
                recycler.layoutManager=mLayout
                dietWeekDaysAdapter= DietWeekDaysAdapter(currActivity,arrList,this)
                recycler.adapter=dietWeekDaysAdapter*/
            }else{
                /* tvClearAll.visibility=View.GONE
                 binding.root.ll_empty.visibility = View.VISIBLE
                 binding.root.ivNoImage.setImageResource(R.drawable.no_diet_plan)
                 binding.root.tvMsg.text=currActivity.getString(R.string.no_diet_available)*/
            }
        }
    }






}