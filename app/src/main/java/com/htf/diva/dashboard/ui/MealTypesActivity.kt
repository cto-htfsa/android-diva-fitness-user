package com.htf.diva.dashboard.ui
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.MealTypesAdapter
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.databinding.ActivityDietDayBinding
import com.htf.diva.models.DietWeekdayModel
import com.htf.diva.models.MealDietType
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class MealTypesActivity : BaseDarkActivity<ActivityDietDayBinding, DitPlanViewModel>(
    DitPlanViewModel::class.java), IListItemClickListener<Any>, SwipeRefreshLayout.OnRefreshListener {
    private var currActivity: Activity = this
    private lateinit var mealTypesAdapter: MealTypesAdapter


    companion object {
        fun open(currActivity: Activity, dietWeekPlan: DietWeekdayModel?) {
            val intent = Intent(currActivity, MealTypesActivity::class.java)
            intent.putExtra("dietWeekDay",dietWeekPlan)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_diet_day
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root.refresh.setOnRefreshListener(this)
        binding.dietWeekDayViewModel = viewModel
        getExtra()

    }

    private fun getExtra() {
        val dietWeekDay = intent.getSerializableExtra("dietWeekDay") as DietWeekdayModel?
        tvTitle.text = dietWeekDay!!.name
        mealTypes(dietWeekDay.mealTypes)
    }


    private fun mealTypes(arrMealTypes: ArrayList<MealDietType>?) {
        if(arrMealTypes!!.size>0){
            val mLayout= LinearLayoutManager(currActivity)
            recycler.layoutManager=mLayout
            mealTypesAdapter= MealTypesAdapter(currActivity,arrMealTypes,this)
            recycler.adapter=mealTypesAdapter
        }else{
            tvClearAll.visibility= View.GONE
            binding.root.ll_empty.visibility = View.VISIBLE
            binding.root.ivNoImage.setImageResource(R.drawable.diva_place_holder)
            binding.root.tvMsg.text=currActivity.getString(R.string.no_diet_available)
        }
    }


    override fun onItemClickListener(data: Any) {
        if (data is MealDietType){
            DietPlanActivity.open(currActivity,data)
        }
    }

    override fun onRefresh() {
        binding.root.refresh.isRefreshing = false
    }


}