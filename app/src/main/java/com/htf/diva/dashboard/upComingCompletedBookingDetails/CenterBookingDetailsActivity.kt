package com.htf.diva.dashboard.upComingCompletedBookingDetails

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RatingBar
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
import kotlinx.android.synthetic.main.layout_booking_review.view.*
import kotlinx.android.synthetic.main.row_home_diet_plan.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class CenterBookingDetailsActivity : BaseDarkActivity<ActivityBookingDetailsBinding, BookingDetailsViewModel>(
    BookingDetailsViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this
    private var bookingId:String=""
    private var bookingDetailModel=BookingDetailModel()
    private lateinit var selectedSlotsAdapter: SelectedSlotAdapter
    private lateinit var dialog: AlertDialog
    var rateTitle=""
    var edtMessage=""
    var ratingNumber=""

        companion object{
        fun open(currActivity: Activity, upcomingBookingModel: UpComingBookingModel){
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
            rltBookDetail.visibility=View.VISIBLE
            bookingDetailModel=bookingDetail
            setBookingDetail(bookingDetail)
        }
    }

    private fun onHandleUpBookingReviewSuccessResponse(bookingReview: Any?) {
        bookingReview?.let {
          dialog.dismiss()
            viewModel.bookingDetail(AppSession.locale, AppSession.deviceId, AppSession.deviceType, BuildConfig.VERSION_NAME,bookingId)
        }
    }

    private fun setOnClickListener(){
        btnBookingReview.setOnClickListener(this)
        tvCenterDirection.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
           R.id.btnBookingReview->{
               bookingReview(bookingDetailModel)
           }

            R.id.tvCenterDirection->{
              val addresses=  "http://maps.google.com/maps?daddr="+bookingDetailModel.latitude+","+bookingDetailModel.longitude
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(addresses))
                startActivity(intent)
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

        if (bookingDetail.reviews!=null){
            btnBookingReview.visibility=View.GONE
            cardViewRating.visibility=View.VISIBLE
            tvRatingTitle.text=bookingDetailModel.reviews!!.title
            tvRating.text=bookingDetailModel.reviews!!.rating
            tvRatingMessage.text=bookingDetailModel.reviews!!.message
        }else{
            btnBookingReview.visibility=View.VISIBLE
            cardViewRating.visibility=View.GONE
        }

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


    private fun bookingReview(bookingDetailModel: BookingDetailModel) {
        val builder = AlertDialog.Builder(currActivity)
        val dialogView = currActivity.layoutInflater.inflate(R.layout.layout_booking_review, null)
        builder.setView(dialogView)
        builder.setCancelable(true)
        dialog = builder.create()
        dialog.show()


        val myAnim = AnimationUtils.loadAnimation(currActivity, R.anim.slide_up)
        dialogView.startAnimation(myAnim)

        dialogView.ed_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                rateTitle = charSequence.toString().trim { it <= ' ' }
            }

            override fun afterTextChanged(editable: Editable) {
            }
        })

        dialogView.edtMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                edtMessage = charSequence.toString().trim { it <= ' ' }
            }

            override fun afterTextChanged(editable: Editable) {
            }
        })


       // val ratingNumber = dialogView.rating_bar.rating

        dialogView.rating_bar.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
               // Toast.makeText(currActivity, "Given rating is: $p1", Toast.LENGTH_SHORT).show()
                ratingNumber=p1.toString()

            }
        })


        dialogView.rlMain.setOnClickListener {
            dialog.dismiss()
        }

        dialogView.btnBookingReview.setOnClickListener {
                if(checkRatingValidation()){
                    viewModel.onBookingReviewClick(bookingId,ratingNumber,rateTitle,edtMessage)
                }
        }

        Glide.with(currActivity).load(Constants.Urls.TRAINER_IMAGE_URL + bookingDetailModel.trainerName)
            .placeholder(R.drawable.user).into(dialogView.ivTrainerImage)

        dialogView.tvTrainerName.text=bookingDetailModel.trainerName

        val window = dialog.window
        window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.BOTTOM)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }

    private fun checkRatingValidation(): Boolean {
        var isValid : Boolean = true
        if (ratingNumber.isEmpty()){
            isValid=false
            Toast.makeText(currActivity,getString(R.string.please_rate_this_booking), Toast.LENGTH_SHORT).show()
        }
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