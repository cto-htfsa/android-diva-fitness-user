package com.htf.diva.dashboard.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.R
import com.htf.diva.base.BaseActivity
import com.htf.diva.dashboard.adapters.SelectedSlotAdapter
import com.htf.diva.dashboard.adapters.SlotsAdapter
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivitySlotBookBinding
import com.htf.diva.models.Packages
import com.htf.diva.models.Slot
import com.htf.diva.models.Tenure
import com.htf.diva.models.TrainerDetailsModel
import com.htf.diva.utils.DateUtils
import com.htf.diva.utils.DateUtils.getCurrentDateC
import com.htf.diva.utils.DateUtils.getCurrentMonthC
import com.htf.diva.utils.DateUtils.getCurrentWeekDay
import com.htf.diva.utils.DateUtils.getCurrentYearC
import com.htf.diva.utils.content.DummyContent
import com.htf.diva.utils.showToast
import com.htf.diva.callBack.IListItemClickListener
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_slot_book.*
import kotlinx.android.synthetic.main.layout_selected_slots.view.*
import kotlin.collections.ArrayList

class SelectSlotsActivity : BaseActivity<ActivitySlotBookBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java
), IListItemClickListener<Any>, View.OnClickListener,
    OnDateSelectedListener {

    private var selectedDate: String=""
    private var itemSelectedSlot= Slot()
    private var selectedDay=getCurrentWeekDay()
    private var selectedDayType=0
    private var arrBookingSlots=ArrayList<Slot>()
    private var currActivity: Activity = this
    private lateinit var slotsAdapter: SlotsAdapter
    private lateinit var selctedSlotsAdapter: SelectedSlotAdapter
    private var arrSelectedSlots=ArrayList<Slot>()
    private lateinit var dialog: AlertDialog

    private var currentDate:String?=null
    private var packageSelected=Packages()
    private var tenureSelected=Tenure()
    private var trainerDetail=TrainerDetailsModel()
    private var booking_type:String?=""
    private var numberOfPeoplePerSession:String?=null
    private var withMyFriendsGym:String?=null
    private var gymBookingWith:String?=null


    companion object{
        fun open(
            currActivity: Activity,
            arrBookingSlots: ArrayList<Slot>,
            trainerDetail: TrainerDetailsModel,
            tenureSelected: Tenure,
            packageSelected: Packages,
            booking_type: String?,
            currentDate: String?,
            numberOfPeoplePerSession: String?,
            withMyFriendsGym: String?,
            gymBookingWith: String?){
            val intent= Intent(currActivity, SelectSlotsActivity::class.java)
            intent.putExtra("arrBookingSlots", arrBookingSlots)
            intent.putExtra("trainerDetail", trainerDetail)
            intent.putExtra("tenureSelected", tenureSelected)
            intent.putExtra("packageSelected", packageSelected)
            intent.putExtra("booking_type", booking_type)
            intent.putExtra("currentDate", currentDate)
            intent.putExtra("numberOfPeoplePerSession", numberOfPeoplePerSession)
            intent.putExtra("withMyFriendsGym", withMyFriendsGym)
            intent.putExtra("gymBookingWith", gymBookingWith)
            currActivity.startActivity(intent) }
    }

    override var layout = R.layout.activity_slot_book
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.slotsViewModel = viewModel
        getExtra()
        setListener()
        val cDay=CalendarDay.from(getCurrentYearC().toInt(), getCurrentMonthC().toInt(), getCurrentDateC().toInt())
        binding.calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_SINGLE
        binding.calendarView.state().edit().setMinimumDate(cDay).commit()
        binding.calendarView.selectedDate = cDay
        selectedDate=cDay.date.toString()

    }

    fun setListener(){
        calendarView.setOnDateChangedListener(this)
        viewSelectedSlots.setOnClickListener(this)
        btnConfirmSlot.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.viewSelectedSlots -> {
            if (arrSelectedSlots.isNotEmpty()){
                openDialog()
               }
            }
            R.id.btnConfirmSlot->{
                if(arrSelectedSlots.isNotEmpty()){
                    TrainerBookingSummaryActivity.open(currActivity,arrSelectedSlots,trainerDetail,
                        tenureSelected,packageSelected,booking_type,currentDate,numberOfPeoplePerSession,withMyFriendsGym,gymBookingWith)
                } else{
                    showToast(currActivity.getString(R.string.please_select_slots), true)
                }
            }
        }
    }

    private fun getExtra() {
        selectedDayType=DummyContent.getWeekDays().single { it.weekDay==selectedDay }.type
        arrBookingSlots.clear()
        arrBookingSlots.addAll(intent.getSerializableExtra("arrBookingSlots") as ArrayList<Slot>)
        trainerDetail=intent.getSerializableExtra("trainerDetail") as TrainerDetailsModel
        tenureSelected=intent.getSerializableExtra("tenureSelected") as Tenure
        packageSelected=intent.getSerializableExtra("packageSelected") as Packages
        booking_type=intent.getStringExtra("booking_type")
        currentDate=intent.getStringExtra("currentDate")
        numberOfPeoplePerSession=intent.getStringExtra("numberOfPeoplePerSession")
        withMyFriendsGym=intent.getStringExtra("withMyFriendsGym")
        gymBookingWith=intent.getStringExtra("gymBookingWith")



        val filterArrBookingSlots=arrBookingSlots.filter { it.weekdayId==selectedDayType }
        setSlotsList(filterArrBookingSlots)
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
           selectedDate=date.date.toString()
           arrBookingSlots.clear()
           val weekDay=DateUtils.convertDateFormat(selectedDate, DateUtils.serverDateFormat, DateUtils.weekdaysFormat)
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


    /* Dialog for selected slots */

    private fun openDialog() {
        val builder = AlertDialog.Builder(currActivity)
        val dialogView =
            LayoutInflater.from(currActivity).inflate(R.layout.layout_selected_slots, null)
        builder.setView(dialogView)
        dialog = builder.create()

        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.VERTICAL, false)
        dialogView.rvSelectedSlots.layoutManager = mLayout
        selctedSlotsAdapter = SelectedSlotAdapter(currActivity, arrSelectedSlots, this)
        dialogView.rvSelectedSlots.adapter = selctedSlotsAdapter


        dialogView.rlMainSlots.setOnClickListener {
            dialog.dismiss()
        }

        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    override fun onItemClickListener(data: Any) {
         if (data is Slot){
             data.date=selectedDate
             val d=arrSelectedSlots.filter { it.date==data.date}
               if (d.isNotEmpty()){
                   arrSelectedSlots.removeAll(d)
             }
             arrSelectedSlots.add(data)

         }
    }

}