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
import kotlinx.android.synthetic.main.activity_booking_successfully.*
import kotlinx.android.synthetic.main.activity_fitness_center_booking_summary.*
import kotlinx.android.synthetic.main.activity_fitness_center_booking_summary.btnPayableAmount

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
        setOnClickListener()
    }

    private fun setOnClickListener(){
        tvMyBooking.setOnClickListener(this)
        tvBacktoHome.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tvMyBooking -> {
                HomeActivity.open(currActivity, "memberShipTab")
                currActivity.finish()
            }

            R.id.tvBacktoHome->{
                HomeActivity.open(currActivity, null)
                currActivity.finish()
            }
        }
    }

}