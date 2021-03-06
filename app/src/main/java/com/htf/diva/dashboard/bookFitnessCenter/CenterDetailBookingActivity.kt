package com.htf.diva.dashboard.bookFitnessCenter

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.DetailFitnessCenterAdapter
import com.htf.diva.dashboard.adapters.PackagesAdapter
import com.htf.diva.dashboard.adapters.TenureAdapter
import com.htf.diva.dashboard.bookTrainerWithCenter.TrainerListActivity
import com.htf.diva.dashboard.viewModel.FitnessCenterDetailBookingViewModel
import com.htf.diva.databinding.ActivityCenterDetailBookingBinding
import com.htf.diva.models.*
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.*
import kotlinx.android.synthetic.main.activity_center_detail_booking.*
import kotlinx.android.synthetic.main.layout_privacy_policy.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import kotlin.collections.ArrayList

class CenterDetailBookingActivity : BaseDarkActivity<ActivityCenterDetailBookingBinding, FitnessCenterDetailBookingViewModel>(
    FitnessCenterDetailBookingViewModel::class.java
), IListItemClickListener<Any>, View.OnClickListener {
    private  var startDate= Date()
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
    private var privacyPolicyData:String?=""
    private lateinit var dialog: AlertDialog

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
        getExtra()
        binding.centerDetailBookingViewModel = viewModel
        viewModel.fitnessCenterDetailBooking(AppSession.locale,
            AppSession.deviceId,
            AppSession.deviceType,
            BuildConfig.VERSION_NAME,
            selectedFitnessCenter.id.toString())
            viewModelInitialize()
            selectGroup()
            setListener()

    }

    private fun setListener() {
        switchNeedPersonalTrainer.setOnClickListener(this)
        btnBookCenter.setOnClickListener(this)
        tvCenterJoiningDate.setOnClickListener(this)
        tvPrivacy_policy.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        switchNeedPersonalTrainer.isChecked = false
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.switchNeedPersonalTrainer -> {
                switchNeedPersonalTrainer.isChecked = true
                TrainerListActivity.open(currActivity, Constants.FROM_CENTER,offer,
                    selectedFitnessCenter,
                    tenureSelected,
                    packageSelected,
                    currentDate,
                    joinCenterWithFriends,
                    sessionPriceCalculate,
                    vatPercentage)
            }

            R.id.btnBookCenter -> {
                FitnessCenterSummaryActivity.open(currActivity,
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
                datePickerStart()
            }

            R.id.tvPrivacy_policy->{
                viewModel.privacyPolicy(AppSession.locale, AppSession.deviceId, AppSession.deviceType, BuildConfig.VERSION_NAME)

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

    private fun datePickerStart() {
        val currentDate1 = Calendar.getInstance()
        val date = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            currActivity,
            DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
                date.set(Calendar.MONTH, monthOfYear)
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                date.set(Calendar.YEAR, year)
                date.set(year, monthOfYear, dayOfMonth)
                startDate = date.time
                currentDate=DateUtilss.targetDateFormat.format(startDate)
                tvCenterJoiningDate.text = (DateUtilss.targetDateFormat.format(startDate))

            },
            currentDate1.get(Calendar.YEAR),
            currentDate1.get(Calendar.MONTH),
            currentDate1.get(Calendar.DATE)
        )
        datePickerDialog.datePicker.minDate=System.currentTimeMillis()
        datePickerDialog.show()
    }


    private fun getExtra() {
           selectedFitnessCenter=intent.getSerializableExtra("selectedFitnessCenter")as AppDashBoard.FitnessCenter
           tvTitle.text=selectedFitnessCenter.name
           rbOnlyMeCenter.isChecked=true
           lnrWith_my_frnd_in_center.visibility=View.GONE
         /*val c = Calendar.getInstance().time
           val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
           val formattedDate: String = df.format(c)
         */
            currentDate= DateUtilss.getCurrentDateInServerFormat()
           val showInUiDate=DateUtilss.convertDateFormat(currentDate,DateUtilss.serverDateFormat,DateUtilss.targetDateFormat)
           tvCenterJoiningDate.text= showInUiDate

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
        observerViewModel(viewModel.mPrivacyPolicyResponse, this::privacyPolicyResponse)

    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error, true)
    }

    private fun onHandleAppDashBoardSuccessResponse(fitnessCenterDetailResponse: FitnessCenterDetailForBookModel?){
        fitnessCenterDetailResponse?.let {
            rltCenterDetail.visibility=View.VISIBLE
            lnrBookSlots.visibility=View.VISIBLE
            vatPercentage=fitnessCenterDetailResponse.vatPercentage.toString()
            if (fitnessCenterDetailResponse.offers!=null){
                offer=fitnessCenterDetailResponse.offers!!
            }
            packages=fitnessCenterDetailResponse.packages!!
            setOutFitnessCenter(fitnessCenterDetailResponse.fitnessCenters)
            setTenureList(fitnessCenterDetailResponse.tenures)
        }
    }

    private fun privacyPolicyResponse(privacyPolicyModel: PrivacyPolicyModel?){
        privacyPolicyModel?.let {
            privacyPolicyData=privacyPolicyModel.privacyPolicy
            openPrivacyPolicy(privacyPolicyData)
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
            val payAmount= currActivity.getString(R.string.pay_sar).replace("[X]", joiningPrice.toString())
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


    private fun openPrivacyPolicy(privacyPolicyData: String?) {
        val builder = AlertDialog.Builder(currActivity)
        val dialogView = currActivity.layoutInflater.inflate(R.layout.layout_privacy_policy,
            null
        )
        builder.setView(dialogView)
        builder.setCancelable(true)
        dialog = builder.show()

        val myAnim = AnimationUtils.loadAnimation(currActivity, R.anim.slide_up)
        dialogView.startAnimation(myAnim)



        dialogView.tv_return_policy.text= Html.fromHtml(privacyPolicyData)


        dialogView.rl_main.setOnClickListener {
            dialog.dismiss()
        }

        dialogView.tvOkay.setOnClickListener {
            dialog.dismiss()
        }

        val window = dialog.window
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        window.setGravity(Gravity.BOTTOM)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }



}