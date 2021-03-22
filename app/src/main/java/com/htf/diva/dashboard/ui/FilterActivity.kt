package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.FilterCenterAdapter
import com.htf.diva.dashboard.viewModel.FilterViewModel
import com.htf.diva.databinding.ActivityFilterBinding
import com.htf.diva.models.AppDashBoard


import com.htf.diva.utils.AppSession.appDashBoard
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.toolbar.*

class FilterActivity : BaseDarkActivity<ActivityFilterBinding, FilterViewModel>(
    FilterViewModel::class.java), View.OnClickListener {

    private var selectedFilterItem:AppDashBoard.FitnessCenter?=null
    private lateinit var mFitnessCenterAdapter: FilterCenterAdapter
    private var currActivity: Activity = this

    companion object{
        const val FILTER_REQUEST_CODE = 1234
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, FilterActivity::class.java)
            currActivity.startActivityForResult(intent, FILTER_REQUEST_CODE)
        }
    }

    override var layout = R.layout.activity_filter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.filter)
        refresh.isRefreshing=false
        binding.filterViewModel = viewModel
        setListener()

        if(appDashBoard!=null) {
            setOutFitnessCenter(appDashBoard!!.fitnessCenters)
        }
    }

    /* set fitness center recyclerview here*/
    private fun setOutFitnessCenter(fitnessCenters: ArrayList<AppDashBoard.FitnessCenter>?) {
        val mLayout = GridLayoutManager(currActivity, 2)
        recycler.layoutManager = mLayout
        recycler.itemAnimator = null
        mFitnessCenterAdapter = FilterCenterAdapter(currActivity, fitnessCenters!!)
        recycler.adapter = mFitnessCenterAdapter
    }


    private fun setListener() {
        btnApplyNow.setOnClickListener(this)
        btnClearFilter.setOnClickListener(this)
    }


    fun selectedGymCenter(model: AppDashBoard.FitnessCenter, position: Int) {
        selectedFilterItem=model
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnApplyNow -> {
                if(selectedFilterItem!=null){
                    val returnIntent = Intent()
                    returnIntent.putExtra("selectedFilter", selectedFilterItem)
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }else{
                    finish()
                }
            }
            R.id.btnClearFilter->{
                selectedFilterItem=null
                mFitnessCenterAdapter.rowIndex=-1
                mFitnessCenterAdapter.notifyDataSetChanged()

            }
        }
    }


}