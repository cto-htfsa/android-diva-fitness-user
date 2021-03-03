package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.NotificationAdapter
import com.htf.diva.dashboard.viewModel.NotificationViewModel
import com.htf.diva.databinding.ActivityNotificationBinding
import com.htf.diva.models.Notifications
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class NotificationActivity : BaseDarkActivity<ActivityNotificationBinding, NotificationViewModel>(NotificationViewModel::class.java), View.OnClickListener {
    private var currActivity: Activity = this
    private lateinit var notificationAdapter: NotificationAdapter


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
        tvClearAll.visibility=View.VISIBLE
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
        observerViewModel(viewModel.mNotificationResponse, this::onHandleAppDashBoardSuccessResponse)

    }
    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }

    private fun onHandleAppDashBoardSuccessResponse(arrList: ArrayList<Notifications>?) {
        arrList?.let {
                if(arrList.size>0){
                    val mLayout= LinearLayoutManager(currActivity)
                    recycler.layoutManager=mLayout
                    notificationAdapter= NotificationAdapter(currActivity,arrList)
                    recycler.adapter=notificationAdapter
                }else{
                    tvClearAll.visibility=View.GONE
                    binding.root.ll_empty.visibility = View.VISIBLE
                    binding.root.ivNoImage.setImageResource(R.drawable.notification_2)
                    binding.root.tvMsg.text=currActivity.getString(R.string.no_notification_found)
                }
        }
      }

    override fun onClick(view: View?) {
        when(view!!.id){

        }
    }

}