package com.htf.diva.dashboard.manageSession

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.viewModel.ManageSessionViewModel
import com.htf.diva.databinding.ActivityManageSessionBinding
import com.htf.diva.models.*
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.SwipeToDeleteCallback
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_booking_successfully.*
import kotlinx.android.synthetic.main.activity_manage_session.*
import kotlinx.android.synthetic.main.toolbar.tvTitle

class ManageSessionActivity : BaseDarkActivity<ActivityManageSessionBinding, ManageSessionViewModel>(
    ManageSessionViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var arrSelectedSlots= ArrayList<Slot>()
    private var position: Int?=null
    private var currActivity: Activity = this
    private var bookingDetail=BookingDetailModel()
    private lateinit var selctedSlotsAdapter: ManageSelectedSlotAdapter
    private val REQUEST_CODE_MANAGE_SESSION = 110

    companion object{
        fun open(currActivity: Activity, bookingDetail: BookingDetailModel){
            val intent= Intent(currActivity, ManageSessionActivity::class.java)
            intent.putExtra("bookingDetail",bookingDetail)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_manage_session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.manage_session)
        binding.manageSessionViewModel = viewModel
        setOnClickListener()
        getExtra()
        viewModelInitialize()
        enableSwipeToDeleteAndUndo()
     }
    private fun setOnClickListener(){
        tvSchedule_session.setOnClickListener(this)
        reschedule_session.setOnClickListener(this)
     }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.mDeleteBookedData, this::onDeleteBookedSuccessResponse)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)

    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error, true)
    }



    private fun getExtra() {
        bookingDetail=intent.getSerializableExtra("bookingDetail") as BookingDetailModel
        if (bookingDetail.slots!!.isEmpty()){
            reschedule_session.visibility=View.GONE
            llNoSessionAvl.visibility=View.VISIBLE
            llRecyclerView_session.visibility=View.GONE
        }else{
            reschedule_session.visibility=View.VISIBLE
            llNoSessionAvl.visibility=View.GONE
            llRecyclerView_session.visibility=View.VISIBLE
            arrSelectedSlots=bookingDetail.slots!!
            selectedSlot(arrSelectedSlots)
            tvAvlSession.text=bookingDetail.baseSessions.toString()
            val remainingSession=bookingDetail.baseSessions!!-arrSelectedSlots.size
            tvRemaining_session.text=remainingSession.toString()
        }


    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tvSchedule_session->{
                ScheduleSessionActivity.open(currActivity,bookingDetail,REQUEST_CODE_MANAGE_SESSION)
            }

            R.id.reschedule_session->{
                ScheduleSessionActivity.open(currActivity,bookingDetail,REQUEST_CODE_MANAGE_SESSION)
            }

        }
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                position = viewHolder.adapterPosition
                val item = selctedSlotsAdapter.getData()[position!!]
                val weekdayId = item.weekdayId
                //callDeleteNotificationService(position, notificationID)
                viewModel.deleteBookedSlotClick(AppSession.locale,
                    AppSession.deviceId, AppSession.deviceType,BuildConfig.VERSION_NAME,weekdayId.toString())
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(llRecyclerView_session)
    }



    fun selectedSlot(slots: ArrayList<Slot>) {
        llRecyclerView_session.visibility=View.VISIBLE
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.VERTICAL, false)
        llRecyclerView_session.layoutManager = mLayout
        selctedSlotsAdapter = ManageSelectedSlotAdapter(currActivity, slots)
        llRecyclerView_session.adapter = selctedSlotsAdapter
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_MANAGE_SESSION -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                         arrSelectedSlots = data.getStringArrayListExtra("arrSelectedSlots") as ArrayList<Slot>
                         arrSelectedSlots.addAll(arrSelectedSlots)
                         selectedSlot(arrSelectedSlots)
                       /* tvRemaining_session.text= (bookingDetail.baseSessions!!.toDouble()-arrSelectedSlots.size.toDouble()).toString()*/
                    }
                }
            }
        }
    }

    private fun onDeleteBookedSuccessResponse(paymentVerifyResponse: Any?){
        paymentVerifyResponse?.let {
            selctedSlotsAdapter.removeItem(position!!)
            selctedSlotsAdapter.notifyDataSetChanged()
        }
    }

}