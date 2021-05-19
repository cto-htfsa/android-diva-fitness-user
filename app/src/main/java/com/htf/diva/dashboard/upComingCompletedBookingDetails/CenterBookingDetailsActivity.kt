package com.htf.diva.dashboard.upComingCompletedBookingDetails

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
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
import kotlinx.android.synthetic.main.layout_booking_review.view.*
import kotlinx.android.synthetic.main.toolbar.*

class CenterBookingDetailsActivity : BaseDarkActivity<ActivityBookingDetailsBinding, BookingDetailsViewModel>(
    BookingDetailsViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this
    private var bookingId:String=""
    private var bookingDetailModel=BookingDetailModel()
    private lateinit var selectedSlotsAdapter: SelectedSlotAdapter
    private lateinit var dialog: AlertDialog
    var rateTitle=""
    var edtMessage=""

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
            setOnClickListener()
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
        observerViewModel(viewModel.mBookingReview,this::onHandleUpBookingReviewSuccessResponse)
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

    private fun onHandleUpBookingReviewSuccessResponse(bookingReview: Any?) {
        bookingReview?.let {

        }
    }

    private fun setOnClickListener(){
        btnBookingReview.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
           R.id.btnBookingReview->{
               bookingReview()
           }
        }
    }

    @SuppressLint("SetTextI18n")
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


    private fun bookingReview() {
        val builder = AlertDialog.Builder(currActivity)
        val dialogView = currActivity.layoutInflater.inflate(R.layout.layout_booking_review, null)
        builder.setView(dialogView)
        builder.setCancelable(true)
        dialog = builder.create()
        dialog.show()


        val myAnim = AnimationUtils.loadAnimation(currActivity, R.anim.slide_up)
        dialogView.startAnimation(myAnim)

        rateTitle = dialogView.ed_title.text.toString()
        edtMessage=dialogView.edtMessage.text.toString()
        val ratingNumber = dialogView.rating_bar.rating

        dialogView.rlMain.setOnClickListener {
            dialog.dismiss()
        }

        dialogView.btnBookingReview.setOnClickListener {
            if(checkRatingValidation()){
                viewModel.onBookingReviewClick(bookingId,ratingNumber.toString(),rateTitle,edtMessage)
            }
           /* dialog.dismiss()*/
        }

        val window = dialog.window
        window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.BOTTOM)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }

    private fun checkRatingValidation(): Boolean {
        var isValid : Boolean = true

        if(rateTitle.isEmpty()){
            isValid=false
            Toast.makeText(currActivity,getString(R.string.please_enter_title), Toast.LENGTH_SHORT).show()
        }
      if(edtMessage.isEmpty()){
            isValid=false
            Toast.makeText(currActivity,getString(R.string.please_enter_msg), Toast.LENGTH_SHORT).show()
        }

        return  isValid

    }

}