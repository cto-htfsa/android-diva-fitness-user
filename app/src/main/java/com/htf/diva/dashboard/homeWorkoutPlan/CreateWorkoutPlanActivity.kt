package com.htf.diva.dashboard.homeWorkoutPlan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.viewModel.WorkoutPlanViewModel
import com.htf.diva.databinding.ActivityWorkoutPlanBinding
import com.htf.diva.models.*
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class CreateWorkoutPlanActivity :BaseDarkActivity<ActivityWorkoutPlanBinding, WorkoutPlanViewModel>(
        WorkoutPlanViewModel::class.java),View.OnClickListener,IListItemClickListener<Any>,
        SwipeRefreshLayout.OnRefreshListener {
    private lateinit var arrWorkoutWeekdays: ArrayList<WorkoutWeekDaysModel>
    private var currActivity: Activity = this
    private lateinit var workoutWeekDaysAdapter: WorkoutWeekDaysAdapter
    private var comeFrom:String?=null


    companion object {
        fun open(currActivity: Activity, comeFrom: String) {
            val intent = Intent(currActivity, CreateWorkoutPlanActivity::class.java)
            intent.putExtra("comeFrom",comeFrom)
            currActivity.startActivity(intent)
        }
    }

    private var markRestModel =WorkoutWeekDaysModel()
    private var onRestModel =WorkoutWeekDaysModel()

    override var layout = R.layout.activity_workout_plan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getExtra()
        binding.root.refresh.setOnRefreshListener(this)
        binding.workoutPlanViewModel = viewModel
        setListener()
        viewModel.workoutWeekdaysList()
        viewModelInitialize()
    }

    private fun getExtra() {
        comeFrom = intent.getStringExtra("comeFrom")
        if (comeFrom=="comeFromNoWorkout"){
            tvTitle.text=getString(R.string.create_workout_plan)
        }else{
            tvTitle.text=getString(R.string.edit_workout_plan)
        }
    }




    override fun onRefresh() {
        binding.root.refresh.isRefreshing = false
    }

    private fun setListener() {

    }

    override fun onClick(p0: View?) {

    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mWorkoutWeekDaysResponse, this::onHandleAppDashBoardSuccessResponse)
        observerViewModel(viewModel.mMarkDayRestData, this::onHandleMarkRestDaySuccessResponse)
        observerViewModel(viewModel.mRemoveDayRestData, this::onHandleRemoveRestDaySuccessResponse)
    }



    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }

    private fun onHandleAppDashBoardSuccessResponse(arrList: ArrayList<WorkoutWeekDaysModel>?) {
        arrList?.let {
            arrWorkoutWeekdays=arrList
            if(arrList.size>0){
                val mLayout= LinearLayoutManager(currActivity)
                recycler.layoutManager=mLayout
                workoutWeekDaysAdapter= WorkoutWeekDaysAdapter(currActivity,arrList,this,comeFrom)
                recycler.adapter=workoutWeekDaysAdapter
            }else{
                tvClearAll.visibility=View.GONE
                binding.root.ll_empty.visibility = View.VISIBLE
                binding.root.ivNoImage.setImageResource(R.drawable.no_diet_plan)
                binding.root.tvMsg.text=currActivity.getString(R.string.no_workout_available)
            }
        }
    }


       fun selectDayRest(model: WorkoutWeekDaysModel, adapterPosition: Int) {
          markRestModel=model
          viewModel.markDayRestClick(AppSession.locale, AppSession.deviceId, AppSession.deviceType,
                BuildConfig.VERSION_NAME, model.id.toString())
       }


        fun selectOnDayRest(model: WorkoutWeekDaysModel, adapterPosition: Int) {
            onRestModel=model
            viewModel.removeDayRestClick(AppSession.locale, AppSession.deviceId, AppSession.deviceType,
                    BuildConfig.VERSION_NAME, model.id.toString())
        }


        private fun onHandleMarkRestDaySuccessResponse(markRestDay :Any) {
            arrWorkoutWeekdays.filter { it.id==markRestModel.id }.map { it.isDayRest=1 }
            recycler.post{workoutWeekDaysAdapter.notifyDataSetChanged()}
        }

       private fun onHandleRemoveRestDaySuccessResponse(removeRestDay :Any) {
             arrWorkoutWeekdays.filter { it.id==onRestModel.id }.map { it.isDayRest=0 }
             recycler.post {workoutWeekDaysAdapter.notifyDataSetChanged()}
        }



    override fun onItemClickListener(data: Any) {
      //  if (data is WorkoutWeekDaysModel)
        //    MealTypesActivity.open(currActivity,data)
    }


}