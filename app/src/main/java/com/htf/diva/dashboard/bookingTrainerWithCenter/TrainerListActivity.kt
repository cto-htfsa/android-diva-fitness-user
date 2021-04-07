package com.htf.diva.dashboard.bookingTrainerWithCenter

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
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.TrainerListAdapter
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivityPersonalTrainersBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.Listing
import com.htf.diva.models.Packages
import com.htf.diva.models.Tenure
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.LoadMoreScrollListener
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_personal_trainers.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import kotlin.collections.ArrayList

class TrainerListActivity : BaseDarkActivity<ActivityPersonalTrainersBinding, PersonalTrainerViewModel>(
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
    private lateinit var personalTrainerAllAdapter: TrainerListAdapter
    private var selectedFilter: AppDashBoard.FitnessCenter?=null
    private var term = ""
    private var timer: Timer?=null
    private var comeFrom:String?=null
    private var packageSelected=Packages()
    private var offers=AppDashBoard.Offers()
    private var tenureSelected=Tenure()
    private var selectedFitnessCenter= AppDashBoard.FitnessCenter()
    private var numberOfPeoplePerSession:String?=null
    private var sessionPriceCalculate:String?=null
    private var vatPercentage:String?=null
    private var currentDate:String?=null


    companion object{
        fun open(
            currActivity: Activity,
            keyPersonalTrainerScreenComeFrom: String?,
            offer: AppDashBoard.Offers,
            selectedFitnessCenter: AppDashBoard.FitnessCenter,
            tenureSelected: Tenure,
            packageSelected: Packages,
            currentDate: String?,
            joinCenterWithFriends: String?,
            sessionPriceCalculate: String,
            vatPercentage: String
        ){
            val intent= Intent(currActivity, TrainerListActivity::class.java)
            intent.putExtra(Constants.COME_FROM,keyPersonalTrainerScreenComeFrom)
            intent.putExtra("offers",offer)
            intent.putExtra("selectedFitnessCenter",selectedFitnessCenter)
            intent.putExtra("tenureSelected",tenureSelected)
            intent.putExtra("packageSelected",packageSelected)
            intent.putExtra("currentDate",currentDate)
            intent.putExtra("numberOfPeoplePerSession",joinCenterWithFriends)
            intent.putExtra("sessionPriceCalculate",sessionPriceCalculate)
            intent.putExtra("vatPercentage",vatPercentage)
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
        getExtras()
        setListener()
        initRecycler()

    }

    private fun getExtras(){
        comeFrom=intent.getStringExtra(Constants.COME_FROM)
        if (comeFrom== Constants.FROM_HOME){
            lnrFilter.visibility=View.VISIBLE
        } else{
            lnrFilter.visibility=View.GONE
        }
        offers=intent.getSerializableExtra("offers") as AppDashBoard.Offers
        selectedFitnessCenter=intent.getSerializableExtra("selectedFitnessCenter") as AppDashBoard.FitnessCenter
        tenureSelected=intent.getSerializableExtra("tenureSelected") as Tenure
        packageSelected=intent.getSerializableExtra("packageSelected") as Packages
        currentDate=intent.getStringExtra("currentDate")
        numberOfPeoplePerSession=intent.getStringExtra("numberOfPeoplePerSession")
        sessionPriceCalculate=intent.getStringExtra("sessionPriceCalculate")
        vatPercentage=intent.getStringExtra("vatPercentage")

    }

    private fun setListener() {
        refresh.setOnRefreshListener(this)
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

        }
    }


    private fun initRecycler() {
        val mLayout= LinearLayoutManager(currActivity)
        recycler.layoutManager=mLayout
        personalTrainerAllAdapter= TrainerListAdapter(currActivity,arrTrainer,this)
        recycler.adapter=personalTrainerAllAdapter

        recycler.addOnScrollListener(object : LoadMoreScrollListener(mLayout) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (!isLoading && arrTrainer.size < totalCount) {
                    this@TrainerListActivity.page++
                    isProgressBar = false
                    viewModel.onGetTrainerListing(page = this@TrainerListActivity.page,isProgressBar = isProgressBar,fitnessId=fitnessId,query=query) } }
        })
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


    override fun onItemClickListener(topTrainer: Any) {
        if (topTrainer is AppDashBoard.TopTrainer)
            BookTrainerCenterDetailActivity.open(currActivity,
                topTrainer,
                offers,
                selectedFitnessCenter,
                tenureSelected,
                packageSelected,
                currentDate,
                numberOfPeoplePerSession,
                sessionPriceCalculate,
                vatPercentage)
    }


}