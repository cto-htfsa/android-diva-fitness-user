package com.htf.diva.dashboard.bookTrainerWithCenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.SelectedSlotAdapter
import com.htf.diva.dashboard.ui.BookingSuccessfullyActivity
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.databinding.ActivityCenterTrainerBookingSummaryBinding
import com.htf.diva.models.*
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.*
import kotlinx.android.synthetic.main.activity_booking_summary.ivTrainerImage
import kotlinx.android.synthetic.main.activity_booking_summary.llBookingWithPackage
import kotlinx.android.synthetic.main.activity_booking_summary.llBookingWithPerSession
import kotlinx.android.synthetic.main.activity_booking_summary.rltPackageOfferDiscount
import kotlinx.android.synthetic.main.activity_booking_summary.rltTrainerOfferDiscount
import kotlinx.android.synthetic.main.activity_booking_summary.rvSelectedSlots
import kotlinx.android.synthetic.main.activity_booking_summary.switchAutoRenewMembership
import kotlinx.android.synthetic.main.activity_booking_summary.tvFitnessCenter
import kotlinx.android.synthetic.main.activity_booking_summary.tvFitnessCenterAddress
import kotlinx.android.synthetic.main.activity_booking_summary.tvJoining_from
import kotlinx.android.synthetic.main.activity_booking_summary.tvNo_ofPeopleTraining
import kotlinx.android.synthetic.main.activity_booking_summary.tvPackageName
import kotlinx.android.synthetic.main.activity_booking_summary.tvPackageOfferPercentage
import kotlinx.android.synthetic.main.activity_booking_summary.tvPackageOfferPrice
import kotlinx.android.synthetic.main.activity_booking_summary.tvPackageTainerTaxCharges
import kotlinx.android.synthetic.main.activity_booking_summary.tvPackageTax
import kotlinx.android.synthetic.main.activity_booking_summary.tvPackage_price
import kotlinx.android.synthetic.main.activity_booking_summary.tvPackages
import kotlinx.android.synthetic.main.activity_booking_summary.tvPersonal_trainer_price
import kotlinx.android.synthetic.main.activity_booking_summary.tvTax
import kotlinx.android.synthetic.main.activity_booking_summary.tvTaxCharges
import kotlinx.android.synthetic.main.activity_booking_summary.tvTenure
import kotlinx.android.synthetic.main.activity_booking_summary.tvTrainerName
import kotlinx.android.synthetic.main.activity_booking_summary.tvTrainerOfferPercentage
import kotlinx.android.synthetic.main.activity_booking_summary.tvTrainerOfferPrice
import kotlinx.android.synthetic.main.activity_center_trainer_booking_summary.*
import kotlinx.android.synthetic.main.toolbar.*

