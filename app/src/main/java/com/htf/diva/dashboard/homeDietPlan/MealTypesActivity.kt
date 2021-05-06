package com.htf.diva.dashboard.homeDietPlan
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
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.models.DietPlan
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class MealTypesActivity : BaseDarkActivity<ActivityDietDayBinding, DitPlanViewModel>(
    DitPlanViewModel::class.java), IListItemClickListener<Any>, SwipeRefreshLayout.OnRefreshListener {
    private var currActivity: Activity = this
    private lateinit var mealTypesAdapter: MealTypesAdapter
    private var weekDayName: String? = null
    private var weekDayId: String? = null
    private var mealDietType = MealDietType()
    private var arrMealDietTypes: ArrayList<MealDietType>?=null

    companion object {
        fun open(currActivity: Activity, dietWeekPlan: DietWeekdayModel?) {
            val intent = Intent(currActivity, MealTypesActivity::class.java)
            intent.putExtra("dietWeekDay", dietWeekPlan)
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
        weekDayName = dietWeekDay.name
        weekDayId = dietWeekDay.id.toString()
        arrMealDietTypes=dietWeekDay.mealTypes
        mealTypes(arrMealDietTypes)
    }


    private fun mealTypes(arrMealTypes: ArrayList<MealDietType>?) {
        if (arrMealTypes!!.size > 0) {
            val mLayout = LinearLayoutManager(currActivity)
            recycler.layoutManager = mLayout
            mealTypesAdapter = MealTypesAdapter(currActivity, arrMealTypes, this)
            recycler.adapter = mealTypesAdapter
        } else {
            tvClearAll.visibility = View.GONE
            binding.root.ll_empty.visibility = View.VISIBLE
            binding.root.ivNoImage.setImageResource(R.drawable.diva_place_holder)
            binding.root.tvMsg.text = currActivity.getString(R.string.no_diet_available)
        }
    }


    override fun onItemClickListener(data: Any) {
        if (data is MealDietType) {
            DietPlanActivity.open(currActivity, data, weekDayName, weekDayId)
        }
    }

    override fun onRefresh() {
        binding.root.refresh.isRefreshing = false
    }

      override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            DietPlanActivity.DIET_REQUEST_CODE->{
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        if (data != null) {
                            mealDietType = data.getSerializableExtra("mealDietType") as MealDietType
                            if(mealDietType!==null){
                                for (i in 0.until(arrMealDietTypes!!.size)) {
                                    if (mealDietType.id==arrMealDietTypes!![i].id){
                                        arrMealDietTypes!![i].dietPlans=mealDietType.dietPlans
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}