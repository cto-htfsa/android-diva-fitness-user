package com.htf.diva.dashboard.homeDiet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.DietPlanAdapter
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.databinding.ActivityDietPlanBinding
import com.htf.diva.models.DietPlan
import com.htf.diva.models.MealDietType
import com.htf.diva.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class DietPlanActivity: BaseDarkActivity<ActivityDietPlanBinding,
        DitPlanViewModel>(DitPlanViewModel::class.java), IListItemClickListener<Any>,SwipeRefreshLayout.OnRefreshListener{

    private var currActivity: Activity = this
    private var weekDayName:String?=null
    private lateinit var dietPlanAdapter: DietPlanAdapter

    companion object {
        fun open(currActivity: Activity, mealDietType: MealDietType?, weekDayName: String?) {
            val intent = Intent(currActivity, DietPlanActivity::class.java)
            intent.putExtra("mealDietType",mealDietType)
            intent.putExtra("weekDayName",weekDayName)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_diet_plan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.dietWeekDayViewModel = viewModel
        refresh.setOnRefreshListener(this)
        getExtra()
    }

    private fun getExtra() {
        val dietWeekDay = intent.getSerializableExtra("mealDietType") as MealDietType?
        weekDayName=intent.getStringExtra("weekDayName")
        tvTitle.text = weekDayName+" - "+dietWeekDay!!.name
        setDietPlan(dietWeekDay.dietPlans)
    }


    private fun setDietPlan(arrDietPlan: ArrayList<DietPlan>?) {
        if(arrDietPlan!!.size>0){
            val mLayout= LinearLayoutManager(currActivity)
            recycler.layoutManager=mLayout
             dietPlanAdapter= DietPlanAdapter(currActivity,arrDietPlan,this)
            recycler.adapter=dietPlanAdapter
        }else{
            tvClearAll.visibility= View.GONE
            binding.root.ll_empty.visibility = View.VISIBLE
            binding.root.ivNoImage.setImageResource(R.drawable.diva_place_holder)
            binding.root.tvMsg.text=currActivity.getString(R.string.no_diet_available)
        }
    }

    override fun onItemClickListener(data: Any) {
        if (data is DietPlan){
        }
    }

    override fun onRefresh() {
       refresh.isRefreshing = false
    }


}