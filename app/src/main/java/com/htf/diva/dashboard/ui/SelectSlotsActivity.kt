package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.R
import com.htf.diva.base.BaseActivity
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.SlotsAdapter
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivitySlotBookBinding
import com.htf.diva.models.Slot
import com.htf.diva.utils.DateUtils
import com.htf.diva.utils.DateUtils.getCurrentWeekDay
import com.htf.diva.utils.content.DummyContent
import com.htf.eyenakhr.callBack.IListItemClickListener
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_slot_book.*

class SelectSlotsActivity : BaseActivity<ActivitySlotBookBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java
), IListItemClickListener<Any>, View.OnClickListener,
    OnDateSelectedListener {

    private var selectedDay=getCurrentWeekDay()
    private var selectedDayType=0
    private var arrBookingSlots=ArrayList<Slot>()
    private var currActivity: Activity = this
    private lateinit var slotsAdapter: SlotsAdapter

    companion object{
        fun open(currActivity: Activity, arrBookingSlots: ArrayList<Slot>){
            val intent= Intent(currActivity, SelectSlotsActivity::class.java)
            intent.putExtra("arrBookingSlots", arrBookingSlots)
            currActivity.startActivity(intent) } }

    override var layout = R.layout.activity_slot_book
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.slotsViewModel = viewModel
        getExtra()
        setListener()
        binding.calendarView.currentDate

    }

    fun setListener(){
        calendarView.setOnDateChangedListener(this);
    }
    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
    private fun getExtra() {
        selectedDayType=DummyContent.getWeekDays().single { it.weekDay==selectedDay }.type
        arrBookingSlots.clear()
        arrBookingSlots.addAll(intent.getSerializableExtra("arrBookingSlots") as ArrayList<Slot>)
        val filterArrBookingSlots=arrBookingSlots.filter { it.weekdayId==selectedDayType }
        setSlotsList(filterArrBookingSlots)
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        val selectedDate=date.date.toString()
           arrBookingSlots.clear()
           val weekDay=DateUtils.convertDateFormat(selectedDate,DateUtils.serverDateFormat,DateUtils.weekdaysFormat)
           selectedDayType=DummyContent.getWeekDays().single { it.weekDay==weekDay }.type
           arrBookingSlots.addAll(intent.getSerializableExtra("arrBookingSlots") as ArrayList<Slot>)
           val filterArrBookingSlots=arrBookingSlots.filter { it.weekdayId==selectedDayType }
         setSlotsList(filterArrBookingSlots)
    }

    /* set slots recyclerview here*/
    private fun setSlotsList(arrSlots: List<Slot>) {
        val mLayout = GridLayoutManager(currActivity, 2)
        rvSlots.layoutManager = mLayout
         slotsAdapter = SlotsAdapter(currActivity, arrSlots, this)
        rvSlots.adapter = slotsAdapter

    }
}