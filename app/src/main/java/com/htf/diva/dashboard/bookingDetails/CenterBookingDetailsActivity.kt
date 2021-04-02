package com.htf.diva.dashboard.bookingDetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.SelectedSlotAdapter
import com.htf.diva.dashboard.viewModel.BookingDetailsViewModel
import com.htf.diva.databinding.ActivityBookingDetailsBinding
import com.htf.diva.models.BookingDetailModel
import com.htf.diva.models.UpComingBookingModel
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_booking_details.*
import kotlinx.android.synthetic.main.activity_booking_details.rvSelectedSlots
import kotlinx.android.synthetic.main.activity_booking_details.tvFitnessCenterAddress
import kotlinx.android.synthetic.main.activity_booking_details.tvJoining_from
import kotlinx.android.synthetic.main.activity_booking_details.tvPackages
import kotlinx.android.synthetic.main.activity_booking_details.tvTenure
import kotlinx.android.synthetic.main.activity_booking_details.tvTrainerName
import kotlinx.android.synthetic.main.activity_booking_summary.*
import kotlinx.android.synthetic.main.row_up_coming_booking.view.*
import kotlinx.android.synthetic.main.toolbar.*

class CenterBookingDetailsActivity : BaseDarkActivity<ActivityBookingDetailsBinding, BookingDetailsViewModel>(
    BookingDetailsViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this
    private var bookingId:String=""
    private var bookingDetailModel=BookingDetailModel()
    private lateinit var selectedSlotsAdapter: SelectedSlotAdapter

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
            bookingDetailModel=bookingDetail
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
        tvTitle.text=getString(R.string.booking_id)+""+bookingDetailModel.trackingId
        Glide.with(currActivity).load(Constants.Urls.FITNESS_CENTER_IMAGE_URL + bookingDetail.fitnessCenterImage)
            .placeholder(R.drawable.user).into(ivCenterImage)
        tvFitnessCenterName.text=bookingDetail.fitnessCenterName
        tvBookingDate.text=bookingDetail.joinDate
        tvFitnessCenterAddress.text=bookingDetail.location
        tvTenure.text=bookingDetail.tenureName
        tvJoining_from.text=bookingDetail.joinDate
        tvPackages.text=bookingDetail.packageName
        tvNo_ofPeople.text=bookingDetail.numberOfPeople.toString()


        /* Selected slots rv*/
        if(bookingDetail.trainerId!=null){
            llPersonalTrainer.visibility=View.VISIBLE
            val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.VERTICAL, false)
            rvSelectedSlots.layoutManager = mLayout
            selectedSlotsAdapter = SelectedSlotAdapter(currActivity, bookingDetail.slots!!, this)
            rvSelectedSlots.adapter = selectedSlotsAdapter
        }else{
            llPersonalTrainer.visibility=View.GONE
        }


        if(bookingDetail.trackingId!=null){
            lnrTrainer.visibility=View.VISIBLE
            tvTrainerName.text=bookingDetail.trainerName
        } else{
            lnrTrainer.visibility=View.GONE
        }


    }
}