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
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.activity_center_detail_booking.*
import kotlinx.android.synthetic.main.activity_fitness_center_booking_summary.*
import kotlinx.android.synthetic.main.toolbar.*

class FitnessCenterSummaryActivity : BaseDarkActivity<ActivityFitnessCenterBookingSummaryBinding, BookingSummaryViewModel>(
    BookingSummaryViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this

    private var currentDate:String?=null
    private var packageSelected=Packages()
    private var tenureSelected=Tenure()
    private var selectedFitnessCenter= AppDashBoard.FitnessCenter()
    private var booking_type:String?=""
    private var numberOfPeoplePerSession:String?=null
    private var withMyFriendsGym:String?=null
    private var sessionPriceCalculate:String?=null
    private var vatPercentage:String?=null

        companion object{
        fun open(
            currActivity: Activity,
            selectedFitnessCenter: AppDashBoard.FitnessCenter,
            tenureSelected: Tenure,
            packageSelected: Packages,
            currentDate: String?,
            joinCenterWithFriends: String?,
            sessionPriceCalculate: String,
            vatPercentage: String
        ){
            val intent= Intent(currActivity, FitnessCenterSummaryActivity::class.java)
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
    }
    private fun getExtra() {
        selectedFitnessCenter=intent.getSerializableExtra("selectedFitnessCenter") as AppDashBoard.FitnessCenter
        tenureSelected=intent.getSerializableExtra("tenureSelected") as Tenure
        packageSelected=intent.getSerializableExtra("packageSelected") as Packages
        currentDate=intent.getStringExtra("currentDate")
        numberOfPeoplePerSession=intent.getStringExtra("numberOfPeoplePerSession")
        sessionPriceCalculate=intent.getStringExtra("sessionPriceCalculate")
        vatPercentage=intent.getStringExtra("vatPercentage")
        setDetails()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {

        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDetails() {
        /* set all trainer detail*/
        tvFitnessCenter.text = selectedFitnessCenter.name
        tvFitnessCenterAddress.text = selectedFitnessCenter.location
        val str = currActivity.getString(R.string.package_membership)
            .replace("[X]", packageSelected.tenureName.toString())
        tvPackages.text = str
        tvTenure.text = tenureSelected.name
        tvJoining_from.text = currentDate
        tvNo_ofPeople.text = numberOfPeoplePerSession


        tvforSession.text=packageSelected.sessions.toString()+" "+currActivity.getString(R.string.session)
        tvPackage_price.text=currActivity.getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(sessionPriceCalculate!!.toDouble())

        tvPackageTax.text="${getString(R.string.vat)} ($vatPercentage %)"
        val afterCalculateTax=(sessionPriceCalculate!!.toDouble()*vatPercentage!!.toDouble())/100
        tvPackageTaxCharges.text= getString(R.string.sar)+" "+afterCalculateTax

         val totalPayableAmt= afterCalculateTax + sessionPriceCalculate!!.toDouble()
         val payableAmount= currActivity.getString(R.string.pay_sar).replace("[X]",AppUtils.roundMathValueFromDouble(totalPayableAmt).toString())
         btnPayableAmount.text=payableAmount
         tvPayable_amt.text=payableAmount

    }

}