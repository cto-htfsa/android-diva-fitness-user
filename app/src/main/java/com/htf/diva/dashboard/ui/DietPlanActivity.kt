package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.databinding.ActivityDietDayBinding
import com.htf.diva.databinding.ActivityDietPlanBinding
import com.htf.diva.models.DietWeekdayModel
import com.htf.diva.models.MealDietType
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

class DietPlanActivity: BaseDarkActivity<ActivityDietPlanBinding, DitPlanViewModel>(DitPlanViewModel::class.java) {
    private var currActivity: Activity = this

    companion object {
        fun open(currActivity: Activity, mealDietType: MealDietType?) {
            val intent = Intent(currActivity, DietPlanActivity::class.java)
            intent.putExtra("mealDietType",mealDietType)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_diet_plan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.dietWeekDayViewModel = viewModel

    }

}