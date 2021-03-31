package com.htf.diva.dashboard.fitnessCenters

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.DetailFitnessCenterAdapter
import com.htf.diva.dashboard.adapters.PackagesAdapter
import com.htf.diva.dashboard.adapters.TenureAdapter
import com.htf.diva.dashboard.ui.PersonalTrainersActivity
import com.htf.diva.dashboard.viewModel.FitnessCenterDetailBookingViewModel
import com.htf.diva.databinding.ActivityCenterDetailBookingBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.FitnessCenterDetailForBookModel
import com.htf.diva.models.Packages
import com.htf.diva.models.Tenure
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DateUtils
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_center_detail_booking.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CenterDetailBookingActivity : BaseDarkActivity<ActivityCenterDetailBookingBinding, FitnessCenterDetailBookingViewModel>(
    FitnessCenterDetailBookingViewModel::class.java
), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this
    private var selectedFitnessCenter = AppDashBoard.FitnessCenter()
    private lateinit var mFitnessCenterAdapter: DetailFitnessCenterAdapter
    private var packageSelected=Packages()
    private var tenureSelected=Tenure()
    private var offer=AppDashBoard.Offers()
    private var packages=ArrayList<Packages>()
    private lateinit var tenureAdapter: TenureAdapter
    private lateinit var packageAdapter: PackagesAdapter
    private var joinCenterWithFriends:String?="1"
    private var isRequirePersonalTrainer: Int=0
    private var currentDate:String?=null
    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private var joiningPrice:Double=0.0
    private var sessionPriceCalculate:String=""
    private var vatPercentage:String=""
    private var selectedDate:String=""


    companion object{
        fun open(currActivity: Activity, selectedFitnessCenter: AppDashBoard.FitnessCenter?){
            val intent= Intent(currActivity, CenterDetailBookingActivity::class.java)
            intent.putExtra("selectedFitnessCenter", selectedFitnessCenter)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_center_detail_booking
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.centerDetailBookingViewModel = viewModel
        viewModel.fitnessCenterDetailBooking(
            AppSession.locale,
            AppSession.deviceId,
            AppSession.deviceType,
            BuildConfig.VERSION_NAME
        )
        viewModelInitialize()
        getExtra()
        selectGroup()
        setListener()

    }

    private fun setListener() {
        switchNeedPersonalTrainer.setOnClickListener(this)
        btnBookCenter.setOnClickListener(this)
        tvCenterJoiningDate.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        switchNeedPersonalTrainer.isChecked = false
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.switchNeedPersonalTrainer -> {
                switchNeedPersonalTrainer.isChecked = true
                PersonalTrainersActivity.open(currActivity, Constants.FROM_CENTER)
            }

            R.id.btnBookCenter -> {
                FitnessCenterSummaryActivity.open(
                    currActivity,
                    offer,
                    selectedFitnessCenter,
                    tenureSelected,
                    packageSelected,
                    currentDate,
                    joinCenterWithFriends,
                    sessionPriceCalculate,
                    vatPercentage)
            }

            R.id.tvCenterJoiningDate -> {
                pickDate()
            }
        }

    }

    private fun selectGroup(){
        rgGroupSelect_center_for.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbOnlyMeCenter -> {
                    joinCenterWithFriends = "1"
                    calculatePrice(joinCenterWithFriends)
                    rbOnlyMeCenter.isChecked = true
                    lnrWith_my_frnd_in_center.visibility = View.GONE
                }
                R.id.rbWithMyFriends_in_center -> {
                    joinCenterWithFriends = "2"
                    withMyFriendsSection()
                    calculatePrice(joinCenterWithFriends)
                    rbWithMyFriends_in_center.isChecked = true
                    lnrWith_my_frnd_in_center.visibility = View.VISIBLE
                }

            }
        }
    }


    private fun pickDate(){
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                currentDate = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                tvCenterJoiningDate.text = currentDate
            }, mYear, mMonth, mDay)
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()

    }


    private fun getExtra() {
           selectedFitnessCenter=intent.getSerializableExtra("selectedFitnessCenter")as AppDashBoard.FitnessCenter
           tvTitle.text=selectedFitnessCenter.name
           rbOnlyMeCenter.isChecked=true
           lnrWith_my_frnd_in_center.visibility=View.GONE
        /*   val c = Calendar.getInstance().time
           val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
           val formattedDate: String = df.format(c)
         */
           currentDate= DateUtils.getCurrentDateInServerFormat()
           tvCenterJoiningDate.text= currentDate

      }

    private fun withMyFriendsSection(){
        ivAdd.setOnClickListener {
            var count: Int = tvPeople.text.toString().toInt()
            count++
            tvPeople.text = "" + count
            joinCenterWithFriends=""+count
            calculatePrice(joinCenterWithFriends)
        }

        ivSubtract.setOnClickListener {
            var count: Int = tvPeople.text.toString().toInt()
            if (count == 2) {
                tvPeople.text = "2"
                calculatePrice(count.toString())
            } else {
                count -= 1
                tvPeople.text = "" + count
                joinCenterWithFriends=""+count
                calculatePrice(joinCenterWithFriends)
            }
        }

    }





    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)
        observerViewModel(
            viewModel.mFitnessCenterDetailData,
            this::onHandleAppDashBoardSuccessResponse
        )
    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error, true)
    }

    private fun onHandleAppDashBoardSuccessResponse(fitnessCenterDetailResponse: FitnessCenterDetailForBookModel?){
        fitnessCenterDetailResponse?.let {
            vatPercentage=fitnessCenterDetailResponse.vatPercentage.toString()
            offer=fitnessCenterDetailResponse.offers!!
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
        mFitnessCenterAdapter = DetailFitnessCenterAdapter(
            currActivity,
            arrFitnessCenters!!,
            selectedFitnessCenter,
            this
        )
        rvBranch.adapter = mFitnessCenterAdapter
       val position =arrFitnessCenters.indexOf(arrFitnessCenters.filter { it.id == selectedFitnessCenter.id }[0])
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


    /*Selected Tenure*/
    fun selectedTenure(selectedTenureItem: Tenure, position: Int) {
         tenureSelected=selectedTenureItem
         val selectedPackage = packages.filter { it.tenureId== tenureSelected.id }
         setPackageList(selectedPackage)
    }

    /*selected Package*/
    fun selectedPackage(selectedPackage: Packages, position: Int) {
        packageSelected=selectedPackage
        calculatePrice("1")
    }


    fun selectFitnessCenter(model: AppDashBoard.FitnessCenter, position: Int) {
        selectedFitnessCenter=model
    }

    /* calculate the price for selected package and tenure*/
    private fun calculatePrice(joinCenterWithFriends: String?) {
        if (joinCenterWithFriends!="1"){
          joiningPrice= joinCenterWithFriends!!.toDouble()*packageSelected.price!!.toDouble()
            val payAmount= currActivity.getString(R.string.pay_sar).replace(
                "[X]",
                joiningPrice.toString()
            )
            btnBookCenter.text=payAmount
        }else{
            sessionPriceCalculate=packageSelected.price.toString()
            val payAmount= currActivity.getString(R.string.pay_sar).replace(
                "[X]",
                sessionPriceCalculate
            )
            btnBookCenter.text=payAmount
        }

    }



}