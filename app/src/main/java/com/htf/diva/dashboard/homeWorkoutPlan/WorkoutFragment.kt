package com.htf.diva.dashboard.homeWorkoutPlan

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
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
import com.htf.diva.utils.DateUtilss
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.prolificinteractive.materialcalendarview.CalendarDay
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.fragment_diet.*
import kotlinx.android.synthetic.main.fragment_diet.view.lnrEdit
import kotlinx.android.synthetic.main.fragment_workout.*
import kotlinx.android.synthetic.main.fragment_workout.tvBurned
import kotlinx.android.synthetic.main.fragment_workout.tvCalsLeft
import kotlinx.android.synthetic.main.fragment_workout.tvConsumed
import kotlinx.android.synthetic.main.fragment_workout.tvDietConsumedCabs
import kotlinx.android.synthetic.main.fragment_workout.tvDietConsumedCalories
import kotlinx.android.synthetic.main.fragment_workout.tvDietConsumedFat
import kotlinx.android.synthetic.main.fragment_workout.tvDietConsumedProtien
import kotlinx.android.synthetic.main.fragment_workout.tvPlanCabs
import kotlinx.android.synthetic.main.fragment_workout.tvPlanCalories
import kotlinx.android.synthetic.main.fragment_workout.tvPlanFat
import kotlinx.android.synthetic.main.fragment_workout.tvPlanProtien
import kotlinx.android.synthetic.main.fragment_workout.view.*
import kotlinx.android.synthetic.main.fragment_workout.view.lnrMyWorkout
import kotlinx.android.synthetic.main.fragment_workout.workoutProgress
import java.util.*


class WorkoutFragment : BaseFragment<WorkoutPlanViewModel>(WorkoutPlanViewModel::class.java) , View.OnClickListener{
    private var selectedDate :String?=""
    private var selectedDateCalender :String?=""
    private lateinit var currActivity: Activity
    lateinit var binding: FragmentWorkoutBinding
    private val calendar = Calendar.getInstance()
    private lateinit var myWorkoutAdapter: MyWorkoutAdapter
    private lateinit var arrWorkout: ArrayList<UserWorkout>
    private var userWorkout =UserWorkout()
    private var workPlanData =MyWorkoutPlanModel()
    private var requestAddressCode = 101
    private var horizontalCalendar: HorizontalCalendar? = null
    private var currentDate:String?=null
    private  var startDate= Date()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_workout, container, false)


        binding.myWorkoutPlan = viewModel
       // viewModel.myWorkoutPlan(selectedDate)
        viewModelInitialize()
        setOnClickListner()

        /* start 2 months ago from now */

        /* start 2 months ago from now */
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -2)

        /* end after 2 months from now */

        /* end after 2 months from now */
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 2)

        // Default Date set to Today.

        // Default Date set to Today.
        val defaultSelectedDate = Calendar.getInstance()

        horizontalCalendar = HorizontalCalendar.Builder(binding.root, R.id.calendarView)
            .range(startDate, endDate)
            .datesNumberOnScreen(7)
            .configure()
            .formatTopText("MMM")
            .formatMiddleText("dd")
            .formatBottomText("EEE")
            .showTopText(false)
            .showBottomText(true)
            .textColor(Color.LTGRAY, Color.BLACK)
            .colorTextMiddle(Color.LTGRAY, Color.parseColor("#371257"))
            .end()
            .defaultSelectedDate(defaultSelectedDate)
            .build()

        Log.i("Default Date", DateFormat.format("EEE, MMM d, yyyy", defaultSelectedDate).toString())
        selectedDate= DateFormat.format("yyyy-MM-dd", defaultSelectedDate).toString()
        binding.root.tvWorkoutDate.text=DateFormat.format("EEE, MMM d, yyyy", defaultSelectedDate).toString()
        viewModel.myWorkoutPlan(selectedDate)

        horizontalCalendar?.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar, position: Int) {
                val selectedDateStr = DateFormat.format("EEE, MMM d, yyyy", date).toString()
                binding.root.tvWorkoutDate.text=selectedDateStr
              /*  Toast.makeText(currActivity, "$selectedDateStr selected!", Toast.LENGTH_SHORT)
                    .show()
                Log.i("onDateSelected", "$selectedDateStr - Position = $position")*/
                val selectedDateFormat = DateFormat.format("yyyy-MM-dd", date).toString()
                selectedDate=selectedDateFormat
                viewModel.myWorkoutPlan(selectedDate)

            }
        }


        return binding.root
    }

    private fun setOnClickListner() {
        binding.root.btn_create_workout.setOnClickListener(this)
        binding.root.btnEditWorkoutPlan.setOnClickListener(this)
        binding.root.btn_create_rest_workout.setOnClickListener(this)
        binding.root.ivSelectDate.setOnClickListener(this)
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


            R.id.ivSelectDate->{
                datePickerStart()
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

        if (myWorkoutPLan.myScheduled!!.dietConsumed!!.carbs!=null){
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

    /* when user click on workout completed button*/
      fun updateCompletedWorkout(model: UserWorkout, adapterPosition: Int) {
         userWorkout=model
        arrWorkout.filter { it.workoutId==model.workoutId }.map { it.workoutCompleted==1}
        val workoutId = HashMap<String, String?>()
        for (i in 0.until(arrWorkout.size)) {
            if (arrWorkout[i].workoutCompleted==1){
                workoutId["workout_id[$adapterPosition]"] =  arrWorkout[i].workoutId.toString()
            }
        }
        viewModel.updateCompletedWorkout(AppSession.locale, AppSession.deviceId, AppSession.deviceType,
            BuildConfig.VERSION_NAME, workoutId)
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

    override fun onResume() {
        super.onResume()
        viewModel.myWorkoutPlan(selectedDate)
    }

    private fun datePickerStart() {
        val currentDate1 = Calendar.getInstance()
        val date = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            currActivity,
            DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
                date.set(Calendar.MONTH, monthOfYear)
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                date.set(Calendar.YEAR, year)
                date.set(year, monthOfYear, dayOfMonth)
                startDate = date.time
                currentDate= DateUtilss.targetDateFormat.format(startDate)
                tvWorkoutDate.text = (DateUtilss.targetDateFormat.format(startDate))

            },
            currentDate1.get(Calendar.YEAR),
            currentDate1.get(Calendar.MONTH),
            currentDate1.get(Calendar.DATE)
        )
        datePickerDialog.datePicker.minDate=System.currentTimeMillis()
        datePickerDialog.show()
    }
}