package com.htf.diva.dashboard.fitnessCenters

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.DetailFitnessCenterAdapter
import com.htf.diva.dashboard.adapters.PackagesAdapter
import com.htf.diva.dashboard.adapters.TenureAdapter
import com.htf.diva.dashboard.ui.PersonalTrainersActivity
import com.htf.diva.dashboard.viewModel.FitnessCenterDetailBookingViewModel
import com.htf.diva.databinding.ActivityCenterDetailBookingBinding
import com.htf.diva.models.*
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.activity_center_detail_booking.*
import kotlinx.android.synthetic.main.activity_center_detail_booking.rvSelectTenure
import kotlinx.android.synthetic.main.activity_trainer_details.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.rvFitnessCenter
import kotlinx.android.synthetic.main.toolbar.*

class CenterDetailBookingActivity : BaseDarkActivity<ActivityCenterDetailBookingBinding, FitnessCenterDetailBookingViewModel>(
    FitnessCenterDetailBookingViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this
    private var selectedFitnessCenter = AppDashBoard.FitnessCenter()
    private lateinit var mFitnessCenterAdapter: DetailFitnessCenterAdapter
    private var packageSelected=Packages()
    private var tenureSelected=Tenure()
    private var packages=ArrayList<Packages>()
    private lateinit var tenureAdapter: TenureAdapter
    private lateinit var packageAdapter: PackagesAdapter
    private var joinCenterWithFriends:String?=null
    private var isRequirePersonalTrainer: Int=0

    companion object{
        fun open(currActivity: Activity, selectedFitnessCenter: AppDashBoard.FitnessCenter?){
            val intent= Intent(currActivity, CenterDetailBookingActivity::class.java)
            intent.putExtra("selectedFitnessCenter",selectedFitnessCenter)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_center_detail_booking
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.centerDetailBookingViewModel = viewModel
        viewModel.fitnessCenterDetailBooking(AppSession.locale, AppSession.deviceId, AppSession.deviceType, BuildConfig.VERSION_NAME)
        viewModelInitialize()
        getExtra()
        selectGroup()
        withMyFriendsSection()
        setListener()

    }

    private fun setListener() {
        switchNeedPersonalTrainer.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        switchNeedPersonalTrainer.isChecked = false
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.switchNeedPersonalTrainer -> {
                switchNeedPersonalTrainer.isChecked = true
                PersonalTrainersActivity.open(currActivity,Constants.FROM_CENTER)
             }

            R.id.btnBookCenter->{
                FitnessCenterSummaryActivity.open(currActivity)
            }
        }

    }

    private fun selectGroup(){
        rgGroupSelect_center_for.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbOnlyMeCenter -> {
                    rbOnlyMeCenter.isChecked = true
                    lnrWith_my_frnd_in_center.visibility = View.GONE
                }
                R.id.rbWithMyFriends_in_center -> {
                    rbWithMyFriends_in_center.isChecked = true
                    lnrWith_my_frnd_in_center.visibility = View.VISIBLE
                }

            }
        }
    }




    private fun getExtra() {
           selectedFitnessCenter=intent.getSerializableExtra("selectedFitnessCenter")as AppDashBoard.FitnessCenter
           tvTitle.text=selectedFitnessCenter.name
           rbOnlyMeCenter.isChecked=true
           lnrWith_my_frnd_in_center.visibility=View.GONE

      }

    private fun withMyFriendsSection(){
        ivAdd.setOnClickListener {
            var count: Int = tvPeople.text.toString().toInt()
            count++
            tvPeople.text = "" + count
            joinCenterWithFriends=""+count
        }

        ivSubtract.setOnClickListener {
            var count: Int = tvPeople.text.toString().toInt()
            if (count == 2) {
                tvPeople.text = "2"
            } else {
                count -= 1
                tvPeople.text = "" + count
                joinCenterWithFriends=""+count
            }
        }

    }





    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mFitnessCenterDetailData,this::onHandleAppDashBoardSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }

    private fun onHandleAppDashBoardSuccessResponse(fitnessCenterDetailResponse: FitnessCenterDetailForBookModel?){
        fitnessCenterDetailResponse?.let {
            packages=fitnessCenterDetailResponse.packages!!
            setOutFitnessCenter(fitnessCenterDetailResponse.fitnessCenters)
            setTenureList(fitnessCenterDetailResponse.tenures)
        }
    }

    /* set fitness center recyclerview here*/
    private fun setOutFitnessCenter(arrFitnessCenters: ArrayList<AppDashBoard.FitnessCenter>?) {
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
        rvBranch.layoutManager = mLayout
        rvBranch.itemAnimator = null
        mFitnessCenterAdapter = DetailFitnessCenterAdapter(currActivity, arrFitnessCenters!!,selectedFitnessCenter, this)
        rvBranch.adapter = mFitnessCenterAdapter
       val position =arrFitnessCenters.indexOf(arrFitnessCenters.filter { it.id==selectedFitnessCenter.id }[0])
        mFitnessCenterAdapter.rowIndex=position
        mFitnessCenterAdapter.notifyDataSetChanged()
    }

    /* set TenureAdapter recyclerview here*/
    private fun setTenureList(arrTenureList: ArrayList<Tenure>?) {
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
        rvSelectTenure.layoutManager = mLayout
        tenureAdapter = TenureAdapter(currActivity, arrTenureList!!, this)
        rvSelectTenure.adapter = tenureAdapter
    }

    /* set package recyclerview here*/
    private fun setPackageList(arrPackageList: List<Packages>) {
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
        rbCenterPackage.layoutManager = mLayout
        packageAdapter = PackagesAdapter(currActivity, arrPackageList, this)
        rbCenterPackage.adapter = packageAdapter
    }


    fun selectedTenure(selectedTenureItem: Tenure, position: Int) {
         tenureSelected=selectedTenureItem
         val selectedPackage = packages.filter { it.tenureId== tenureSelected.id }
         setPackageList(selectedPackage)
    }

    fun selectedPackage(selectedPackage: Packages, position: Int) {
        packageSelected=selectedPackage
    }



}