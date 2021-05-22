package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseActivity
import com.htf.diva.base.MyApplication
import com.htf.diva.dashboard.viewModel.CustomerSupportViewModel
import com.htf.diva.databinding.ActivityCustomerSupportBinding
import com.htf.diva.utils.*
import kotlinx.android.synthetic.main.activity_customer_support.*

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

        val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
        if (currUser!=null){
            AppUtils.setText(etName, currUser.user!!.name, "")
            tvDialCode.text="+"+currUser.user!!.dialCode
            AppUtils.setText(etMobileNo, currUser.user!!.mobile, "")
        }


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