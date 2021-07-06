package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.auth.viewModel.ProfileViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.MySubscriptionViewModel
import com.htf.diva.databinding.ActivityMySubscriptionBinding
import kotlinx.android.synthetic.main.toolbar.*

class MySubscriptionActivity : BaseDarkActivity<ActivityMySubscriptionBinding, MySubscriptionViewModel>(
        MySubscriptionViewModel::class.java),
        View.OnClickListener   {
    private var currActivity: Activity =this

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, MySubscriptionActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_my_subscription
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=currActivity.getString(R.string.my_subscription)
        binding.mySubscriptionViewModel = viewModel
        setListener()
       // viewModelInitialize()
        /*viewModel.profileDetails()*/
    }

    private fun setListener() {
        /*btnEditProfile.setOnClickListener(this)
        ivCamera.setOnClickListener(this)*/
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}