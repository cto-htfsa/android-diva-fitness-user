package com.htf.diva.dashboard.ui
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.DietWeekDaysAdapter
import com.htf.diva.dashboard.viewModel.DitPlanViewModel
import com.htf.diva.databinding.ActivityDietWeekDaysBinding
import com.htf.diva.models.DietWeekdayModel
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*

class DietWeekDaysActivity : BaseDarkActivity<ActivityDietWeekDaysBinding, DitPlanViewModel>(
    DitPlanViewModel::class.java), View.OnClickListener , IListItemClickListener<Any>, SwipeRefreshLayout.OnRefreshListener {
    private var currActivity: Activity = this
    private lateinit var dietWeekDaysAdapter: DietWeekDaysAdapter

    companion object {
        fun open(currActivity: Activity) {
            val intent = Intent(currActivity, DietWeekDaysActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_diet_week_days
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.create_diet_plan)
        binding.root.refresh.setOnRefreshListener(this)
        binding.dietWeekDayViewModel = viewModel
        setListener()
        viewModel.dietWeekdaysList()
        viewModelInitialize()

    }
    private fun setListener() {

    }

    override fun onClick(p0: View?) {

    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mDietWeekDaysResponse, this::onHandleAppDashBoardSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }

    private fun onHandleAppDashBoardSuccessResponse(arrList: ArrayList<DietWeekdayModel>?) {
        arrList?.let {
            if(arrList.size>0){
                val mLayout= LinearLayoutManager(currActivity)
                recycler.layoutManager=mLayout
                dietWeekDaysAdapter= DietWeekDaysAdapter(currActivity,arrList,this)
                recycler.adapter=dietWeekDaysAdapter
            }else{
                tvClearAll.visibility=View.GONE
                binding.root.ll_empty.visibility = View.VISIBLE
                binding.root.ivNoImage.setImageResource(R.drawable.no_diet_plan)
                binding.root.tvMsg.text=currActivity.getString(R.string.no_diet_available)
            }
        }
    }

    override fun onItemClickListener(data: Any) {
        if (data is DietWeekdayModel)
            MealTypesActivity.open(currActivity,data)
    }

    override fun onRefresh() {
        binding.root.refresh.isRefreshing = false
    }

}
