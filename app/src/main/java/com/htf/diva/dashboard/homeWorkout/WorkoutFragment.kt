package com.htf.diva.dashboard.homeWorkout

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseFragment
import com.htf.diva.dashboard.viewModel.WorkoutPlanViewModel
import com.htf.diva.databinding.FragmentWorkoutBinding
import com.htf.diva.models.MyWorkoutPlanModel
import com.htf.diva.models.UserWorkout
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DateUtilss.getCurrentDateC
import com.htf.diva.utils.DateUtilss.getCurrentMonthC
import com.htf.diva.utils.DateUtilss.getCurrentYearC
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.calendar_item.view.*
import kotlinx.android.synthetic.main.fragment_diet.view.lnrEdit
import kotlinx.android.synthetic.main.fragment_workout.*
import kotlinx.android.synthetic.main.fragment_workout.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WorkoutFragment : BaseFragment<WorkoutPlanViewModel>(WorkoutPlanViewModel::class.java) , View.OnClickListener{
    private var selectedDate :String?=""
    private var selectedDateCalender :String?=""
    private lateinit var currActivity: Activity
    lateinit var binding: FragmentWorkoutBinding
    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private var currentDate=0
    private lateinit var myWorkoutAdapter: MyWorkoutAdapter
    private lateinit var arrWorkout: ArrayList<UserWorkout>
    private var userWorkout =UserWorkout()
    private var workPlanData =MyWorkoutPlanModel()
    private var requestAddressCode = 101


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_workout, container, false)


        val cDay= CalendarDay.from(getCurrentYearC().toInt(), getCurrentMonthC().toInt(), getCurrentDateC().toInt())

        selectedDate=cDay.date.toString()
        binding.myWorkoutPlan = viewModel
        viewModel.myWorkoutPlan(selectedDate)
        viewModelInitialize()
        setOnClickListner()
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

                tvWorkoutDate.text = "${DateUtils.getDayNumber(date)},${DateUtils.getMonth3LettersName(date)},${DateUtils.getYear(date)}"
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
                viewModel.myWorkoutPlan(selectedDate)

                // in this example sunday and saturday can't be selected, others can
                return when (cal[Calendar.DAY_OF_WEEK]) {
                    Calendar.SATURDAY -> true
                    Calendar.SUNDAY -> true
                    else -> true
                }

            }

        }

        // here we init our calendar, also you can set more properties if you haven't specified in XML layout
        val singleRowCalendar = binding.root.main_workout_single_row_calendar.apply {
            binding.root.main_workout_single_row_calendar.
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            includeCurrentDate = true
            setDates(getFutureDatesOfCurrentMonth())
            init()
        }



        return binding.root
    }

    private fun setOnClickListner() {
        binding.root.btn_create_workout.setOnClickListener(this)
        binding.root.btnEditWorkoutPlan.setOnClickListener(this)
        binding.root.btn_create_rest_workout.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_create_workout -> {
                CreateWorkoutPlanActivity.open(currActivity, "comeFromNoWorkout")
            }

            R.id.btn_create_rest_workout -> {
                CreateWorkoutPlanActivity.open(currActivity, "comeFromNoWorkout")
            }

            R.id.btnEditWorkoutPlan -> {
                CreateWorkoutPlanActivity.open(currActivity, "comeFromEditWorkout")
            }
        }
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)
        observerViewModel(viewModel.myWorkOutPlanData, this::onHandleMyWorkoutPlanSuccessResponse)
        observerViewModel(viewModel.mUpdateCompletedWorkoutData, this::onHandleupdateCompletedWorkoutSuccessResponse)

    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error, true)
    }

    private fun onHandleMyWorkoutPlanSuccessResponse(myWorkoutPLan: MyWorkoutPlanModel?) {
        myWorkoutPLan?.let {
            if (myWorkoutPLan.isDayRest==1){
                binding.root.workoutRecycler.visibility=View.GONE
                binding.root.lnrMyWorkout.visibility=View.GONE
                binding.root.lnrNoWorkoutPlanAvailable.visibility=View.GONE
                binding.root.lnrOnRest.visibility=View.VISIBLE
            }else{
                if(myWorkoutPLan.userWorkouts!!.isNotEmpty()){
                    workPlanData=myWorkoutPLan
                    arrWorkout=myWorkoutPLan.userWorkouts!!
                    /*  lnrMyWorkoutSchedule.visibility=View.VISIBLE*/
                    binding.root.lnrOnRest.visibility=View.GONE
                    binding.root.workoutRecycler.visibility=View.VISIBLE
                    binding.root.lnrMyWorkout.visibility=View.VISIBLE
                    binding.root.lnrNoWorkoutPlanAvailable.visibility=View.GONE

                    val mLayout= LinearLayoutManager(currActivity)
                    workoutRecycler.layoutManager=mLayout
                    myWorkoutAdapter= MyWorkoutAdapter(currActivity, myWorkoutPLan.userWorkouts!!, this)
                    workoutRecycler.adapter=myWorkoutAdapter
                    binding.root.lnrEdit.visibility=View.VISIBLE
                    setDietPlanWorkout(myWorkoutPLan)

                }else{
                    binding.root.workoutRecycler.visibility=View.GONE
                    binding.root.lnrMyWorkout.visibility=View.GONE
                    binding.root.lnrNoWorkoutPlanAvailable.visibility=View.VISIBLE
                    binding.root.lnrEdit.visibility=View.GONE
                    binding.root.lnrOnRest.visibility=View.GONE

                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDietPlanWorkout(myWorkoutPLan: MyWorkoutPlanModel) {
        if (myWorkoutPLan.myScheduled!!.workouts!!.caloriesBurn!=null){
            tvBurned.text=myWorkoutPLan.myScheduled!!.workouts!!.caloriesBurn.toString()
        }
        if (myWorkoutPLan.myScheduled!!.workoutCompleted!!.caloriesBurn==null){
            val calBurn=myWorkoutPLan.myScheduled!!.workouts!!.caloriesBurn!!.toDouble()- 0
            tvCalsLeft.text=calBurn.toString()
        }else{
            val calBurn=myWorkoutPLan.myScheduled!!.workouts!!.caloriesBurn!!.toDouble()- myWorkoutPLan.myScheduled!!.workoutCompleted!!.caloriesBurn!!.toDouble()
            tvCalsLeft.text=calBurn.toString()
        }
        if (myWorkoutPLan.myScheduled!!.dietConsumed!!.calories!=null){
            tvConsumed.text=myWorkoutPLan.myScheduled!!.workoutCompleted!!.caloriesBurn.toString()
        }

        if(myWorkoutPLan.myScheduled!!.dietConsumed!!.calories!=null &&myWorkoutPLan.myScheduled!!.dietPlans!!.calories!=null){
            val progress=(myWorkoutPLan.myScheduled!!.dietConsumed!!.calories!!.toDouble())/(myWorkoutPLan.myScheduled!!.dietPlans!!.calories!!.toDouble())
            workoutProgress.progress = progress.toInt()
        }else{
            workoutProgress.progress=0
        }

        /* Diet plan and diet consumed */
        if (myWorkoutPLan.myScheduled!!.dietPlans!!.proteins!=null){
            tvPlanProtien.text=myWorkoutPLan.myScheduled!!.dietPlans!!.proteins+currActivity.getString(R.string.g)
        } else{
            tvPlanProtien.text="0"+currActivity.getString(R.string.g)
        }

        if (myWorkoutPLan.myScheduled!!.dietPlans!!.carbs!=null){
            tvPlanCabs.text=myWorkoutPLan.myScheduled!!.dietPlans!!.carbs+currActivity.getString(R.string.g)
        } else{
            tvPlanCabs.text="0"+currActivity.getString(R.string.g)
        }

        if (myWorkoutPLan.myScheduled!!.dietPlans!!.fats!=null){
            tvPlanFat.text=myWorkoutPLan.myScheduled!!.dietPlans!!.fats+currActivity.getString(R.string.g)
        } else{
            tvPlanFat.text="0"+currActivity.getString(R.string.g)
        }

        if (myWorkoutPLan.myScheduled!!.dietPlans!!.calories!=null){
            tvPlanCalories.text=myWorkoutPLan.myScheduled!!.dietPlans!!.calories+currActivity.getString(R.string.g)
        } else{
            tvPlanCalories.text="0"+currActivity.getString(R.string.g)
        }


        /* diet consumed */
        if (myWorkoutPLan.myScheduled!!.dietConsumed!!.proteins!=null){
            tvDietConsumedProtien.text=myWorkoutPLan.myScheduled!!.dietConsumed!!.proteins+currActivity.getString(R.string.g)
        } else{
            tvDietConsumedProtien.text="0"+currActivity.getString(R.string.g)
        }

        if (myWorkoutPLan.myScheduled!!.dietPlans!!.carbs!=null){
            tvDietConsumedCabs.text=myWorkoutPLan.myScheduled!!.dietConsumed!!.carbs+currActivity.getString(R.string.g)
        } else{
            tvDietConsumedCabs.text="0"+currActivity.getString(R.string.g)
        }

        if (myWorkoutPLan.myScheduled!!.dietConsumed!!.fats!=null){
            tvDietConsumedFat.text=myWorkoutPLan.myScheduled!!.dietConsumed!!.fats+currActivity.getString(R.string.g)
        } else{
            tvDietConsumedFat.text="0"+currActivity.getString(R.string.g)
        }

        if (myWorkoutPLan.myScheduled!!.dietConsumed!!.calories!=null){
            tvDietConsumedCalories.text=myWorkoutPLan.myScheduled!!.dietConsumed!!.calories+currActivity.getString(R.string.g)
        } else{
            tvDietConsumedCalories.text="0"+currActivity.getString(R.string.g)
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

    /* when user click on workout completed button*/
      fun updateCompletedWorkout(model: UserWorkout, adapterPosition: Int) {
          userWorkout=model
          viewModel.updateCompletedWorkout(AppSession.locale, AppSession.deviceId, AppSession.deviceType,
                  BuildConfig.VERSION_NAME, model.workoutId.toString())
      }


    private fun onHandleupdateCompletedWorkoutSuccessResponse(updateCompletedWorkout: Any) {
        arrWorkout.filter { it.workoutId==userWorkout.workoutId }.map { it.workoutCompleted=1 }
        binding.root.workoutRecycler.post {myWorkoutAdapter.notifyDataSetChanged()}
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            when (requestCode) {
                requestAddressCode -> {
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            //     callNewCustomizationReq()
                        }
                    }
                }

            }

        }
    }


}