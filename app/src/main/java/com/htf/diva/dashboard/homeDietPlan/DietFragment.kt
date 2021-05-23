package com.htf.diva.dashboard.homeDietPlan

import android.annotation.SuppressLint
import android.app.Activity
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
import com.htf.diva.dashboard.adapters.MyMealTypeDietAdapter
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.databinding.FragmentDietBinding
import com.htf.diva.models.MealType
import com.htf.diva.models.MyDietModel
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.htf.diva.models.UserDietPlan
import com.htf.diva.utils.AppSession
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.fragment_diet.*
import kotlinx.android.synthetic.main.fragment_diet.lnrMyWorkout
import kotlinx.android.synthetic.main.fragment_diet.tvBurned
import kotlinx.android.synthetic.main.fragment_diet.tvCalsLeft
import kotlinx.android.synthetic.main.fragment_diet.tvConsumed
import kotlinx.android.synthetic.main.fragment_diet.tvDietConsumedCabs
import kotlinx.android.synthetic.main.fragment_diet.tvDietConsumedCalories
import kotlinx.android.synthetic.main.fragment_diet.tvDietConsumedFat
import kotlinx.android.synthetic.main.fragment_diet.tvDietConsumedProtien
import kotlinx.android.synthetic.main.fragment_diet.tvPlanCabs
import kotlinx.android.synthetic.main.fragment_diet.tvPlanCalories
import kotlinx.android.synthetic.main.fragment_diet.tvPlanFat
import kotlinx.android.synthetic.main.fragment_diet.tvPlanProtien
import kotlinx.android.synthetic.main.fragment_diet.view.*
import kotlinx.android.synthetic.main.fragment_diet.view.lnrEdit
import kotlinx.android.synthetic.main.fragment_diet.view.lnrMyWorkout
import kotlinx.android.synthetic.main.fragment_diet.workoutProgress
import kotlinx.android.synthetic.main.fragment_workout.view.*

import java.util.*


