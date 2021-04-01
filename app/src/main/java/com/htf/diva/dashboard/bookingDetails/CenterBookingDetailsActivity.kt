package com.htf.diva.dashboard.bookingDetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.viewModel.BookingDetailsViewModel
import com.htf.diva.databinding.ActivityBookingDetailsBinding
import com.htf.diva.models.BookingDetailModel
import com.htf.diva.models.UpComingBookingModel
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_booking_details.*
import kotlinx.android.synthetic.main.toolbar.*

class CenterBookingDetailsActivity : BaseDarkActivity<ActivityBookingDetailsBinding, BookingDetailsViewModel>(
    BookingDetailsViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this
    private var bookingId:String=""

        companion object{
        fun open(
            currActivity: Activity, upcomingBookingModel: UpComingBookingModel){
            val intent= Intent(currActivity, CenterBookingDetailsActivity::class.java)
            intent.putExtra("upcomingBookingModel",upcomingBookingModel)
            currActivity.startActivity(intent)
        }
    }
        override var layout = R.layout.activity_booking_details
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            tvTitle.text=getString(R.string.booking_summary)
            binding.bookingDetailsViewModel = viewModel
            getExtra()
            viewModel.bookingDetail(AppSession.locale, AppSession.deviceId, AppSession.deviceType, BuildConfig.VERSION_NAME,bookingId)
            viewModelInitialize()
        }

    private fun getExtra() {
       val upcomingBookingModel=intent.getSerializableExtra("upcomingBookingModel")as UpComingBookingModel
        bookingId=upcomingBookingModel.id.toString()
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mBookingDetailData,this::onHandleUpBookingDetailSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }

    private fun onHandleUpBookingDetailSuccessResponse(bookingDetail: BookingDetailModel?) {
        bookingDetail?.let {
           setBookingDetail(bookingDetail)
        }
    }

    private fun setOnClickListener(){

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {

        }
    }

    private fun setBookingDetail(bookingDetail: BookingDetailModel) {
        tvFitnessCenterName.text=bookingDetail.fitnessCenterName
        tvBookingDate.text=bookingDetail.joinDate
        tvFitnessCenterAddress.text=bookingDetail.location

    }

}