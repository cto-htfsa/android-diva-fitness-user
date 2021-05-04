package com.htf.diva.auth.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.auth.viewModel.LoginViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.fragments.HomeFragment
import com.htf.diva.dashboard.fragments.MembershipFragment
import com.htf.diva.dashboard.ui.HomeActivity
import com.htf.diva.databinding.ActivityLoginBinding
import com.htf.diva.models.UserData
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseDarkActivity<ActivityLoginBinding,LoginViewModel>(LoginViewModel::class.java) {
    private var currActivity:Activity=this
    private var comeFrom: String?=""


    companion object{
        fun open(currActivity: Activity, comeFrom: String){
            val intent= Intent(currActivity,LoginActivity::class.java)
            intent.putExtra("comeFrom",comeFrom)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_login
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginViewModel=viewModel
        if (AppSession.locale=="en")  tvLang.text=getString(R.string.arabic) else tvLang.text=getString(R.string.english)
        getExtra()
        viewModelInitialize()

    }

    private fun getExtra() {
        comeFrom = intent.getStringExtra("comeFrom")
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.errorValidateRes,this::onHandleValidationErrorResponse)
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.mLoginData,this::onHandleLoginSuccessResponse)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)

    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }

    private fun onHandleValidationErrorResponse(error:String) {
        DialogUtils.showSnackBar(currActivity, tvLoginError, error)
    }

    private fun onHandleLoginSuccessResponse(userData: UserData?){
        userData?.let {
            OtpActivity.open(currActivity,userData.id,userData.token,fcmId = null,
                mobileNumber = viewModel.mMobile.value.toString(), comeFrom = comeFrom!!)
                finish()
        }
    }

    fun onSwitchLang(view:View){
        switchLang()
    }

    fun skipLogin(view: View){
        HomeActivity.open(currActivity, null)
    }

    private fun switchLang() {
        if (AppSession.locale == "ar") {
            AppSession.locale = "en"
            AppPreferences.getInstance(currActivity)
                .saveInPreference(Constants.KEY_PREF_USER_LANGUAGE, AppSession.locale)
            AppSession.isLocaleEnglish = true
            open(currActivity, "fromSplash")
        }else{
            AppSession.locale = "ar"
            AppPreferences.getInstance(currActivity).saveInPreference(Constants.KEY_PREF_USER_LANGUAGE, AppSession.locale)
            AppSession.isLocaleEnglish = false
            open(currActivity, "fromSplash")
        }
    }


}