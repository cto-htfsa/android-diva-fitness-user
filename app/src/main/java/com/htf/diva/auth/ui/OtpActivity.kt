package com.htf.diva.auth.ui

import `in`.aabhasjindal.otptextview.OTPListener
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.htf.diva.R
import com.htf.diva.auth.viewModel.OtpViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.databinding.ActivityOtpBinding
import com.htf.diva.utils.AppUtils
import kotlinx.android.synthetic.main.activity_otp.*


class OtpActivity : BaseDarkActivity<ActivityOtpBinding,OtpViewModel>(OtpViewModel::class.java) {
    private var currActivity: Activity =this
    val mFcmId = MutableLiveData<String>("")
    private var mHashToken:String?=null
    private var userId:String?=null


    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,OtpActivity::class.java)
          /*  intent.putExtra("hash_token",hashToken)
            intent.putExtra("userId",userId)*/
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_otp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.otpViewModel=viewModel
        startCountdownTimer()
        getFcmToken()
        getExtra()

        otp_view.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                // fired when user types something in the Otpbox
            }

            override fun onOTPComplete(otp: String) {
                // fired when user has entered the OTP fully.
               /* AboutYouActivity.open(currActivity)*/
             //   viewModel.verifyOtp(mHashToken!!,otp)

            }
        }
    }

    private fun getExtra() {
        mHashToken=intent.getStringExtra("hash_token")
        userId=intent.getStringExtra("userId")
        viewModel.mHashToken.value=mHashToken
    }



    private fun startCountdownTimer(){
        llResendOtp.visibility = View.GONE
        AppUtils.countDownTimer().observe(this, Observer {
            if (it == "-1")
                llResendOtp.visibility = View.VISIBLE
            else
                tvTimer.text = it
            println(it)
        })
    }

    private fun getFcmToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                //Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            viewModel.mFcmId.value=token
        })
    }


}