package com.htf.diva.dashboard.homeDiet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.R
import com.htf.diva.base.BaseFragment
import com.htf.diva.dashboard.adapters.MyMealTypeDietAdapter
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.databinding.FragmentDietBinding
import com.htf.diva.models.MyDietModel
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DateUtilss
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_diet.*
import kotlinx.android.synthetic.main.calendar_item.view.*
import kotlinx.android.synthetic.main.fragment_diet.view.*
import kotlinx.android.synthetic.main.fragment_diet.view.lnrEdit
import java.text.SimpleDateFormat
import java.util.*


class DietFragment : BaseFragment<DitPlanViewModel>(DitPlanViewModel::class.java) ,
    View.OnClickListener, IListItemClickListener<Any> {
    private lateinit var currActivity: Activity
    lateinit var binding: FragmentDietBinding
    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private var currentDate=0
    private lateinit var myDietAdapter: MyMealTypeDietAdapter
    private var selectedDate :String?=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diet, container, false)
        setListener()

        val cDay= CalendarDay.from(DateUtilss.getCurrentYearC().toInt(), DateUtilss.getCurrentMonthC().toInt(), DateUtilss.getCurrentDateC().toInt())
        selectedDate=cDay.date.toString()

        binding.myDietPlan = viewModel
        viewModel.myDietList(selectedDate!!)
        viewModelInitialize()

        // set current date to calendar and current month to currentMonth variable
        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]
        currentDate=calendar[Calendar.DATE]

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
                if (AppSession.locale=="ar"){
                    holder.itemView.tv_date_calendar_item.text = DateUtils.getDayNumber(date)
                    holder.itemView.tv_day_calendar_item.text = DateUtils.getDay3LettersName(date)
                }else{
                    holder.itemView.tv_date_calendar_item.text = DateUtils.getDayNumber(date)
                    holder.itemView.tv_day_calendar_item.text = DateUtils.getDay3LettersName(date)
                }


            }
        }

        // using calendar changes observer we can track changes in calendar
        val myCalendarChangesObserver = object : CalendarChangesObserver {
            // you can override more methods, in this example we need only this one
            @SuppressLint("SetTextI18n")
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {

                tvDietPlanDate.text = "${DateUtils.getDayNumber(date)},${DateUtils.getMonth3LettersName(date)},${DateUtils.getYear(date)}"
                //  selectedDate="${DateUtils.getYear(date)}-${DateUtils.getMonthNumber(date)}-${DateUtils.getDayNumber(date)}"
                super.whenSelectionChanged(isSelected, position, date)

            }

        }

        // selection manager is responsible for managing selection
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                // set date to calendar according to position
                val cal = Calendar.getInstance()
                cal.time = date

                val formateDate = SimpleDateFormat("yyyy-MM-dd").format(date)
                selectedDate=formateDate
                Log.e("formateDate date ", formateDate + "")
                viewModel.myDietList(selectedDate!!)

                // in this example sunday and saturday can't be selected, others can
                return when (cal[Calendar.DAY_OF_WEEK]) {
                    Calendar.SATURDAY -> true
                    Calendar.SUNDAY -> true
                    else -> true
                }

            }

        }

        // here we init our calendar, also you can set more properties if you haven't specified in XML layout
        val singleRowCalendar = binding.root.dietPlanCalenderView.apply {
            binding.root.dietPlanCalenderView.
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            includeCurrentDate = true
            setDates(getFutureDatesOfCurrentMonth())
            init()
        }



        return binding.root

    }


    private fun setListener() {
        binding.root.btn_create_diet.setOnClickListener(this)
        binding.root.btnEditDietPlan.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_create_diet -> {
                DietWeekDaysActivity.open(currActivity,"noDietPlan")
            }
            R.id.btnEditDietPlan->{
                DietWeekDaysActivity.open(currActivity,"editDietPlan")
            }
        }
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mMyDietData, this::onHandleDietPLanSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }

    private fun onHandleDietPLanSuccessResponse(myDiet: MyDietModel?) {
        myDiet?.let {
            if(myDiet.mealTypes!!.size>0){
                btnEditDietPlan.visibility=View.VISIBLE
                lnrMySchedule.visibility=View.VISIBLE
                dietRecycler.visibility=View.VISIBLE
                lnrNoDietPlanAvailable.visibility=View.GONE
                val mLayout= LinearLayoutManager(currActivity)
                dietRecycler.layoutManager=mLayout
                myDietAdapter= MyMealTypeDietAdapter(currActivity,myDiet.mealTypes!!,this)
                dietRecycler.adapter=myDietAdapter
                binding.root.lnrEdit.visibility=View.VISIBLE

             /*   binding.root.lnrMySchedule.visibility=View.GONE
                binding.root.lnrNoDietPlanAvailable.visibility=View.VISIBLE
                binding.root.lnrEdit.visibility=View.GONE*/

            }else{
                binding.root.lnrMySchedule.visibility=View.GONE
                binding.root.lnrNoDietPlanAvailable.visibility=View.VISIBLE
                binding.root.lnrEdit.visibility=View.GONE

            }
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

    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        // get all next dates of current month
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }


    private fun getDates(list: MutableList<Date>): List<Date> {
        // load dates of whole month
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }
}