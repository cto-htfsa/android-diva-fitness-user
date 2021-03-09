package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.AllTrainersAdapter
import com.htf.diva.dashboard.adapters.NotificationAdapter
import com.htf.diva.dashboard.adapters.TrainerAdapter
import com.htf.diva.dashboard.viewModel.NotificationViewModel
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivityNotificationBinding
import com.htf.diva.databinding.ActivityPersonalTrainersBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.Listing
import com.htf.diva.utils.LoadMoreScrollListener
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.activity_personal_trainers.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import kotlin.collections.ArrayList

class PersonalTrainersActivity : BaseDarkActivity<ActivityPersonalTrainersBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
    IListItemClickListener<Any> {
    private var currActivity: Activity = this
    private var page=1
    private var fitnessId=""
    private var query=""
    private var isProgressBar=true
    private var totalCount=0
    private var isLoading = false
    private var arrTrainer=ArrayList<AppDashBoard.TopTrainer>()
    private lateinit var personalTrainerAllAdapter: AllTrainersAdapter
    private var selectedFilter:AppDashBoard.FitnessCenter?=null
    private var term = ""
    private var timer:Timer?=null

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, PersonalTrainersActivity::class.java)
            currActivity.startActivity(intent)
        }
    }


    override var layout = R.layout.activity_personal_trainers
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.personal_trainers)
        binding.personalTrainerViewModel = viewModel
        viewModel.onGetTrainerListing(page = page,isProgressBar = isProgressBar,fitnessId=fitnessId,query=query)
        viewModelInitialize()
        setListener()
        initRecycler()

    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mNotificationData,this::onHandleNotificationResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }


    private fun onHandleNotificationResponse(data: Listing<AppDashBoard.TopTrainer>?){
        data?.let {
            if (page == 1) {
                binding.root.refresh.isRefreshing = false
            } else {
                binding.root.load_more.visibility = View.GONE
            }
            isLoading=false
            totalCount=it.total!!
            if (!it.data.isNullOrEmpty()){
                if (page==1)
                    arrTrainer.clear()
                arrTrainer.addAll(it.data!!)
                tvClearAll.visibility=View.GONE
                personalTrainerAllAdapter.notifyDataSetChanged()
                binding.root.ll_empty.visibility=View.GONE
            }
            else {
                tvClearAll.visibility=View.GONE
                binding.root.ll_empty.visibility = View.VISIBLE
            /*    binding.root.ivNoImage.setImageResource(R.drawable.diva_place_holder)
                binding.root.tvMsg.text=currActivity.getString(R.string.no_trainer_avl)*/
            }
        }
    }


    private fun initRecycler() {
        val mLayout= LinearLayoutManager(currActivity)
        recycler.layoutManager=mLayout
        personalTrainerAllAdapter= AllTrainersAdapter(currActivity,arrTrainer,this)
        recycler.adapter=personalTrainerAllAdapter

        recycler.addOnScrollListener(object : LoadMoreScrollListener(mLayout) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (!isLoading && arrTrainer.size < totalCount) {
                    this@PersonalTrainersActivity.page++
                    isProgressBar = false
                    viewModel.onGetTrainerListing(page = this@PersonalTrainersActivity.page,isProgressBar = isProgressBar,fitnessId=fitnessId,query=query)
                }
            }

        })
    }

    private fun setListener() {
        refresh.setOnRefreshListener(this)
        lnrFilter.setOnClickListener(this)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                term=s.toString()
                if(term!=""){
                    timer= Timer()
                    timer?.schedule(object : TimerTask(){
                        override fun run() {
                            currActivity.runOnUiThread {
                                isProgressBar=false
                                arrTrainer.clear()
                                personalTrainerAllAdapter.notifyDataSetChanged()
                                viewModel.onGetTrainerListing(page,isProgressBar,fitnessId=fitnessId,query=term) }
                        }
                    },500)

                }else{
                    if(ll_empty.isShown){
                        ll_empty.visibility=View.GONE
                    }
                    arrTrainer.clear()
                    personalTrainerAllAdapter.notifyDataSetChanged()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count!=0){
                    if (timer != null) {
                        timer?.cancel()
                    }
                }else{

                }
            }

        })
    }

    override fun onRefresh() {
        page=1
        isProgressBar=true
        viewModel.onGetTrainerListing(page,isProgressBar,fitnessId=fitnessId,query=query)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.lnrFilter -> {
                FilterActivity.open(currActivity)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            FilterActivity.FILTER_REQUEST_CODE->{
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        if (data != null) {
                            selectedFilter = data.getSerializableExtra("selectedFilter") as AppDashBoard.FitnessCenter
                            if(selectedFilter?.id!=null){
                                fitnessId=selectedFilter?.id.toString()
                                query=selectedFilter?.name.toString()
                                viewModel.onGetTrainerListing(page,isProgressBar,fitnessId=fitnessId,query="")
                            }
                        }
                    }
                    Activity.RESULT_CANCELED -> {

                    }
                }
            }
        }


    }


}