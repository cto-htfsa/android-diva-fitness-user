package com.htf.diva.auth.ui

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.htf.diva.auth.viewModel.SplashViewModel
import com.htf.diva.R
import com.htf.diva.base.BaseActivity
import com.htf.diva.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding,SplashViewModel>(SplashViewModel::class.java) {
    private var currActivity:Activity=this

    override val layout: Int
        get() = R.layout.activity_splash
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.splashViewModel=viewModel

            Handler(Looper.getMainLooper()).postDelayed({
                LoginActivity.open(currActivity)
                finish()
            },3000)


    }
}