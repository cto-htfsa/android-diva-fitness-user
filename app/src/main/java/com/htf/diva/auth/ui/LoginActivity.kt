package com.htf.diva.auth.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.auth.viewModel.LoginViewModel
import com.htf.diva.auth.viewModel.SplashViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.databinding.ActivityLoginBinding
import com.htf.diva.models.UserData
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseDarkActivity<ActivityLoginBinding,LoginViewModel>(LoginViewModel::class.java) {
    private var currActivity:Activity=this
    private val mSplashViewModel by lazy { getViewModel<SplashViewModel>() }


    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,LoginActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_login
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginViewModel=viewModel
        if (AppSession.locale=="en")  tvLang.text=getString(R.string.arabic) else tvLang.text=getString(R.string.english)
        viewModelInitialize()

    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.errorValidateRes,this::onHandleValidationErrorResponse)
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.mLoginData,this::onHandleLoginSuccessResponse)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(mSplashViewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(mSplashViewModel.isApiCalling,this::onHandleShowProgress)
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
            OtpActivity.open(currActivity,userData.id,userData.token,fcmId = null)
            finish()
        }
    }


    fun onSwitchLang(view:View){
        switchLang()
    }

    private fun switchLang() {
        if (AppSession.locale == "ar") {
            AppSession.locale = "en"
            AppPreferences.getInstance(currActivity)
                .saveInPreference(Constants.KEY_PREF_USER_LANGUAGE, AppSession.locale)
            AppSession.isLocaleEnglish = true
            open(currActivity)
        }else{
            AppSession.locale = "ar"
            AppPreferences.getInstance(currActivity).saveInPreference(Constants.KEY_PREF_USER_LANGUAGE, AppSession.locale)
            AppSession.isLocaleEnglish = false
            open(currActivity)
        }
    }

}