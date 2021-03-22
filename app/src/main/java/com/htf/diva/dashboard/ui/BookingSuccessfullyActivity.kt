package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.databinding.ActivityBookingSuccessfullyBinding
import com.htf.diva.callBack.IListItemClickListener

class BookingSuccessfullyActivity : BaseDarkActivity<ActivityBookingSuccessfullyBinding, BookingSummaryViewModel>(
    BookingSummaryViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this

    companion object {
        fun open(currActivity: Activity) {
            val intent = Intent(currActivity, BookingSuccessfullyActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_booking_successfully
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bookingSuccessfully = viewModel

    }


    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}