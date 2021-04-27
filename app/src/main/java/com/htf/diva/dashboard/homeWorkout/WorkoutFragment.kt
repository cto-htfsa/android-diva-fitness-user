package com.htf.diva.dashboard.homeWorkout

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.R
import com.htf.diva.base.BaseFragment
import com.htf.diva.dashboard.adapters.MyMealTypeDietAdapter
import com.htf.diva.dashboard.ui.DietWeekDaysActivity
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.dashboard.viewModel.WorkoutPlanViewModel
import com.htf.diva.databinding.FragmentDietBinding
import com.htf.diva.databinding.FragmentWorkoutBinding
import com.htf.diva.models.MyDietModel
import com.htf.diva.models.MyWorkoutPlanModel
import com.htf.diva.utils.DateUtils.getCurrentDateC
import com.htf.diva.utils.DateUtils.getCurrentMonthC
import com.htf.diva.utils.DateUtils.getCurrentYearC
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendar
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.activity_booking_successfully.*
import kotlinx.android.synthetic.main.calendar_item.view.*
import kotlinx.android.synthetic.main.fragment_diet.*
import kotlinx.android.synthetic.main.fragment_diet.lnrNoDietPlanAvailable
import kotlinx.android.synthetic.main.fragment_diet.view.*
import kotlinx.android.synthetic.main.fragment_diet.view.lnrEdit
import kotlinx.android.synthetic.main.fragment_diet.view.lnrNoDietPlanAvailable
import kotlinx.android.synthetic.main.fragment_workout.*
import kotlinx.android.synthetic.main.fragment_workout.view.*
import java.util.*
import kotlin.collections.ArrayList

class WorkoutFragment : BaseFragment<WorkoutPlanViewModel>(WorkoutPlanViewModel::class.java) ,
        View.OnClickListener {
    private lateinit var currActivity: Activity
    lateinit var binding: FragmentWorkoutBinding
    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private var currentDate=0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_workout, container, false)
        val cDay= CalendarDay.from(getCurrentYearC().toInt(), getCurrentMonthC().toInt(), getCurrentDateC()
                .toInt())
        myCalenderView()
        binding.myWorkoutPlan = viewModel
        viewModel.myWorkoutPlan()
        viewModelInitialize()
       setOnClickListner()
        binding.root.main_workout_single_row_calendar.init()

        return binding.root
    }

    private fun setOnClickListner() {
        binding.root.btn_create_workout.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_create_workout -> {
                CreateWorkoutPlanActivity.open(currActivity)

            }
        }
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.myWorkOutPlanData, this::onHandleMyWorkoutPlanSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }

    private fun onHandleMyWorkoutPlanSuccessResponse(myDiet: MyWorkoutPlanModel?) {
        myDiet?.let {
            if(myDiet.userWorkouts!!.isNotEmpty()){
              /*  lnrMyWorkoutSchedule.visibility=View.VISIBLE*/
                binding.root.workoutRecycler.visibility=View.VISIBLE
                binding.root.lnrNoWorkoutPlanAvailable.visibility=View.GONE
             /*   val mLayout= LinearLayoutManager(currActivity)
                dietRecycler.layoutManager=mLayout
                myDietAdapter= MyMealTypeDietAdapter(currActivity,myDiet.mealTypes!!,this)
                dietRecycler.adapter=myDietAdapter
                binding.root.lnrEdit.visibility=View.VISIBLE*/

                /*   binding.root.lnrMySchedule.visibility=View.GONE
                   binding.root.lnrNoDietPlanAvailable.visibility=View.VISIBLE
                   binding.root.lnrEdit.visibility=View.GONE*/

            }else{
              /*  binding.root.lnrMySchedule.visibility=View.GONE*/
                binding.root.lnrNoWorkoutPlanAvailable.visibility=View.VISIBLE
                binding.root.lnrEdit.visibility=View.GONE

            }
        }
    }


    private fun myCalenderView() {
        // calendar view manager is responsible for our displaying logic
        val myCalendarViewManager = object : CalendarViewManager {
            override fun setCalendarViewResourceId(position: Int, date: Date, isSelected: Boolean): Int {
                // set date to calendar according to position where we are
                val cal = Calendar.getInstance()
                cal.time = date
                // if item is selected we return this layout items
                // in this example. monday, wednesday and friday will have special item views and other days
                // will be using basic item view
                return if (isSelected)
                    when (cal[Calendar.DAY_OF_MONTH]) {
                        else -> R.layout.selected_calendar_item

                    }
                else
                // here we return items which are not selected
                    when (cal[Calendar.DAY_OF_MONTH]) {
                        else -> R.layout.calendar_item
                    }

                // NOTE: if we don't want to do it this way, we can simply change color of background
                // in bindDataToCalendarView method
            }

            override fun bindDataToCalendarView(
                    holder: SingleRowCalendarAdapter.CalendarViewHolder,
                    date: Date,
                    position: Int,
                    isSelected: Boolean) {

                // using this method we can bind data to calendar view
                // good practice is if all views in layout have same IDs in all item views
                holder.itemView.tv_date_calendar_item.text = DateUtils.getDayNumber(date)
                holder.itemView.tv_day_calendar_item.text = DateUtils.getDay3LettersName(date)

            }
        }

        // using calendar changes observer we can track changes in calendar
        val myCalendarChangesObserver = object :
                CalendarChangesObserver {
            // you can override more methods, in this example we need only this one
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                binding.root.tvWorkoutDate.text = "${DateUtils.getMonthName(date)},${DateUtils.getYear(date)} "
                //     tvDay.text = DateUtils.getDayName(date)
                super.whenSelectionChanged(isSelected, position, date)
            }


        }

        // selection manager is responsible for managing selection
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                // set date to calendar according to position
                val cal = Calendar.getInstance()
                cal.time = date
                // in this example sunday and saturday can't be selected, others can

                /*return when (cal[Calendar.DATE]) {
                    Calendar.SATURDAY -> true
                    Calendar.SUNDAY -> true
                    else -> true
                }*/
                return true
            }
        }

        // here we init our calendar, also you can set more properties if you haven't specified in XML layout
        val list=ArrayList<Int>()
        list.add(0)

        val singleRowCalendar = binding.root.main_workout_single_row_calendar.apply {
            binding.root.main_workout_single_row_calendar.calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            includeCurrentDate = true
            setDates(getFutureDatesOfCurrentMonth())
            //setItemsSelected(list,true)
            init()
        }
    }

    private fun getDatesOfPreviousMonth(): List<Date> {
        currentMonth-- // - because we want previous month
        if (currentMonth == -1) {
            // we will switch to december of previous year, when we reach first month of year
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] - 1)
            currentMonth = 11 // 11 == december
        }
        return getDates(mutableListOf())
    }

    private fun getDates(list: MutableList<Date>): List<Date> {
        // load dates of whole month
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, getCurrentDateC().toInt())
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }


    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        // get all next dates of current month
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }


}