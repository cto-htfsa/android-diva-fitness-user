package com.htf.diva.auth.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.content.FileProvider
import com.htf.diva.R
import com.htf.diva.auth.viewModel.EditProfileViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.databinding.ActivityEditProfileBinding
import com.htf.diva.models.AboutModel
import com.htf.diva.utils.*
import com.jem.rubberpicker.RubberSeekBar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_about_you.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_my_profile.view.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : BaseDarkActivity<ActivityEditProfileBinding, EditProfileViewModel>(EditProfileViewModel::class.java) {

    private var currActivity: Activity =this
    private val mEditProfileViewModel by lazy { getViewModel<EditProfileViewModel>() }

    companion object{
        private val REQUEST_CODE_UPDATE_PROFILE=1111
        fun open(currActivity: Activity, userProfile: AboutModel){
            val intent= Intent(currActivity,EditProfileActivity::class.java)
            intent.putExtra("userProfile",userProfile)
            currActivity.startActivityForResult(intent, REQUEST_CODE_UPDATE_PROFILE)
        }
    }

    override var layout = R.layout.activity_edit_profile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.editProfileViewModel = viewModel
        tvTitle.text = getString(R.string.edit_profile)
        getExtra()
        viewModelInitialize()

        if (AppSession.locale=="ar"){
            editageSeekBar.rotation = 180F
            editheightSeekBar.rotation=180F
            editweightSeekBar.rotation=180F
        }

    }



    private fun getExtra() {
       val userProfile=intent.getSerializableExtra("userProfile") as AboutModel
        AppUtils.setText(etName, userProfile.name, "")

        /* Here the code for edit user Age*/

        editageSeekBar.setCurrentValue(userProfile.age!!.toInt())
        editheightSeekBar.setCurrentValue(userProfile.height!!.toInt())
        editweightSeekBar.setCurrentValue(userProfile.weight!!.toInt())
        editageSeekBar.setOnRubberSeekBarChangeListener(object : RubberSeekBar.OnRubberSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                tvEditAge.text=value.toString()
            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {
            }
        })


        /*
         *this seekBar for User Height*/
        editheightSeekBar.setOnRubberSeekBarChangeListener(object : RubberSeekBar.OnRubberSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                tvEditHeight.text=value.toString()
            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {
            }
        })

        /* here code for set weight */
        editweightSeekBar.setOnRubberSeekBarChangeListener(object : RubberSeekBar.OnRubberSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                tvEditWeight.text=value.toString()
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
        observerViewModel(mEditProfileViewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(mEditProfileViewModel.isApiCalling,this::onHandleShowProgress)
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
            val returnIntent = Intent()
            returnIntent.putExtra("userProfileData", userAboutUs)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }




}