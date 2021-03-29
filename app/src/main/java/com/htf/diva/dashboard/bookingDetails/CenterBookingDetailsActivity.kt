package com.htf.diva.dashboard.bookingDetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.viewModel.BookingDetailsViewModel
import com.htf.diva.databinding.ActivityBookingDetailsBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.Packages
import com.htf.diva.models.Tenure
import kotlinx.android.synthetic.main.toolbar.*

class CenterBookingDetailsActivity : BaseDarkActivity<ActivityBookingDetailsBinding, BookingDetailsViewModel>(
    BookingDetailsViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this

        companion object{
        fun open(
            currActivity: Activity){
            val intent= Intent(currActivity, CenterBookingDetailsActivity::class.java)
            currActivity.startActivity(intent)
        }
    }
        override var layout = R.layout.activity_booking_details
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            tvTitle.text=getString(R.string.booking_summary)
            binding.bookingDetailsViewModel = viewModel

        }

    private fun setOnClickListener(){

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {

        }
    }
}