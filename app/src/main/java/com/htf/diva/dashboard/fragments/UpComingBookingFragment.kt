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
import com.htf.diva.dashboard.adapters.DietWeekDaysAdapter
import com.htf.diva.dashboard.adapters.TrainerAdapter
import com.htf.diva.dashboard.adapters.UpComingBookingAdapter
import com.htf.diva.dashboard.fitnessCenters.CenterActivity
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.databinding.FragmentUpcomingBookingBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.DietWeekdayModel
import com.htf.diva.models.Listing
import com.htf.diva.models.UpComingBookingModel
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.fragment_membership.view.*
import kotlinx.android.synthetic.main.fragment_membership.view.llUpcomingBooking
import kotlinx.android.synthetic.main.fragment_upcoming_booking.view.*
import kotlinx.android.synthetic.main.fragment_upcoming_booking.view.ivNoImage
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class UpComingBookingFragment : BaseFragment<BookingSummaryViewModel>(BookingSummaryViewModel::class.java) ,
    IListItemClickListener<Any> ,
    View.OnClickListener{
    private lateinit var currActivity: Activity
    private lateinit var upComingBookingAdapter: UpComingBookingAdapter
    private var page=1
    private var isProgressBar=true
    lateinit var binding: FragmentUpcomingBookingBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming_booking, container, false)
        setListener()
        viewModel.onUpComingBookingListing(page = page,isProgressBar = isProgressBar)
        viewModelInitialize()

        return binding.root

    }

    private fun setListener() {
        binding.root.tvBook_memberShip.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.tvBook_memberShip -> {
                CenterActivity.open(currActivity)
            }
        }
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mUpComingBookingResponse,this::onHandleUpComingBookingSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }


    private fun onHandleUpComingBookingSuccessResponse(upComingBooking: Listing<UpComingBookingModel>?) {
        upComingBooking?.let {
            if(upComingBooking.data!!.size>0){
                val mLayout= LinearLayoutManager(currActivity)
                binding.root.llRecyclerView.layoutManager=mLayout
                upComingBookingAdapter= UpComingBookingAdapter(currActivity,upComingBooking.data!!,this)
                binding.root.llRecyclerView.adapter=upComingBookingAdapter
            }else{
                binding.root.llNoUpComingBooking.visibility = View.VISIBLE
                binding.root.llRecyclerView.visibility = View.GONE
            }
        }
    }



}