package com.htf.diva.dashboard.payment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.diva.R
import com.htf.diva.auth.viewModel.PaymentHistoryViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.PaymentHistoryAdapter
import com.htf.diva.databinding.ActivityPaymentHistoryBinding
import com.htf.diva.models.Listing
import com.htf.diva.models.PaymentHistoryModel
import com.htf.diva.utils.LoadMoreScrollListener
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_personal_trainers.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class PaymentHistoryActivity : BaseDarkActivity<ActivityPaymentHistoryBinding, PaymentHistoryViewModel>(
        PaymentHistoryViewModel::class.java), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        IListItemClickListener<Any> {

    private lateinit var paymentHistoryAdapter: PaymentHistoryAdapter
    private var page=1
    private var isProgressBar=true
    private var totalCount=0
    private var isLoading = false
    private var currActivity: Activity = this
    private var arrPaymentHistory=ArrayList<PaymentHistoryModel>()

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, PaymentHistoryActivity::class.java)
            currActivity.startActivity(intent)
        }
    }


    override var layout = R.layout.activity_payment_history
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.payment_history)
        binding.rvPaymentHistoryViewModel = viewModel
        viewModel.paymentHistoryListing(page = page,isProgressBar = isProgressBar)
        viewModelInitialize()
        setListener()
        initRecycler()

    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mPaymentHistoryData,this::onHandleNotificationResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }


    private fun onHandleNotificationResponse(data: Listing<PaymentHistoryModel>?){
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
                    arrPaymentHistory.clear()
                arrPaymentHistory.addAll(it.data!!)
                tvClearAll.visibility=View.GONE
                paymentHistoryAdapter.notifyDataSetChanged()
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
        paymentHistoryAdapter= PaymentHistoryAdapter(currActivity,arrPaymentHistory,this)
        recycler.adapter=paymentHistoryAdapter

        recycler.addOnScrollListener(object : LoadMoreScrollListener(mLayout) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (!isLoading && arrPaymentHistory.size < totalCount) {
                    this@PaymentHistoryActivity.page++
                    isProgressBar = false
                    viewModel.paymentHistoryListing(page = this@PaymentHistoryActivity.page,isProgressBar = isProgressBar)
                }
            }

        })
    }

    private fun setListener() {
        refresh.setOnRefreshListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

        }
    }


    override fun onRefresh() {
        page=1
        isProgressBar=true
        viewModel.paymentHistoryListing(page,isProgressBar)
    }

}