package com.htf.diva.auth.ui

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.htf.diva.BuildConfig
import com.htf.diva.auth.viewModel.SplashViewModel
import com.htf.diva.R
import com.htf.diva.base.BaseActivity
import com.htf.diva.base.MyApplication
import com.htf.diva.dashboard.ui.HomeActivity
import com.htf.diva.databinding.ActivitySplashBinding
import com.htf.diva.models.AppSetting
import com.htf.diva.utils.AppPreferences
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showForceUpdateDialog
import com.htf.diva.utils.showToast

class SplashActivity : BaseActivity<ActivitySplashBinding,SplashViewModel>(SplashViewModel::class.java) {
    private var currActivity:Activity=this

    override val layout: Int get() = R.layout.activity_splash
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.splashViewModel=viewModel
        val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
        if (currUser!=null){
            Handler(Looper.getMainLooper()).postDelayed({
                HomeActivity.open(currActivity, null)
                finish()
            },3000)
        }
        else{
            viewModel.onGetAppVersion(false)
        }
        viewModelInitialize()
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mAppVersionData,this::onHandleAppVersionSuccessResponse)
    }
    private fun onHandleApiErrorResponse(error: String){
        showToast(error, true)
    }

    private fun onHandleAppVersionSuccessResponse(appVersion: AppSetting?){
        appVersion?.let {
            setForceUpdateData(appVersion)
        }
    }
    private fun setForceUpdateData(appVersion: AppSetting) {
        val currVersion = BuildConfig.VERSION_NAME
        val av = appVersion.appVersion
        av?.let {
            if (currVersion < it){
                showForceUpdateDialog(appVersion)
            }else{
                Handler(Looper.getMainLooper()).postDelayed({
                    LoginActivity.open(currActivity)
                    finish()
                },2000)
            }
        }
    }

}