package com.htf.diva.dashboard.manageSession

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivityScheduleSessionBinding
import com.htf.diva.models.*
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DateUtilss
import com.htf.diva.utils.content.DummyContent
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_schedule_session.*
import kotlinx.android.synthetic.main.activity_schedule_session.calendarView
import kotlinx.android.synthetic.main.activity_schedule_session.rvSlots

class ScheduleSessionActivity  : BaseActivity<ActivityScheduleSessionBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener,
    OnDateSelectedListener {
    private var selectedDate: String=""
    private var selectedDayType=0
    private var bookingDetail=BookingDetailModel()
    private var arrBookingSlots=ArrayList<Slot>()
    private var selectedDay= DateUtilss.getCurrentWeekDay()
    private var currActivity: Activity = this
    private lateinit var slotsAdapter: ManageSessionSlotsAdapter
    private var arrSelectedSlots=ArrayList<Slot>()


    companion object{
        fun open(
            currActivity: Activity,
            bookingDetail: BookingDetailModel,
            REQUEST_CODE_MANAGE_SESSION: Int
        ){
            val intent= Intent(currActivity, ScheduleSessionActivity::class.java)
            intent.putExtra("bookingDetail",bookingDetail)
            currActivity.startActivityForResult(intent,REQUEST_CODE_MANAGE_SESSION)
        }
    }

    override var layout = R.layout.activity_schedule_session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.slotsViewModel = viewModel
        getExtra()
        setListener()
        val cDay= CalendarDay.from(DateUtilss.getCurrentYearC().toInt(), DateUtilss.getCurrentMonthC().toInt(), DateUtilss.getCurrentDateC()
            .toInt())
        calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_SINGLE
        calendarView.state().edit().setMinimumDate(cDay).commit()
        calendarView.selectedDate = cDay
        selectedDate=cDay.date.toString()

        viewModel.manageSessionSlots(
            AppSession.locale, AppSession.deviceId,
            AppSession.deviceType, BuildConfig.VERSION_NAME,bookingDetail.trainerId)

        viewModelInitialize()

    }

    private fun getExtra() {
        bookingDetail=intent.getSerializableExtra("bookingDetail") as BookingDetailModel
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mManageSessionResponse, this::onHandleAppManageSessionResponse)
        observerViewModel(viewModel.mBookSlotResponse, this::onHandleAppBookSlotResponse)

    }
    private fun onHandleAppManageSessionResponse(manageSession: ManageSessionModel?) {
        manageSession?.let {
            selectedDayType=DummyContent.getWeekDays().single { it.weekDay==selectedDay }.type
            arrBookingSlots.clear()
            arrBookingSlots= manageSession.slots!!
            val filterArrBookingSlots=arrBookingSlots.filter { it.weekdayId==selectedDayType }
            setSlotsList(filterArrBookingSlots)
          }
    }

    private fun onHandleAppBookSlotResponse(bookSessionSlot: BookSessionSlotModel?) {
        bookSessionSlot?.let {
            val intent=Intent()
            intent.putExtra("arrSelectedSlots",arrSelectedSlots)
            currActivity.setResult(Activity.RESULT_OK, intent)
            currActivity.finish()
          }
    }

    /* set slots recyclerview here*/
    private fun setSlotsList(arrSlots: List<Slot>) {
        val mLayout = GridLayoutManager(currActivity, 2)
        rvSlots.layoutManager = mLayout
        slotsAdapter = ManageSessionSlotsAdapter(currActivity, arrSlots, this)
        rvSlots.adapter = slotsAdapter
    }



    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error, true)
    }


    fun setListener(){
        calendarView.setOnDateChangedListener(this)
        btnConfirmSessionSlot.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
        R.id.btnConfirmSessionSlot->{
            if(arrSelectedSlots.isNotEmpty()){
                viewModel.bookSessionApiCall(AppSession.locale, AppSession.deviceId,
                    AppSession.deviceType, BuildConfig.VERSION_NAME,bookingDetail.trainerId,bookingDetail.id,selectedDate,
                    arrSelectedSlots[0].startAt,arrSelectedSlots[0].endAt)
            }else{
                showToast(currActivity.getString(R.string.please_select_slots), true)
            }
           }
        }
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        arrBookingSlots.clear()
        viewModel.manageSessionSlots(AppSession.locale, AppSession.deviceId,
            AppSession.deviceType, BuildConfig.VERSION_NAME,bookingDetail.trainerId)
        selectedDate=date.date.toString()
        val weekDay=DateUtilss.convertDateFormat(selectedDate, DateUtilss.serverDateFormat, DateUtilss.weekdaysFormat)
        selectedDayType= DummyContent.getWeekDays().single { it.weekDay==weekDay }.type
        arrBookingSlots.addAll(arrBookingSlots)
        val filterArrBookingSlots=arrBookingSlots.filter { it.weekdayId==selectedDayType }
        setSlotsList(filterArrBookingSlots)
    }


    override fun onItemClickListener(data: Any) {
        if (data is Slot){
            data.date=selectedDate
            val d=arrSelectedSlots.filter {it.date==data.date}
            if (d.isNotEmpty()){
                arrSelectedSlots.removeAll(d)
            }
            arrSelectedSlots.add(data)

        }
    }


}