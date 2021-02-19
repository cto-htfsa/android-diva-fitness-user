package com.htf.diva.auth.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.auth.viewModel.LoginViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.databinding.ActivityLoginBinding
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.AppPreferences
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.diva.utils.observerViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseDarkActivity<ActivityLoginBinding,LoginViewModel>(LoginViewModel::class.java) {
    private var currActivity:Activity=this

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


    }

    private fun onHandleValidationErrorResponse(error:String) {
        //DialogUtils.showSnackBar(currActivity, tvLoginError, error)
        OtpActivity.open(currActivity)

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