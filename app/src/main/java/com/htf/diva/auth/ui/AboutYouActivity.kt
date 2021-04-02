package com.htf.diva.auth.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.htf.diva.R
import com.htf.diva.auth.viewModel.AboutViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.base.MyApplication
import com.htf.diva.dashboard.ui.HomeActivity
import com.htf.diva.databinding.ActivityAboutYouBinding
import com.htf.diva.models.AboutModel
import com.htf.diva.utils.*
import com.jem.rubberpicker.RubberSeekBar
import kotlinx.android.synthetic.main.activity_about_you.*
import kotlinx.android.synthetic.main.toolbar.*

class AboutYouActivity : BaseDarkActivity<ActivityAboutYouBinding,AboutViewModel>(AboutViewModel::class.java) {
    private var currActivity: Activity =this
    private val mAboutUsViewModel by lazy { getViewModel<AboutViewModel>() }

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,AboutYouActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_about_you
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.aboutViewModel=viewModel
        tvTitle.text=getString(R.string.about_you)
        viewModelInitialize()

          if (AppSession.locale=="ar"){
              ageSeekBar.rotation = 180F
              heightSeekBar.rotation=180F
              weightSeekBar.rotation=180F
          }

        /*  this seekBar for User Age*/
        val currentValue = ageSeekBar.getCurrentValue()
        ageSeekBar.setCurrentValue(currentValue)
        ageSeekBar.setOnRubberSeekBarChangeListener(object : RubberSeekBar.OnRubberSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                tvAge.text=value.toString()
            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {

            }
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {


            }

        })


        /*
        *this seekBar for User Height*/
        val currentHeightValue = heightSeekBar.getCurrentValue()
        heightSeekBar.setCurrentValue(currentHeightValue)
        heightSeekBar.setOnRubberSeekBarChangeListener(object : RubberSeekBar.OnRubberSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                tvHeight.text=value.toString()

            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {

            }
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {


            }

        })


        /* this seekBar for User Weight*/
        val currentWeightValue = weightSeekBar.getCurrentValue()
        weightSeekBar.setCurrentValue(currentWeightValue)

        weightSeekBar.setOnRubberSeekBarChangeListener(object : RubberSeekBar.OnRubberSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                tvWeight.text=value.toString()

            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {

            }
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {


            }

        })


    }
    private fun viewModelInitialize() {
        observerViewModel(viewModel.errorValidateRes,this::onHandleValidationErrorResponse)
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.mAboutUsData,this::onHandleLoginSuccessResponse)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(mAboutUsViewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(mAboutUsViewModel.isApiCalling,this::onHandleShowProgress)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }

    private fun onHandleValidationErrorResponse(error:String) {
        DialogUtils.showSnackBar(currActivity, tvAboutUsError, error)
    }

    private fun onHandleLoginSuccessResponse(userAboutUs: AboutModel?){
        userAboutUs?.let {
            val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
            currUser!!.user!!.name=userAboutUs.name
            currUser.user!!.mobile=userAboutUs.mobile
            currUser.user!!.dialCode=userAboutUs.dialCode
            currUser.user!!.isReturner=userAboutUs.isReturner
            AppPreferences.getInstance(MyApplication.getAppContext()).saveUserDetails(currUser)
            HomeActivity.open(currActivity, null)
            finish()
        }
    }


}