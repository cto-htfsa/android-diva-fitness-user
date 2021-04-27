package com.htf.diva.dashboard.homeWorkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.viewModel.WorkoutPlanViewModel
import com.htf.diva.databinding.ActivityWorkoutDayBinding
import com.htf.diva.models.DietWeekdayModel
import com.htf.diva.models.WorkoutWeekDaysModel
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class WorkoutDayActivity : BaseDarkActivity<ActivityWorkoutDayBinding, WorkoutPlanViewModel>(
        WorkoutPlanViewModel::class.java), View.OnClickListener, IListItemClickListener<Any>,
        SwipeRefreshLayout.OnRefreshListener {

    private var currActivity: Activity = this
/*    private lateinit var workoutWeekDaysAdapter: WorkoutWeekDaysAdapter*/

    companion object {
        fun open(currActivity: Activity, workoutModel: WorkoutWeekDaysModel) {
            val intent = Intent(currActivity, WorkoutDayActivity::class.java)
            intent.putExtra("workoutWeekDay",workoutModel)
            currActivity.startActivity(intent)
        }
    }


    override var layout = R.layout.activity_workout_day
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.create_workout_plan)
        binding.root.refresh.setOnRefreshListener(this)
        binding.dayWorkoutPlanViewModel = viewModel
       /* setListener()
        viewModelInitialize()*/
         getExtra()

    }

    private fun getExtra() {
        val workoutWeekDay = intent.getSerializableExtra("workoutWeekDay") as WorkoutWeekDaysModel?
        tvTitle.text = workoutWeekDay!!.name
        /*workoutWeekDayName= workoutWeekDay.name
        workout(workoutWeekDay.workouts)*/
    }


    override fun onRefresh() {
        binding.root.refresh.isRefreshing = false
    }

    private fun setListener() {

    }

    override fun onClick(p0: View?) {

    }



}