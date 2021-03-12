package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseActivity
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.CustomerSupportViewModel
import com.htf.diva.databinding.ActivityAboutUsBinding
import com.htf.diva.databinding.ActivityCustomerSupportBinding
import com.htf.diva.models.AboutModel
import com.htf.diva.models.AppDashBoard
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.toolbar.*

class AboutUsActivity : BaseActivity<ActivityAboutUsBinding, CustomerSupportViewModel>(
    CustomerSupportViewModel::class.java), View.OnClickListener{

    private var currActivity: Activity = this

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, AboutUsActivity::class.java)
            currActivity.startActivity(intent) }
    }

    override var layout = R.layout.activity_about_us
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.aboutUsViewModel= viewModel
        viewModel.appAboutUs(AppSession.locale, AppSession.deviceId, AppSession.deviceType, BuildConfig.VERSION_NAME)
        viewModelInitialize()
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mAppAboutUs,this::onHandleAppDashBoardSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }

    private fun onHandleAppDashBoardSuccessResponse(appAboutUs: AboutModel?){
        appAboutUs?.let {
            tvAboutUs.text=appAboutUs.about_us
        }
    }




    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }



}