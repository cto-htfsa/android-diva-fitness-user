package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.dashboard.viewModel.BuyMemberShipViewModel
import com.htf.diva.databinding.ActivityBookingSummaryBinding
import com.htf.diva.databinding.ActivityBuyMemberShipBinding
import com.htf.diva.models.Packages
import com.htf.diva.models.Slot
import com.htf.diva.models.Tenure
import com.htf.diva.models.TrainerDetailsModel
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.toolbar.*

class BuyMemberShipActivity : BaseDarkActivity<ActivityBuyMemberShipBinding, BuyMemberShipViewModel>(
    BuyMemberShipViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this


    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, BuyMemberShipActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_buy_member_ship
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  tvTitle.text=getString(R.string.booking_summary)*/
        binding.bookingBuyMember = viewModel

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}