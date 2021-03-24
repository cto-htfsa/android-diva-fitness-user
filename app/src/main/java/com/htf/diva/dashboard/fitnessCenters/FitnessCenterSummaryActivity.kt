package com.htf.diva.dashboard.fitnessCenters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.databinding.ActivityFitnessCenterBookingSummaryBinding
import com.htf.diva.models.*
import com.htf.diva.utils.AppUtils
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.utils.DialogUtils
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_fitness_center_booking_summary.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class FitnessCenterSummaryActivity : BaseDarkActivity<ActivityFitnessCenterBookingSummaryBinding, BookingSummaryViewModel>(
    BookingSummaryViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this

    private var currentDate:String?=null
    private var packageSelected=Packages()
    private var offers=AppDashBoard.Offers()
    private var tenureSelected=Tenure()
    private var selectedFitnessCenter= AppDashBoard.FitnessCenter()
    private var booking_type:String?=""
    private var numberOfPeoplePerSession:String?=null
    private var withMyFriendsGym:String?=null
    private var sessionPriceCalculate:String?=null
    private var vatPercentage:String?=null
    private var isProgressBar=true
    private var finalPayableAmt:Double?=null
    private var afterCalculateTax:Double?=null
    private var totalPayableAmt:Double?=null

    private var discount_amount:Double?=null
    private var amount_after_discount:Double?=null
    private var baseAmount:Double?=null
    private var totalAmt:Double?=null
    private var calculatedAmtAfterDiscount:Double?=null


        companion object{
        fun open(
            currActivity: Activity,
            offers: AppDashBoard.Offers,
            selectedFitnessCenter: AppDashBoard.FitnessCenter,
            tenureSelected: Tenure,
            packageSelected: Packages,
            currentDate: String?,
            joinCenterWithFriends: String?,
            sessionPriceCalculate: String,
            vatPercentage: String
        ){
            val intent= Intent(currActivity, FitnessCenterSummaryActivity::class.java)
            intent.putExtra("offers",offers)
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
    override var layout = R.layout.activity_fitness_center_booking_summary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.booking_summary)
        binding.fitnessCenterSummaryViewModel = viewModel
        getExtra()
        setOnClickListener()
        viewModelInitialize()
    }

    private fun getExtra() {
        offers=intent.getSerializableExtra("offers") as AppDashBoard.Offers
        selectedFitnessCenter=intent.getSerializableExtra("selectedFitnessCenter") as AppDashBoard.FitnessCenter
        tenureSelected=intent.getSerializableExtra("tenureSelected") as Tenure
        packageSelected=intent.getSerializableExtra("packageSelected") as Packages
        currentDate=intent.getStringExtra("currentDate")
        numberOfPeoplePerSession=intent.getStringExtra("numberOfPeoplePerSession")
        sessionPriceCalculate=intent.getStringExtra("sessionPriceCalculate")
        vatPercentage=intent.getStringExtra("vatPercentage")
        setDetails()
    }

    private fun setOnClickListener(){
        btnPayableAmount.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnPayableAmount->{
                viewModel.onBookFitnessCenterClick(selectedFitnessCenter.id,tenureSelected.id,
                    "2021-03-24",packageSelected.id,numberOfPeoplePerSession,baseAmount,totalAmt,
                    vatPercentage,afterCalculateTax,offers.id,calculatedAmtAfterDiscount.toString(),amount_after_discount,totalPayableAmt)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDetails() {
        /* set all trainer detail*/

        tvFitnessCenter.text = selectedFitnessCenter.name
        tvFitnessCenterAddress.text = selectedFitnessCenter.location
        val str = currActivity.getString(R.string.package_membership).replace("[X]", packageSelected.tenureName.toString())
        tvPackages.text = str
        tvTenure.text = tenureSelected.name
        tvJoining_from.text = currentDate
        tvNo_ofPeople.text = numberOfPeoplePerSession

        baseAmount=packageSelected.price!!.toDouble()
        totalAmt=numberOfPeoplePerSession!!.toDouble()*sessionPriceCalculate!!.toDouble()
        amount_after_discount=numberOfPeoplePerSession!!.toDouble()*sessionPriceCalculate!!.toDouble()

        tvforSession.text=packageSelected.sessions.toString()+" "+currActivity.getString(R.string.session)

        tvPackage_price.text=currActivity.getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(amount_after_discount!!)

        if (offers!=null){
            rltOfferDiscount.visibility=View.VISIBLE
            discount_amount=offers.discountValue!!.toDouble()
            calculatedAmtAfterDiscount=(amount_after_discount!!*discount_amount!!/100)
            amount_after_discount=amount_after_discount!!-calculatedAmtAfterDiscount!!
            tvOfferPercentage.text="${getString(R.string.discount)} (${"-"+offers.discountValue})"
            tvOfferPrice.text=currActivity.getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(calculatedAmtAfterDiscount!!)
        } else{
            amount_after_discount=numberOfPeoplePerSession!!.toDouble()*sessionPriceCalculate!!.toDouble()
        }

        tvPackageTax.text="${getString(R.string.vat)} ($vatPercentage %)"
         afterCalculateTax=(amount_after_discount!!*vatPercentage!!.toDouble())/100
        tvPackageTaxCharges.text= getString(R.string.sar)+" "+afterCalculateTax

          totalPayableAmt= afterCalculateTax!! + amount_after_discount!!
         val payableAmount= currActivity.getString(R.string.pay_sar).replace("[X]",AppUtils.roundMathValueFromDouble(totalPayableAmt!!).toString())
         btnPayableAmount.text=payableAmount
         tvPayable_amt.text=payableAmount

    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.mBookFitnessCenterData,this::onHandleLoginSuccessResponse)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)

    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error,true)
    }

    private fun onHandleValidationErrorResponse(error:String) {
        DialogUtils.showSnackBar(currActivity, tvLoginError, error)
    }

    private fun onHandleLoginSuccessResponse(bookFitnessCenter: BookFitnessCenterModel?){
        bookFitnessCenter?.let {

        }
    }


}