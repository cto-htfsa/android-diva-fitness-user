package com.htf.diva.dashboard.homeWorkoutPlan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.WorkoutDaysAdapter
import com.htf.diva.dashboard.homeDietPlan.DietPlanActivity
import com.htf.diva.dashboard.viewModel.WorkoutPlanViewModel
import com.htf.diva.databinding.ActivityWorkoutDayBinding
import com.htf.diva.models.MealDietType
import com.htf.diva.models.UserWorkouts
import com.htf.diva.models.Workout
import com.htf.diva.models.WorkoutWeekDaysModel
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_workout_day.*
import kotlinx.android.synthetic.main.activity_workout_day.view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class WorkoutDayActivity : BaseDarkActivity<ActivityWorkoutDayBinding, WorkoutPlanViewModel>(
        WorkoutPlanViewModel::class.java), View.OnClickListener, IListItemClickListener<Any>{

    private var currActivity: Activity = this
   private lateinit var mWorkoutDaysAdapter: WorkoutDaysAdapter
    var arrayWorkoutList: ArrayList<Workout>? = null
    private var weekDayId :Int?=null
    private var comeFrom:String?=null
    private var workoutWeekDayNew = WorkoutWeekDaysModel()

    companion object {
        val WORkOUT_REQUEST_CODE = 1234
        fun open(currActivity: Activity, workoutModel: WorkoutWeekDaysModel, comeFrom: String?) {
            val intent = Intent(currActivity, WorkoutDayActivity::class.java)
            intent.putExtra("workoutWeekDay",workoutModel)
            intent.putExtra("comeFrom",comeFrom)
            currActivity.startActivityForResult(intent, WORkOUT_REQUEST_CODE)
        }
    }


    override var layout = R.layout.activity_workout_day
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.create_workout_plan)
        binding.dayWorkoutPlanViewModel = viewModel
        setListener()
        getExtra()
        viewModelInitialize()
    }




    private fun getExtra() {
        val workoutWeekDay = intent.getSerializableExtra("workoutWeekDay") as WorkoutWeekDaysModel?
        comeFrom = intent.getStringExtra("comeFrom")
        workoutWeekDayNew=workoutWeekDay!!
        if(workoutWeekDay!=null){
            arrayWorkoutList=workoutWeekDay.workouts
            weekDayId=workoutWeekDay.id
            tvTitle.text = workoutWeekDay.name
            workout()
        }
    }


    private fun workout() {
        if(arrayWorkoutList!!.size>0){
            val mLayout= LinearLayoutManager(currActivity)
            binding.root.recycler_workout_day.layoutManager=mLayout
            mWorkoutDaysAdapter= WorkoutDaysAdapter(currActivity,arrayWorkoutList!!,this,comeFrom)
            binding.root.recycler_workout_day.adapter=mWorkoutDaysAdapter
        }else{
            tvClearAll.visibility= View.GONE
            binding.root.ll_empty.visibility = View.VISIBLE
            binding.root.ivNoImage.setImageResource(R.drawable.diva_place_holder)
            binding.root.tvMsg.text=currActivity.getString(R.string.no_diet_available)
        }
    }

    fun selectedReps(selectedRepetitionsQty: Int, adapterPosition: Int, cartItemPosition: Int, workoutModel: UserWorkouts) {
   /*     arrayWorkoutList!!.filter { it.id==workoutModel.id}.map { it.repetitions=selectedRepetitionsQty}*/
        arrayWorkoutList!![cartItemPosition].userWorkouts!!.repetitions= selectedRepetitionsQty
        workout()
        mWorkoutDaysAdapter.notifyItemChanged(cartItemPosition)

    }

    fun selectedWorkoutSet(selectedSetsQty: Int, adapterPosition: Int, cartItemPosition: Int, workoutModel: UserWorkouts) {
      //   arrayWorkoutList!!.filter { it.id==workoutModel.id}.map { it.sets=selectedRepetitionsQty}
         arrayWorkoutList!![cartItemPosition].userWorkouts!!.sets= selectedSetsQty
         workout()
         mWorkoutDaysAdapter.notifyItemChanged(cartItemPosition)
    }


    override fun onItemClickListener(data: Any) {
       /* if (data is MealDietType){
            DietPlanActivity.open(currActivity,data,weekDayName)
        }*/
    }


    private fun setListener() {
        btnSaveDayWorkout.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnSaveDayWorkout -> {
                workoutWeekDayNew.workouts=arrayWorkoutList!!
                val slots = HashMap<String, String?>()
                for (i in 0.until(arrayWorkoutList!!.size)) {
                    if(arrayWorkoutList!![i].repetitions != null) {
                        slots["workouts[$i][workout_id]"] = arrayWorkoutList!![i].id.toString()
                        slots["workouts[$i][repetitions]"] = arrayWorkoutList!![i].userWorkouts!!.repetitions.toString()
                        slots["workouts[$i][sets]"] = arrayWorkoutList!![i].userWorkouts!!.sets.toString()
                    }
                }
                viewModel.onUpdateWorkoutClick(AppSession.locale, AppSession.deviceId, AppSession.deviceType,
                    BuildConfig.VERSION_NAME, weekDayId.toString(),slots)

            }
        }

    }



    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.mWorkoutWeekDaysData, this::onHandleBookTrainerCenterSuccessResponse)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)

    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error, true)
    }

    private fun onHandleBookTrainerCenterSuccessResponse(workoutWeekDay: Any?){
        workoutWeekDay?.let {
            val returnIntent = Intent()
            returnIntent.putExtra("workoutWeekDayNew", workoutWeekDayNew)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()

        }
    }



}