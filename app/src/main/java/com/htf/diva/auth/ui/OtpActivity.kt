package com.htf.diva.auth.ui

import `in`.aabhasjindal.otptextview.OTPListener
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.htf.diva.R
import com.htf.diva.auth.viewModel.OtpViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.databinding.ActivityOtpBinding
import com.htf.diva.models.UserData
import com.htf.diva.utils.AppUtils
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_otp.*


class OtpActivity : BaseDarkActivity<ActivityOtpBinding,OtpViewModel>(OtpViewModel::class.java) {
    private var currActivity: Activity =this
    val mFcmId = MutableLiveData<String>("")
    private var mHashToken:String?=null
    private var userId:String?=null
    private var fcmId:String?=null


    companion object{
        fun open(currActivity: Activity, userId: String?,token: String?,fcmId:String?){
            val intent= Intent(currActivity,OtpActivity::class.java)
            intent.putExtra("userId",userId)
            intent.putExtra("token",token)
            intent.putExtra("fcmId",fcmId)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_otp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.otpViewModel=viewModel
        startCountdownTimer()
       // getFcmToken()
        getExtra()
        viewModelInitialize()

        otp_view.otpListener = object : OTPListener {
            override fun onInteractionListener() {
            }
            override fun onOTPComplete(otp: String) {
                // fired when user has entered the OTP fully.
                viewModel.verifyOtp(mHashToken!!,otp,userId)
            }
        }
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mVerifyOtpData,this::onHandleVerifyOtpSuccessResponse)
        observerViewModel(viewModel.mResendOtpData,this::onHandleResendOtpSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }
    private fun onHandleVerifyOtpSuccessResponse(resendOtp: UserData?){
        resendOtp?.let {
            AboutYouActivity.open(currActivity)
            finish()
        }
    }

    private fun onHandleResendOtpSuccessResponse(res:Any?){
        startCountdownTimer()
    }

    private fun getExtra() {
        mHashToken=intent.getStringExtra("token")
        userId=intent.getStringExtra("userId")
        fcmId=intent.getStringExtra("fcmId")
        viewModel.mHashToken.value=mHashToken
        viewModel.mUserId.value=userId
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