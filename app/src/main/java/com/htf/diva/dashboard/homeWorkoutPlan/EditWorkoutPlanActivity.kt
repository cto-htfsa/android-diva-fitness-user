package com.htf.diva.dashboard.homeWorkoutPlan

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.viewModel.WorkoutPlanViewModel
import com.htf.diva.databinding.ActivityWorkoutPlanBinding

class EditWorkoutPlanActivity : BaseDarkActivity<ActivityWorkoutPlanBinding, WorkoutPlanViewModel>(
        WorkoutPlanViewModel::class.java), View.OnClickListener, IListItemClickListener<Any>,
        SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun open(currActivity: Activity) {
            val intent = Intent(currActivity, EditWorkoutPlanActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    private fun setOnClickListner() {

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {


        }
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }

}