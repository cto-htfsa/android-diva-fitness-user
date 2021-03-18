package com.htf.diva.dashboard.fitnessCenters

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.ui.BookingSuccessfullyActivity
import com.htf.diva.dashboard.ui.BookingSummaryActivity
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.databinding.ActivityBookingSummaryBinding
import com.htf.diva.databinding.ActivityFitnessCenterBookingSummaryBinding
import com.htf.diva.models.Packages
import com.htf.diva.models.Slot
import com.htf.diva.models.Tenure
import com.htf.diva.models.TrainerDetailsModel
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.toolbar.*

class FitnessCenterSummaryActivity : BaseDarkActivity<ActivityFitnessCenterBookingSummaryBinding, BookingSummaryViewModel>(
    BookingSummaryViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this

        companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, FitnessCenterSummaryActivity::class.java)
            currActivity.startActivity(intent)
        }
    }
    override var layout = R.layout.activity_fitness_center_booking_summary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.booking_summary)
        binding.fitnessCenterSummaryViewModel = viewModel
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {

        }
    }


}