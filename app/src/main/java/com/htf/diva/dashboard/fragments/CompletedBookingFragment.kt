package com.htf.diva.dashboard.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.R
import com.htf.diva.base.BaseFragment
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.CompletedBookingAdapter
import com.htf.diva.dashboard.upComingCompletedBookingDetails.CenterBookingDetailsActivity
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.databinding.FragmentCompletedBookingBinding
import com.htf.diva.models.CompletedBookingModel
import com.htf.diva.models.Listing
import com.htf.diva.models.UpComingBookingModel
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.fragment_upcoming_booking.view.*

class CompletedBookingFragment : BaseFragment<BookingSummaryViewModel>(BookingSummaryViewModel::class.java) ,
        IListItemClickListener<UpComingBookingModel>  {
    private lateinit var currActivity: Activity
    lateinit var binding: FragmentCompletedBookingBinding
    private var page=1
    private var isProgressBar=true
    private lateinit var completedBookingAdapter: CompletedBookingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_completed_booking, container, false)
        viewModel.onCompletedBookingListing(page = page,isProgressBar = isProgressBar)
        viewModelInitialize()
        return binding.root
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mCompletedBookingResponse,this::onHandleCompletedBookingSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }


    private fun onHandleCompletedBookingSuccessResponse(upComingBooking: Listing<UpComingBookingModel>?) {
        upComingBooking?.let {
            if(upComingBooking.data!!.size>0){
                binding.root.llRecyclerView.visibility = View.VISIBLE
                val mLayout= LinearLayoutManager(currActivity)
                binding.root.llRecyclerView.layoutManager=mLayout
                completedBookingAdapter= CompletedBookingAdapter(currActivity,upComingBooking.data!!,
                        this)
                binding.root.llRecyclerView.adapter=completedBookingAdapter
            }else{
                binding.root.llNoUpComingBooking.visibility = View.VISIBLE
                binding.root.llRecyclerView.visibility = View.GONE
            }
        }
    }


    override fun onItemClickListener(data: UpComingBookingModel) {
        CenterBookingDetailsActivity.open(currActivity,data)
    }

}