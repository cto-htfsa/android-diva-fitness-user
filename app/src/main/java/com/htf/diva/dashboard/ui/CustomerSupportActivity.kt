package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.CustomerSupportViewModel
import com.htf.diva.dashboard.viewModel.FilterViewModel
import com.htf.diva.databinding.ActivityCustomerSupportBinding
import com.htf.diva.databinding.ActivityFilterBinding
import com.htf.diva.utils.AppSession
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.toolbar.*

class CustomerSupportActivity : BaseDarkActivity<ActivityCustomerSupportBinding, CustomerSupportViewModel>(
    CustomerSupportViewModel::class.java), View.OnClickListener{
    private var currActivity: Activity = this

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, CustomerSupportActivity::class.java)
            currActivity.startActivity(intent)
        }
    }
    override var layout = R.layout.activity_customer_support
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.customerSupportViewModel= viewModel

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}