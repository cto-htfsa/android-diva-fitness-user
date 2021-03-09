package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.CustomerSupportViewModel
import com.htf.diva.databinding.ActivityAboutUsBinding
import com.htf.diva.databinding.ActivityCustomerSupportBinding
import kotlinx.android.synthetic.main.toolbar.*

class AboutUsActivity : BaseDarkActivity<ActivityAboutUsBinding, CustomerSupportViewModel>(
    CustomerSupportViewModel::class.java), View.OnClickListener{

    private var currActivity: Activity = this

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, AboutUsActivity::class.java)
            currActivity.startActivity(intent)
        }
    }


    override var layout = R.layout.activity_about_us
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.aboutUsViewModel= viewModel

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}