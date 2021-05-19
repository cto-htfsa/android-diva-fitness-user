package com.htf.diva.dashboard.homeDietPlan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.DietPlanAdapter
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.databinding.ActivityDietPlanBinding
import com.htf.diva.models.DietPlan
import com.htf.diva.models.MealDietType
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.models.UserDietPlans
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_diet_plan.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class DietPlanActivity: BaseDarkActivity<ActivityDietPlanBinding,
        DitPlanViewModel>(DitPlanViewModel::class.java),
    IListItemClickListener<Any>,SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{

    private var currActivity: Activity = this
    private var weekDayName:String?=null
    private var weekDayId:String?=null
    private lateinit var dietPlanAdapter: DietPlanAdapter
    private var dietPlan : DietPlan?=null
    private var mealDietTypeNew = MealDietType()
    var arrDietPlanList: ArrayList<DietPlan>? = null
    private var mealTypeId :Int?=null

    companion object {
        val DIET_REQUEST_CODE = 1234
        fun open(currActivity: Activity, mealDietType: MealDietType?, weekDayName: String?, weekDayId: String?) {
            val intent = Intent(currActivity, DietPlanActivity::class.java)
            intent.putExtra("mealDietType",mealDietType)
            intent.putExtra("weekDayName",weekDayName)
            intent.putExtra("weekDayId",weekDayId)
            currActivity.startActivityForResult(intent, DIET_REQUEST_CODE)
        }
    }

    override var layout = R.layout.activity_diet_plan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.dietWeekDayViewModel = viewModel
        refresh.setOnRefreshListener(this)
        setListener()
        getExtra()
    }

    private fun getExtra() {
        val dietWeekDay = intent.getSerializableExtra("mealDietType") as MealDietType?
        mealDietTypeNew=dietWeekDay!!
        weekDayName=intent.getStringExtra("weekDayName")
        weekDayId=intent.getStringExtra("weekDayId")
        mealTypeId= dietWeekDay.id
        tvTitle.text = weekDayName+" - "+dietWeekDay!!.name

        setDietPlan(dietWeekDay.dietPlans)
        viewModelInitialize()

    }


    private fun setDietPlan(arrDietPlanLists:ArrayList<DietPlan>?) {
        if(arrDietPlanLists!!.size>0){
            arrDietPlanList=ArrayList()
            arrDietPlanList?.addAll(arrDietPlanLists)
            val mLayout= LinearLayoutManager(currActivity)
            recycler.layoutManager=mLayout
            dietPlanAdapter= DietPlanAdapter(currActivity,arrDietPlanList!!,this)
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
    private fun setListener() {
        btnSaveDietPlan.setOnClickListener(this)
    }

    override fun onRefresh() {
        refresh.isRefreshing = false
    }

    fun dietPlanSelect(model: DietPlan, adapterPosition: Int) {
        arrDietPlanList!![adapterPosition].userDietPlans!!.quantity=1
        dietPlanAdapter.notifyDataSetChanged()
    }

    fun dietPlanAddQty(model: DietPlan, adapterPosition: Int) {
        arrDietPlanList!![adapterPosition].userDietPlans!!.quantity= model.userDietPlans!!.quantity!!+1
        dietPlanAdapter.notifyDataSetChanged()
    }

    fun dietPlanRemoveQty(model: DietPlan, adapterPosition: Int) {
        dietPlan=model
        arrDietPlanList!![adapterPosition].userDietPlans!!.quantity=model.userDietPlans!!.quantity!!-1
        dietPlanAdapter.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnSaveDietPlan -> {
                if (arrDietPlanList!!.isNotEmpty()){
                    mealDietTypeNew.dietPlans=arrDietPlanList!!
                    val dietPlan = HashMap<String, String?>()
                    for (i in 0.until(arrDietPlanList!!.size)) {
                        if(arrDietPlanList!![i].userDietPlans!= null) {
                            dietPlan["meals[$i][meal_id]"] = arrDietPlanList!![i].mealId.toString()
                            dietPlan["meals[$i][quantity]"] = arrDietPlanList!![i].userDietPlans!!.quantity.toString()
                        }
                    }
                    viewModel.onUpdateDietPlanClick(AppSession.locale, AppSession.deviceId, AppSession.deviceType,
                        BuildConfig.VERSION_NAME, weekDayId.toString(),mealTypeId.toString(),dietPlan)
                }else {
                    showToast(currActivity.getString(R.string.please_add_diet), true)
                }
            }
        }
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.mDietPlanResponseData, this::onHandleDietPlanSuccessResponse)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)

    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error, true)
    }


    private fun onHandleDietPlanSuccessResponse(workoutWeekDay: Any?){
        workoutWeekDay?.let {
              val returnIntent = Intent()
              returnIntent.putExtra("mealDietType", mealDietTypeNew)
              setResult(Activity.RESULT_OK, returnIntent)
             finish()
        }
    }


}