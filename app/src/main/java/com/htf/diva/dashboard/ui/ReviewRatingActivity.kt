package com.htf.diva.dashboard.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.ReviewRatingAdapter
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivityPersonalTrainersBinding
import com.htf.diva.databinding.ActivityReviewRatingBinding
import com.htf.diva.models.Listing
import com.htf.diva.models.ReviewRatingModel
import com.htf.diva.models.TrainerDetailsModel
import com.htf.diva.utils.LoadMoreScrollListener
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_trainer_details.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import kotlin.collections.ArrayList

class ReviewRatingActivity : BaseDarkActivity<ActivityReviewRatingBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    private var currActivity: Activity = this
    private var page=1
    private var fitnessId=""
    private var query=""
    private var isProgressBar=true
    private var totalCount=0
    private var isLoading = false
    private var arrReviewRating=ArrayList<ReviewRatingModel>()
    private lateinit var mReviewRatingAdapter: ReviewRatingAdapter
    private var topTrainer:TrainerDetailsModel?=null
    private var term = ""
    private var timer: Timer?=null
    private var comeFrom:String?=null
    var noMembershipAvlDialog: AlertDialog?=null


    companion object{
        fun open(currActivity: Activity, topTrainer: TrainerDetailsModel){
            val intent= Intent(currActivity, ReviewRatingActivity::class.java)
            intent.putExtra("topTrainer", topTrainer)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_review_rating
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.personalTrainerViewModel = viewModel
        viewModelInitialize()
        getExtra()
        setListener()
        initRecycler()
    }

    override fun onRefresh() {
        page=1
        isProgressBar=true
        viewModel.onGetTrainerReviewRating(page = page,isProgressBar = isProgressBar,topTrainer!!.trainer!!.id.toString())
    }



    private fun getExtra() {
         topTrainer = intent.getSerializableExtra("topTrainer") as TrainerDetailsModel?
         viewModel.onGetTrainerReviewRating(page = page,isProgressBar = isProgressBar,topTrainer!!.trainer!!.id.toString())
    }
    private fun setListener() {
       // btnSelectSlots.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
           /* R.id.btnSelectSlots -> {

            }*/

        }
    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mReviewRatingData,this::onHandleReviewRatingResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }


    private fun onHandleReviewRatingResponse(data: Listing<ReviewRatingModel>?){
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
                    arrReviewRating.clear()
                arrReviewRating.addAll(it.data!!)
                tvClearAll.visibility=View.GONE
                mReviewRatingAdapter.notifyDataSetChanged()
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
        mReviewRatingAdapter= ReviewRatingAdapter(currActivity, arrReviewRating)
        recycler.adapter=mReviewRatingAdapter

        recycler.addOnScrollListener(object : LoadMoreScrollListener(mLayout) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (!isLoading && arrReviewRating.size < totalCount) {
                    this@ReviewRatingActivity.page++
                    isProgressBar = false
                    viewModel.onGetTrainerReviewRating(page = this@ReviewRatingActivity.page,isProgressBar = isProgressBar,
                        topTrainer!!.trainer!!.id.toString())
                } }
        })
    }



}