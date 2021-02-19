package com.htf.diva.auth.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.htf.diva.R
import com.htf.diva.auth.viewModel.OtpViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.databinding.ActivityOtpBinding
import kotlinx.android.synthetic.main.activity_otp.*

class OtpActivity : BaseDarkActivity<ActivityOtpBinding,OtpViewModel>(OtpViewModel::class.java) {
    private var currActivity: Activity =this

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,OtpActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_otp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.otpViewModel=viewModel
        otpView.setOtpCompletionListener { otp->
            AboutYouActivity.open(currActivity)

        }
    }
}