class BookingSummaryTrainerCenterActivity : BaseDarkActivity<ActivityCenterTrainerBookingSummaryBinding, BookingSummaryViewModel>(
    BookingSummaryViewModel::class.java
), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this


    private var currentDate:String?=null
    private var packageSelected= Packages()
    private var tenureSelected= Tenure()
    private var trainerDetail= TrainerDetailsModel()
    private var booking_type:String?=""
    private var numberOfPeoplePerSession:String?=null
    private var withMyFriendsGym:String?=null
    private var gymBookingWith:String?=null
    private var arrSelectedSlots=ArrayList<Slot>()
    private lateinit var selectedSlotsAdapter: SelectedSlotAdapter
    private var trainerPerSessionPrice:Double?=null
    private var packagePrice:Double?=null
    private var afterCalculateTax:Double?=0.0
    private var discount_amount:Double?=0.0
    private var amount_after_discount:Double?=0.0
    private var baseAmount:Double?=0.0
    private var calculatedAmtAfterDiscount:Double?=0.0
    private var totalPayableAmt:Double?=null
    private var is_auto_renew:String?="Yes"

    private var joinCenterWithFriends:String?=null
    private var sessionPriceCalculate:String?=null
    private var vatPercentage:String?=null
    private var packageCenterSelected=Packages()
    private var offers=AppDashBoard.Offers()
    private var tenureCenterSelected=Tenure()
    private var selectedFitnessCenter= AppDashBoard.FitnessCenter()
    private var currentCenterDate:String?=null
    private var selectCenterPackagePrice:Double?=null


    companion object{
        fun open(
            currActivity: Activity,
            arrBookingSlots: ArrayList<Slot>,
            trainerDetail: TrainerDetailsModel,
            tenureSelected: Tenure,
            packageSelected: Packages,
            booking_type: String?,
            currentDate: String?,
            numberOfPeoplePerSession: String?,
            withMyFriendsGym: String?,
            gymBookingWith: String?,
            offers: AppDashBoard.Offers,
            selectedFitnessCenter: AppDashBoard.FitnessCenter,
            tenureCenterSelected: Tenure,
            packageCenterSelected: Packages,
            currentCenterDate: String?,
            joinCenterWithFriends: String?,
            sessionPriceCalculate: String?,
            vatPercentage: String?){
            val intent= Intent(currActivity, BookingSummaryTrainerCenterActivity::class.java)
            intent.putExtra("arrBookingSlots", arrBookingSlots)
            intent.putExtra("trainerDetail", trainerDetail)
            intent.putExtra("tenureSelected", tenureSelected)
            intent.putExtra("packageSelected", packageSelected)
            intent.putExtra("booking_type", booking_type)
            intent.putExtra("currentDate", currentDate)
            intent.putExtra("numberOfPeoplePerSession", numberOfPeoplePerSession)
            intent.putExtra("withMyFriendsGym", withMyFriendsGym)
            intent.putExtra("gymBookingWith", gymBookingWith)
            intent.putExtra("offers",offers)
            intent.putExtra("selectedFitnessCenter",selectedFitnessCenter)
            intent.putExtra("tenureCenterSelected",tenureCenterSelected)
            intent.putExtra("packageCenterSelected",packageCenterSelected)
            intent.putExtra("currentCenterDate",currentCenterDate)
            intent.putExtra("joinCenterWithFriends",joinCenterWithFriends)
            intent.putExtra("sessionPriceCalculate",sessionPriceCalculate)
            intent.putExtra("vatPercentage",vatPercentage)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_center_trainer_booking_summary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.booking_summary)
        binding.bookingSummaryViewModel = viewModel
        setListener()
        getExtra()
        viewModelInitialize()
    }

    private fun getExtra() {
        arrSelectedSlots.addAll(intent.getSerializableExtra("arrBookingSlots") as ArrayList<Slot>)
        trainerDetail=intent.getSerializableExtra("trainerDetail") as TrainerDetailsModel
        tenureSelected=intent.getSerializableExtra("tenureSelected") as Tenure
        packageSelected=intent.getSerializableExtra("packageSelected") as Packages
        booking_type=intent.getStringExtra("booking_type")
        currentDate=intent.getStringExtra("currentDate")
        numberOfPeoplePerSession=intent.getStringExtra("numberOfPeoplePerSession")
        withMyFriendsGym=intent.getStringExtra("withMyFriendsGym")
        gymBookingWith=intent.getStringExtra("gymBookingWith")

        offers=intent.getSerializableExtra("offers") as AppDashBoard.Offers
        selectedFitnessCenter=intent.getSerializableExtra("selectedFitnessCenter") as AppDashBoard.FitnessCenter
        tenureCenterSelected=intent.getSerializableExtra("tenureCenterSelected") as Tenure
        packageCenterSelected=intent.getSerializableExtra("packageCenterSelected") as Packages
        currentCenterDate=intent.getStringExtra("currentCenterDate")
        joinCenterWithFriends=intent.getStringExtra("joinCenterWithFriends")
        sessionPriceCalculate=intent.getStringExtra("sessionPriceCalculate")
        vatPercentage=intent.getStringExtra("vatPercentage")

        selectCenterPackagePrice=packageCenterSelected.price!!.toDouble()*joinCenterWithFriends!!.toDouble()

        setDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun setDetails(){

        /* set all trainer detail*/
        tvFitnessCenter.text=trainerDetail.fitnessCenters!!.name
        tvFitnessCenterAddress.text=trainerDetail.fitnessCenters!!.location
        val str= currActivity.getString(R.string.package_membership).replace(
            "[X]",
            packageSelected.tenureName.toString()
        )
        tvPackages.text=str
        tvTenure.text=tenureSelected.name
        tvJoining_from.text=currentDate
        tvTrainerName.text= trainerDetail.trainer!!.name
        Glide.with(currActivity).load(Constants.Urls.TRAINER_IMAGE_URL + trainerDetail.trainer!!.image).centerCrop()
            .placeholder(R.drawable.user).into(ivTrainerImage)

        tvCenterName.text=selectedFitnessCenter.name
        tvCenter_price.text=currActivity.getString(R.string.sar)+" "+ AppUtils.roundMathValueFromDouble(packageCenterSelected.price!!.toDouble().toInt()*
                        joinCenterWithFriends!!.toDouble().toInt())


        /* Selected slots rv*/
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.VERTICAL, false)
        rvSelectedSlots.layoutManager = mLayout
        selectedSlotsAdapter = SelectedSlotAdapter(currActivity, arrSelectedSlots, this)
        rvSelectedSlots.adapter = selectedSlotsAdapter

        calculateAmount()


        switchAutoRenewMembership.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchAutoRenewMembership.isChecked = true
                is_auto_renew="Yes"
            }else{
                switchAutoRenewMembership.isChecked = false
                is_auto_renew="No"
            }
        }

    }

    fun setListener(){
        btnTrainerCenterPayableAmount.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnTrainerCenterPayableAmount -> {

                if (offers.id!=null){

                }

                if (booking_type=="Session"){
                    val slots = HashMap<String, String?>()
                    for (i in 0.until(arrSelectedSlots.size)) {
                        if(arrSelectedSlots[i].date != null) {
                            slots["slots[$i][date]"] = arrSelectedSlots[i].date.toString()
                            slots["slots[$i][start_at]"] = arrSelectedSlots[i].startAt.toString()
                            slots["slots[$i][end_at]"] = arrSelectedSlots[i].endAt.toString()
                        }
                    }
                    if (offers.id!=null){
                        viewModel.onBookTrainerWithCenterClick(AppSession.locale, AppSession.deviceId, AppSession.deviceType,
                            BuildConfig.VERSION_NAME, selectedFitnessCenter.id.toString(),trainerDetail.trainer!!.id.toString(),
                            is_auto_renew,trainerDetail.vatPercentage.toString(),offers.id.toString(),calculatedAmtAfterDiscount.toString(),
                            amount_after_discount.toString(),afterCalculateTax.toString(),totalPayableAmt.toString(),
                            tenureCenterSelected.id.toString(),currentCenterDate,
                            packageCenterSelected.id.toString(),joinCenterWithFriends,packageCenterSelected.price,
                            packageCenterSelected.price,tenureSelected.id.toString(),currentDate,booking_type,"",
                          numberOfPeoplePerSession,withMyFriendsGym,baseAmount.toString(),baseAmount.toString(),slots)
                    }else{
                        viewModel.onBookTrainerWithCenterClick(AppSession.locale, AppSession.deviceId, AppSession.deviceType,
                            BuildConfig.VERSION_NAME, selectedFitnessCenter.id.toString(),trainerDetail.trainer!!.id.toString(),
                            is_auto_renew,trainerDetail.vatPercentage.toString(),"",calculatedAmtAfterDiscount.toString(),
                            amount_after_discount.toString(),afterCalculateTax.toString(),totalPayableAmt.toString(),
                            tenureCenterSelected.id.toString(),currentCenterDate,
                            packageCenterSelected.id.toString(),joinCenterWithFriends,packageCenterSelected.price,
                            packageCenterSelected.price,tenureSelected.id.toString(),currentDate,booking_type,"",
                            numberOfPeoplePerSession,withMyFriendsGym,baseAmount.toString(),baseAmount.toString(),slots)
                    }

                }else{
                    val slots = HashMap<String, String?>()
                    for (i in 0.until(arrSelectedSlots.size)) {
                        if(arrSelectedSlots[i].date != null) {
                            slots["slots[$i][date]"] = arrSelectedSlots[i].date.toString()
                            slots["slots[$i][start_at]"] = arrSelectedSlots[i].startAt.toString()
                            slots["slots[$i][end_at]"] = arrSelectedSlots[i].endAt.toString()
                        }
                    }
                    if(offers.id!=null){
                        viewModel.onBookTrainerWithCenterClick(AppSession.locale, AppSession.deviceId, AppSession.deviceType,
                            BuildConfig.VERSION_NAME, selectedFitnessCenter.id.toString(),trainerDetail.trainer!!.id.toString(),
                            is_auto_renew,trainerDetail.vatPercentage.toString(),offers.id.toString(),calculatedAmtAfterDiscount.toString(),
                            amount_after_discount.toString(),afterCalculateTax.toString(),totalPayableAmt.toString(),
                            tenureCenterSelected.id.toString(),currentCenterDate,
                            packageCenterSelected.id.toString(),joinCenterWithFriends,packageCenterSelected.price,
                            packageCenterSelected.price,tenureSelected.id.toString(),currentDate,booking_type,packageSelected.id.toString(),
                        "",withMyFriendsGym,baseAmount.toString(),baseAmount.toString(),slots)
                    }else{
                        viewModel.onBookTrainerWithCenterClick(AppSession.locale, AppSession.deviceId, AppSession.deviceType,
                            BuildConfig.VERSION_NAME, selectedFitnessCenter.id.toString(),trainerDetail.trainer!!.id.toString(),
                            is_auto_renew,trainerDetail.vatPercentage.toString(),"",calculatedAmtAfterDiscount.toString(),
                            amount_after_discount.toString(),afterCalculateTax.toString(),totalPayableAmt.toString(),
                            tenureCenterSelected.id.toString(),currentCenterDate,
                            packageCenterSelected.id.toString(),joinCenterWithFriends,packageCenterSelected.price,
                            packageCenterSelected.price,tenureSelected.id.toString(),currentDate,booking_type,packageSelected.id.toString(),
                            "",withMyFriendsGym,baseAmount.toString(),baseAmount.toString(),slots)
                    }

                }

            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateAmount(){

        if (booking_type=="Session"){
            /* Booking with session*/
            baseAmount=trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()

            amount_after_discount=numberOfPeoplePerSession!!.toDouble()*trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()
            amount_after_discount=amount_after_discount!!+selectCenterPackagePrice!!
            if(AppSession.appDashBoard!!.offers !=null) {
                tvNo_ofPeopleTraining.text = withMyFriendsGym
                rltTrainerOfferDiscount.visibility= View.VISIBLE
                llBookingWithPerSession.visibility= View.VISIBLE
                llBookingWithPackage.visibility= View.GONE

                discount_amount= AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble()
                calculatedAmtAfterDiscount=(amount_after_discount!!*discount_amount!!/100)
                amount_after_discount=amount_after_discount!!-calculatedAmtAfterDiscount!!
                tvTrainerOfferPercentage.text=getString(R.string.discount) +" ("+""+(AppUtils.roundMathValueFromDouble(
                    AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble().toInt()
                ))+"%"+")"
                tvTrainerOfferPrice.text=currActivity.getString(R.string.sar)+" "+ AppUtils.roundMathValueFromDouble(
                    calculatedAmtAfterDiscount!!.toInt()
                )
                tvPersonal_trainer_price.text=currActivity.getString(R.string.sar)+" "+numberOfPeoplePerSession!!.toDouble()*trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()

                tvTax.text=getString(R.string.vat)+" ("+""+ AppUtils.roundMathValueFromDouble(
                    trainerDetail.vatPercentage!!.toDouble().toInt()
                )+"%"+")"
                afterCalculateTax=(amount_after_discount!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                tvTaxCharges.text= getString(R.string.sar)+" "+ AppUtils.roundMathValueFromDouble(
                    afterCalculateTax!!.toInt()
                ).toString()
                totalPayableAmt= afterCalculateTax!! + amount_after_discount!!
                val payableAmount= currActivity.getString(R.string.pay_sar).replace(
                    "[X]", AppUtils.roundMathValueFromDouble(
                        totalPayableAmt!!.toInt()
                    ).toString()
                )
                btnTrainerCenterPayableAmount.text=payableAmount
                tvPayable_amt.text=payableAmount

            } else{
                baseAmount=trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
                tvNo_ofPeopleTraining.text = withMyFriendsGym
                rltTrainerOfferDiscount.visibility= View.GONE
                llBookingWithPerSession.visibility= View.VISIBLE
                amount_after_discount=numberOfPeoplePerSession!!.toDouble()*trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()
                tvPersonal_trainer_price.text=currActivity.getString(R.string.sar)+" "+amount_after_discount
                amount_after_discount=amount_after_discount!!+selectCenterPackagePrice!!
                tvTax.text=getString(R.string.vat)+" ("+""+ AppUtils.roundMathValueFromDouble(
                    trainerDetail.vatPercentage!!.toDouble().toInt()
                )+"%"+")"
                afterCalculateTax=(amount_after_discount!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                tvTaxCharges.text= getString(R.string.sar)+" "+ AppUtils.roundMathValueFromDouble(
                    afterCalculateTax!!.toInt()
                ).toString()


               /* calculatedAmtAfterDiscount=(packagePrice!!*discount_amount!!/100)*/
             /*   amount_after_discount=packagePrice!!-calculatedAmtAfterDiscount!!*/

                totalPayableAmt= afterCalculateTax!! + amount_after_discount!!
                val payableAmount= currActivity.getString(R.string.pay_sar).replace(
                    "[X]", AppUtils.roundMathValueFromDouble(
                        totalPayableAmt!!.toInt()
                    ).toString()
                )
                btnTrainerCenterPayableAmount.text=payableAmount
                tvPayable_amt.text=payableAmount
            }

        }else{
            /* Booking with package*/

            if(AppSession.appDashBoard!!.offers !=null) {
                tvNo_ofPeopleTraining.text = withMyFriendsGym
                llBookingWithPackage.visibility= View.VISIBLE
                rltPackageOfferDiscount.visibility= View.VISIBLE
                llBookingWithPerSession.visibility= View.GONE
                packagePrice=packageSelected.price!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
                val strPackageName = currActivity.getString(R.string.package_membership).replace(
                    "[X]",
                    packageSelected.tenureName.toString()
                )
                tvPackageName.text=strPackageName
                tvPackage_price.text=currActivity.getString(R.string.sar)+" "+packagePrice


                baseAmount=packageSelected.price!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()

                tvPackageOfferPercentage.text=getString(R.string.discount) +" ("+""+(AppUtils.roundMathValueFromDouble(
                    AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble().toInt()
                ))+"%"+")"
                discount_amount= AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble()
                packagePrice=packagePrice!!+selectCenterPackagePrice!!
                calculatedAmtAfterDiscount=(packagePrice!!*discount_amount!!/100)
                amount_after_discount=packagePrice!!-calculatedAmtAfterDiscount!!
                tvPackageOfferPrice.text=currActivity.getString(R.string.sar)+" "+ AppUtils.roundMathValueFromDouble(
                    calculatedAmtAfterDiscount!!.toInt()
                )


                tvPackageTax.text=getString(R.string.vat)+" ("+""+ AppUtils.roundMathValueFromDouble(
                    trainerDetail.vatPercentage!!.toDouble().toInt()
                )+"%"+")"
                afterCalculateTax=(amount_after_discount!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                tvPackageTainerTaxCharges.text= getString(R.string.sar)+" "+ AppUtils.roundMathValueFromDouble(
                    afterCalculateTax!!.toInt()
                ).toString()
                totalPayableAmt= afterCalculateTax!! + amount_after_discount!!
                val payableAmount= currActivity.getString(R.string.pay_sar).replace(
                    "[X]", AppUtils.roundMathValueFromDouble(totalPayableAmt!!.toInt()).toString())
                btnTrainerCenterPayableAmount.text=payableAmount
                tvPayable_amt.text=payableAmount

            }else{
                tvNo_ofPeopleTraining.text = withMyFriendsGym
                rltPackageOfferDiscount.visibility= View.GONE
                llBookingWithPackage.visibility= View.VISIBLE
                llBookingWithPerSession.visibility= View.GONE
                packagePrice=packageSelected.price!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
                val strPackageName = currActivity.getString(R.string.package_membership).replace("[X]", packageSelected.tenureName.toString())
                tvPackageName.text=strPackageName
                tvPackage_price.text=currActivity.getString(R.string.sar)+" "+packagePrice
                tvPackageTax.text=getString(R.string.vat)+" ("+""+ AppUtils.roundMathValueFromDouble(
                    trainerDetail.vatPercentage!!.toDouble().toInt()
                )+"%"+")"


                packagePrice=packagePrice!!+selectCenterPackagePrice!!

                calculatedAmtAfterDiscount=(packagePrice!!*discount_amount!!/100)
                amount_after_discount=packagePrice!!-calculatedAmtAfterDiscount!!

                afterCalculateTax=(packagePrice!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                tvPackageTainerTaxCharges.text= getString(R.string.sar)+" "+ AppUtils.roundMathValueFromDouble(
                    afterCalculateTax!!.toInt()
                ).toString()

                totalPayableAmt= afterCalculateTax!! + packagePrice!!
                val payableAmount= currActivity.getString(R.string.pay_sar).replace(
                    "[X]", AppUtils.roundMathValueFromDouble(
                        totalPayableAmt!!.toInt()
                    ).toString()
                )
                baseAmount=packageSelected.price!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
                btnTrainerCenterPayableAmount.text=payableAmount
                tvPayable_amt.text=payableAmount
            }
        }
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.mBookTrainerFitnessCenterData, this::onHandleBookTrainerCenterSuccessResponse)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)

    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error, true)
    }

    private fun onHandleBookTrainerCenterSuccessResponse(bookTrainerFitnessCenter: BookCenterTrainerModel?){
        bookTrainerFitnessCenter?.let {
             BookingSuccessfullyActivity.open(currActivity)
        }
    }
}