class DietFragment : BaseFragment<DitPlanViewModel>(DitPlanViewModel::class.java) ,
    View.OnClickListener{

    private lateinit var currActivity: Activity
    lateinit var binding: FragmentDietBinding
    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private var currentDate=0
    private lateinit var myDietAdapter: MyMealTypeDietAdapter
    private var selectedDate :String?=""
    private var horizontalCalendar: HorizontalCalendar? = null

    var arrMealType: MealType? = null
    var userDietPlan: UserDietPlan? = null
    var arrMealLisType: ArrayList<MealType>? = null
    var arrMealId: ArrayList<UserDietPlan>? = null
    private var userDietPlanPosition: Int?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diet, container, false)
        setListener()

        binding.myDietPlan = viewModel
   /*     viewModel.myDietList(selectedDate!!)*/
        viewModelInitialize()

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
            .datesNumberOnScreen(5)
            .configure()
            .formatTopText("MMM")
            .formatMiddleText("dd")
            .formatBottomText("EEE")
            .showTopText(true)
            .showBottomText(true)
            .textColor(Color.LTGRAY, Color.BLACK)
            .colorTextMiddle(Color.LTGRAY, Color.parseColor("#371257"))
            .end()
            .defaultSelectedDate(defaultSelectedDate)
            .build()

        Log.i("Default Date", DateFormat.format("EEE, MMM d, yyyy", defaultSelectedDate).toString())
        selectedDate= DateFormat.format("yyyy-MM-dd", defaultSelectedDate).toString()
        binding.root.tvDietPlanDate.text= DateFormat.format("EEE, MMM d, yyyy", defaultSelectedDate).toString()
        viewModel.myDietList(selectedDate!!)

        horizontalCalendar?.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar, position: Int) {
                val selectedDateStr = DateFormat.format("EEE, MMM d, yyyy", date).toString()
                binding.root.tvDietPlanDate.text=selectedDateStr
                /*  Toast.makeText(currActivity, "$selectedDateStr selected!", Toast.LENGTH_SHORT)
                      .show()
                  Log.i("onDateSelected", "$selectedDateStr - Position = $position")*/
                val selectedDateFormat = DateFormat.format("yyyy-MM-dd", date).toString()
                selectedDate=selectedDateFormat
                viewModel.myDietList(selectedDate!!)

            }
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
        observerViewModel(viewModel.mConsumedDietPlanData, this::updateConsumeDietSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }

    private fun onHandleDietPLanSuccessResponse(myDiet: MyDietModel?) {
        myDiet?.let {

            if (myDiet.myScheduled!!.dietPlans!!.calories!=null){
                if(myDiet.mealTypes!!.size>0){
                    arrMealLisType=myDiet.mealTypes!!
                    btnEditDietPlan.visibility=View.VISIBLE
                    lnrMyWorkout.visibility=View.VISIBLE
                    dietRecycler.visibility=View.VISIBLE
                    lnrNoDietPlanAvailable.visibility=View.GONE
                    val mLayout= LinearLayoutManager(currActivity)
                    dietRecycler.layoutManager=mLayout
                    myDietAdapter= MyMealTypeDietAdapter(currActivity,myDiet.mealTypes!!,this)
                    dietRecycler.adapter=myDietAdapter
                    binding.root.lnrEdit.visibility=View.VISIBLE
                    setDietPlanWorkout(myDiet)
                }else{
                    binding.root.lnrMyWorkout.visibility=View.GONE
                    binding.root.lnrNoDietPlanAvailable.visibility=View.VISIBLE
                    binding.root.lnrEdit.visibility=View.GONE
                }
            }else{
                binding.root.lnrMyWorkout.visibility=View.GONE
                binding.root.dietRecycler.visibility=View.GONE
                binding.root.lnrNoDietPlanAvailable.visibility=View.VISIBLE
                binding.root.lnrEdit.visibility=View.GONE
            }
        }
    }

    /* when user click on workout completed button*/
    fun updateConsumedWorkout(model: UserDietPlan, adapterPosition: Int, mealType: MealType, mealTypePosition: Int) {
       // arrMealType=mealType
        userDietPlan=model
        userDietPlanPosition=adapterPosition
       // arrMealId!![adapterPosition]=model
        val consumedDiet = HashMap<String, String?>()
     /*   for (i in 0.until(arrMealId!!.size)) {
            if(arrMealId!= null) {
                consumedDiet["meals[$i][meal_type_id]"] = mealType.id.toString()
                consumedDiet["meals[$i][meal_id]"] = model.id.toString()
            }
        }*/
        if(arrMealId!= null) {
            consumedDiet["meals[$mealTypePosition][meal_type_id]"] = mealType.id.toString()
            consumedDiet["meals[$adapterPosition][meal_id]"] = model.mealId.toString()
        }
        viewModel.onUpdateConsumedDietClick(
            AppSession.locale,AppSession.deviceId, AppSession.deviceType,
            BuildConfig.VERSION_NAME,consumedDiet)
    }


    override fun onResume() {
        super.onResume()
        viewModel.myDietList(selectedDate!!)
    }

    @SuppressLint("SetTextI18n")
    private fun setDietPlanWorkout(myWorkoutPLan: MyDietModel) {
        if (myWorkoutPLan.myScheduled!!.workouts!!.caloriesBurn!=null){
            tvBurned.text=myWorkoutPLan.myScheduled!!.workouts!!.caloriesBurn.toString()
        }
        if (myWorkoutPLan.myScheduled!!.workoutCompleted!!.caloriesBurn==null){
        /*    val calBurn=myWorkoutPLan.myScheduled!!.workouts!!.caloriesBurn!!.toDouble()- 0
            tvCalsLeft.text=calBurn.toString()*/
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

    private fun updateConsumeDietSuccessResponse(updateCompletedWorkout: Any) {
        arrMealLisType!!.filter { it.userDietPlans?.get(userDietPlanPosition!!)!!.id==userDietPlan!!.id }.map { it.userDietPlans!![userDietPlanPosition!!].dietConsumed=1 }
        binding.root.dietRecycler.post {myDietAdapter.notifyDataSetChanged()}
    }



}