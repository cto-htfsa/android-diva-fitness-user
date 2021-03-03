package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.auth.ui.EditProfileActivity
import com.htf.diva.auth.ui.MyProfileActivity
import com.htf.diva.auth.viewModel.ProfileViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.NotificationViewModel
import com.htf.diva.databinding.ActivityMyProfileBinding
import com.htf.diva.databinding.ActivityNotificationBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.NotificationModel
import com.htf.diva.utils.PermissionUtility
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.toolbar.*

class NotificationActivity : BaseDarkActivity<ActivityNotificationBinding, NotificationViewModel>(NotificationViewModel::class.java),
    View.OnClickListener {

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, NotificationActivity::class.java)
            currActivity.startActivity(intent)
        }
    }


    override var layout = R.layout.activity_notification
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.notification)
        binding.notificationViewModel = viewModel
        setListener()
        viewModel.notificationList()
        viewModelInitialize()
    }

    private fun setListener() {

    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mNotificationResponse,this::onHandleAppDashBoardSuccessResponse)
    }
    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }

    private fun onHandleAppDashBoardSuccessResponse(notificationModel: NotificationModel?){
        notificationModel?.let {

        }
    }

    override fun onClick(view: View?) {
        when(view!!.id){

        }
    }

}