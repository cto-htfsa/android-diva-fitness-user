package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseActivity
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivitySlotBookBinding
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.toolbar.*

class BookingSummaryActivity : BaseActivity<ActivitySlotBookBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this
    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, BookingSummaryActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_booking_summary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.booking_summary)
        binding.slotsViewModel = viewModel


    }

    fun setListener(){

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {


        }
    }



}