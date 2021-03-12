package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.auth.ui.OtpActivity
import com.htf.diva.base.BaseActivity
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.CustomerSupportViewModel
import com.htf.diva.dashboard.viewModel.FilterViewModel
import com.htf.diva.databinding.ActivityCustomerSupportBinding
import com.htf.diva.databinding.ActivityFilterBinding
import com.htf.diva.models.UserData
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_customer_support.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.tvLoginError
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.toolbar.*

class CustomerSupportActivity : BaseActivity<ActivityCustomerSupportBinding, CustomerSupportViewModel>(
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
        viewModelInitialize()
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.errorValidateRes,this::onHandleValidationErrorResponse)
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.mCustomerSupport,this::onHandleCustomerSupportSuccessResponse)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)

    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }

    private fun onHandleValidationErrorResponse(error:String) {
        DialogUtils.showSnackBar(currActivity, tvCustomerSupportError, error)
    }

    private fun onHandleCustomerSupportSuccessResponse(customerSupport: Any?){
        customerSupport?.let {
            currActivity.finish()
        }
    }


    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}