package com.htf.diva.dashboard.homeWorkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.DietWeekDaysAdapter
import com.htf.diva.dashboard.ui.DietWeekDaysActivity
import com.htf.diva.dashboard.ui.MealTypesActivity
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.dashboard.viewModel.WorkoutPlanViewModel
import com.htf.diva.databinding.ActivityDietWeekDaysBinding
import com.htf.diva.databinding.ActivityWorkoutPlanBinding
import com.htf.diva.models.DietWeekdayModel
import com.htf.diva.models.WorkoutWeekDaysModel
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class CreateWorkoutPlanActivity :BaseDarkActivity<ActivityWorkoutPlanBinding, WorkoutPlanViewModel>(
        WorkoutPlanViewModel::class.java),View.OnClickListener,IListItemClickListener<Any>,
        SwipeRefreshLayout.OnRefreshListener {
    private var currActivity: Activity = this
    private lateinit var workoutWeekDaysAdapter: WorkoutWeekDaysAdapter
    companion object {
        fun open(currActivity: Activity) {
            val intent = Intent(currActivity, CreateWorkoutPlanActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_workout_plan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.create_workout_plan)
        binding.root.refresh.setOnRefreshListener(this)
        binding.workoutPlanViewModel = viewModel
        setListener()
        viewModel.workoutWeekdaysList()
        viewModelInitialize()
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
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }

    private fun onHandleAppDashBoardSuccessResponse(arrList: ArrayList<WorkoutWeekDaysModel>?) {
        arrList?.let {
            if(arrList.size>0){
                val mLayout= LinearLayoutManager(currActivity)
                recycler.layoutManager=mLayout
                workoutWeekDaysAdapter= WorkoutWeekDaysAdapter(currActivity,arrList,this)
                recycler.adapter=workoutWeekDaysAdapter
            }else{
                tvClearAll.visibility=View.GONE
                binding.root.ll_empty.visibility = View.VISIBLE
                binding.root.ivNoImage.setImageResource(R.drawable.no_diet_plan)
                binding.root.tvMsg.text=currActivity.getString(R.string.no_workout_available)
            }
        }
    }

    override fun onItemClickListener(data: Any) {
      //  if (data is WorkoutWeekDaysModel)
        //    MealTypesActivity.open(currActivity,data)
    }


}