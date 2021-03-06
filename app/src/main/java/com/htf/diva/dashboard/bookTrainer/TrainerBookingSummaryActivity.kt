package com.htf.diva.dashboard.bookTrainer

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
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.databinding.ActivityBookingSummaryBinding
import com.htf.diva.models.*
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.*
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings
import com.oppwa.mobile.connect.provider.Connect
import kotlinx.android.synthetic.main.activity_booking_summary.*
import kotlinx.android.synthetic.main.activity_booking_summary.ivTrainerImage
import kotlinx.android.synthetic.main.activity_booking_summary.llBookingWithPackage
import kotlinx.android.synthetic.main.activity_booking_summary.llBookingWithPerSession
import kotlinx.android.synthetic.main.activity_booking_summary.llContent
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
import java.util.LinkedHashSet


class TrainerBookingSummaryActivity : BaseDarkActivity<ActivityBookingSummaryBinding, BookingSummaryViewModel>(
    BookingSummaryViewModel::class.java
), IListItemClickListener<Any>, View.OnClickListener {
    private var currActivity: Activity = this


    private var currentDate:String?=null
    private var packageSelected=Packages()
    private var tenureSelected=Tenure()
    private var trainerDetail=TrainerDetailsModel()
    private var booking_type:String?=""
    private var numberOfPeoplePerSession:String?=null
    private var withMyFriendsGym:String?=null
    private var gymBookingWith:String?=null
    private var arrSelectedSlots=ArrayList<Slot>()
    private lateinit var selectedSlotsAdapter: SelectedSlotAdapter
    private var trainerPerSessionPrice:Double?=null
    private var packagePrice:Double?=null
    private var afterCalculateTax:Double?=null
    private var discount_amount:Double?=null
    private var amount_after_discount:Double?=null
    private var calculatedAmtAfterDiscount:Double?=null
    private var totalPayableAmt:Double?=null
    private var is_auto_renew:String?="Yes"
    private var baseAmount:Double?=0.0
    private var trainerBaseAmt:Double?=0.0


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
            gymBookingWith: String?
        ){
            val intent= Intent(currActivity, TrainerBookingSummaryActivity::class.java)
            intent.putExtra("arrBookingSlots", arrBookingSlots)
            intent.putExtra("trainerDetail", trainerDetail)
            intent.putExtra("tenureSelected", tenureSelected)
            intent.putExtra("packageSelected", packageSelected)
            intent.putExtra("booking_type", booking_type)
            intent.putExtra("currentDate", currentDate)
            intent.putExtra("numberOfPeoplePerSession", numberOfPeoplePerSession)
            intent.putExtra("withMyFriendsGym", withMyFriendsGym)
            intent.putExtra("gymBookingWith", gymBookingWith)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_booking_summary
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
        setDetails()
    }

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
        val showInUiDate=DateUtilss.convertDateFormat(currentDate,DateUtilss.serverDateFormat,DateUtilss.targetDateFormat)
        tvJoining_from.text=showInUiDate
        tvTrainerName.text= trainerDetail.trainer!!.name
        Glide.with(currActivity).load(Constants.Urls.TRAINER_IMAGE_URL + trainerDetail.trainer!!.image).centerCrop()
            .placeholder(R.drawable.user).into(ivTrainerImage)


        /* Selected slots rv*/
        if (arrSelectedSlots.size>0){
            llContent.visibility=View.VISIBLE
            val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.VERTICAL, false)
            rvSelectedSlots.layoutManager = mLayout
            selectedSlotsAdapter = SelectedSlotAdapter(currActivity, arrSelectedSlots, this)
            rvSelectedSlots.adapter = selectedSlotsAdapter
        }else{
            llContent.visibility=View.GONE
        }

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
        btnTrainerPayableAmount.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnTrainerPayableAmount -> {
                if (booking_type=="Session"){
                    val slots = HashMap<String, String?>()
                    for (i in 0.until(arrSelectedSlots.size)) {
                        if (arrSelectedSlots[i].date != null) {
                            slots["slots[$i][date]"] = arrSelectedSlots[i].date.toString()
                            slots["slots[$i][start_at]"] = arrSelectedSlots[i].startAt.toString()
                            slots["slots[$i][end_at]"] = arrSelectedSlots[i].endAt.toString()
                        }
                    }
                    viewModel.onBookTrainerClick(
                        AppSession.locale,
                        AppSession.deviceId, AppSession.deviceType,
                        BuildConfig.VERSION_NAME, trainerDetail.trainer!!.id.toString(),
                        tenureSelected.id.toString(),
                        currentDate,booking_type,
                      "", numberOfPeoplePerSession,
                        withMyFriendsGym, AppSession.appDashBoard!!.offers!!.id.toString(),
                        trainerBaseAmt.toString(),baseAmount.toString(),
                        calculatedAmtAfterDiscount.toString(),
                        amount_after_discount.toString() ,trainerDetail.vatPercentage.toString(),
                        afterCalculateTax.toString(), totalPayableAmt.toString(), is_auto_renew, slots
                    )
                }else{
                    val slots = HashMap<String, String?>()
                    for (i in 0.until(arrSelectedSlots.size)) {
                        if (arrSelectedSlots[i].date != null) {
                            slots["slots[$i][date]"] = arrSelectedSlots[i].date.toString()
                            slots["slots[$i][start_at]"] = arrSelectedSlots[i].startAt.toString()
                            slots["slots[$i][end_at]"] = arrSelectedSlots[i].endAt.toString()
                        }
                    }
                    viewModel.onBookTrainerClick(
                        AppSession.locale,
                        AppSession.deviceId, AppSession.deviceType,
                        BuildConfig.VERSION_NAME, trainerDetail.trainer!!.id.toString(),
                        tenureSelected.id.toString(),
                        currentDate,booking_type,
                        packageSelected.id.toString(), "",
                        withMyFriendsGym, AppSession.appDashBoard!!.offers!!.id.toString(),
                        trainerBaseAmt.toString(),baseAmount.toString(),
                        calculatedAmtAfterDiscount.toString(),
                        amount_after_discount.toString() ,trainerDetail.vatPercentage.toString(),
                        afterCalculateTax.toString(), totalPayableAmt.toString(), is_auto_renew, slots
                    )
                }

            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateAmount(){

          if (booking_type=="Session"){
              /* Booking with session*/
              trainerBaseAmt=trainerDetail.trainer!!.perSessionPrice!!.toDouble()
              baseAmount=trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
              amount_after_discount=numberOfPeoplePerSession!!.toDouble()*trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()
              if(AppSession.appDashBoard!!.offers !=null) {
                  tvNo_ofPeopleTraining.text = withMyFriendsGym
                  rltTrainerOfferDiscount.visibility=View.VISIBLE
                  llBookingWithPerSession.visibility=View.VISIBLE
                  llBookingWithPackage.visibility=View.GONE

                  discount_amount=AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble()
                  calculatedAmtAfterDiscount=(amount_after_discount!!*discount_amount!!/100)
                  amount_after_discount=amount_after_discount!!-calculatedAmtAfterDiscount!!
                  tvTrainerOfferPercentage.text=getString(R.string.discount) +" ("+""+(AppUtils.roundMathValueFromDouble(
                      AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble().toInt()
                  ))+"%"+")"
                  tvTrainerOfferPrice.text=currActivity.getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(
                      calculatedAmtAfterDiscount!!.toInt()
                  )
                  tvPersonal_trainer_price.text=currActivity.getString(R.string.sar)+" "+numberOfPeoplePerSession!!.toDouble()*trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()

                  tvTax.text=getString(R.string.vat)+" ("+""+AppUtils.roundMathValueFromDouble(
                      trainerDetail.vatPercentage!!.toDouble().toInt()
                  )+"%"+")"
                  afterCalculateTax=(amount_after_discount!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                  tvTaxCharges.text= getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(
                      afterCalculateTax!!.toInt()
                  ).toString()
                  totalPayableAmt= afterCalculateTax!! + amount_after_discount!!
                  val payableAmount= currActivity.getString(R.string.pay_sar).replace(
                      "[X]", AppUtils.roundMathValueFromDouble(
                          totalPayableAmt!!.toInt()
                      ).toString()
                  )
                  btnTrainerPayableAmount.text=payableAmount

              } else{
                  trainerBaseAmt=trainerDetail.trainer!!.perSessionPrice!!.toDouble()
                  baseAmount=trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
                  tvNo_ofPeopleTraining.text = withMyFriendsGym
                  rltTrainerOfferDiscount.visibility=View.GONE
                  llBookingWithPerSession.visibility=View.VISIBLE
                  amount_after_discount=numberOfPeoplePerSession!!.toDouble()*trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()
                  tvPersonal_trainer_price.text=currActivity.getString(R.string.sar)+" "+amount_after_discount
                  tvTax.text=getString(R.string.vat)+" ("+""+AppUtils.roundMathValueFromDouble(
                      trainerDetail.vatPercentage!!.toDouble().toInt()
                  )+"%"+")"
                  afterCalculateTax=(amount_after_discount!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                  tvTaxCharges.text= getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(
                      afterCalculateTax!!.toInt()
                  ).toString()
                  totalPayableAmt= afterCalculateTax!! + amount_after_discount!!
                  val payableAmount= currActivity.getString(R.string.pay_sar).replace(
                      "[X]", AppUtils.roundMathValueFromDouble(
                          totalPayableAmt!!.toInt()
                      ).toString()
                  )
                  btnTrainerPayableAmount.text=payableAmount

              }

          }else{
              /* Booking with package*/

              if(AppSession.appDashBoard!!.offers !=null) {
                  tvNo_ofPeopleTraining.text = withMyFriendsGym
                  llBookingWithPackage.visibility=View.VISIBLE
                  rltPackageOfferDiscount.visibility=View.VISIBLE
                  llBookingWithPerSession.visibility=View.GONE
                  packagePrice=packageSelected.price!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
                  val strPackageName = currActivity.getString(R.string.package_membership).replace(
                      "[X]",
                      packageSelected.tenureName.toString()
                  )
                  tvPackageName.text=strPackageName
                  tvPackage_price.text=currActivity.getString(R.string.sar)+" "+packagePrice

                  trainerBaseAmt=packageSelected.price!!.toDouble()
                  baseAmount=packageSelected.price!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()

                  tvPackageOfferPercentage.text=getString(R.string.discount) +" ("+""+(AppUtils.roundMathValueFromDouble(
                      AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble().toInt()
                  ))+"%"+")"
                  discount_amount=AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble()
                  calculatedAmtAfterDiscount=(packagePrice!!*discount_amount!!/100)
                  amount_after_discount=packagePrice!!-calculatedAmtAfterDiscount!!
                  tvPackageOfferPrice.text=currActivity.getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(
                      calculatedAmtAfterDiscount!!.toInt()
                  )


                  tvPackageTax.text=getString(R.string.vat)+" ("+""+AppUtils.roundMathValueFromDouble(
                      trainerDetail.vatPercentage!!.toDouble().toInt()
                  )+"%"+")"
                  afterCalculateTax=(amount_after_discount!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                  tvPackageTainerTaxCharges.text= getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(
                      afterCalculateTax!!.toInt()
                  ).toString()
                  totalPayableAmt= afterCalculateTax!! + amount_after_discount!!
                  val payableAmount= currActivity.getString(R.string.pay_sar).replace(
                      "[X]", AppUtils.roundMathValueFromDouble(
                          totalPayableAmt!!.toInt()
                      ).toString()
                  )
                  btnTrainerPayableAmount.text=payableAmount

              }else{
                  tvNo_ofPeopleTraining.text = withMyFriendsGym
                  rltPackageOfferDiscount.visibility=View.GONE
                  llBookingWithPackage.visibility=View.VISIBLE
                  llBookingWithPerSession.visibility=View.GONE
                  packagePrice=packageSelected.price!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
                  val strPackageName = currActivity.getString(R.string.package_membership).replace(
                      "[X]",
                      packageSelected.tenureName.toString()
                  )
                  tvPackageName.text=strPackageName
                  tvPackage_price.text=currActivity.getString(R.string.sar)+" "+packagePrice
                  tvPackageTax.text=getString(R.string.vat)+" ("+""+AppUtils.roundMathValueFromDouble(
                      trainerDetail.vatPercentage!!.toDouble().toInt()
                  )+"%"+")"
                  afterCalculateTax=(packageSelected.price!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                  tvPackageTainerTaxCharges.text= getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(
                      afterCalculateTax!!.toInt()
                  ).toString()
                  totalPayableAmt= afterCalculateTax!! + amount_after_discount!!
                  val payableAmount= currActivity.getString(R.string.pay_sar).replace(
                      "[X]", AppUtils.roundMathValueFromDouble(
                          totalPayableAmt!!.toInt()
                      ).toString()
                  )
                  trainerBaseAmt=packageSelected.price!!.toDouble()
                  baseAmount=packageSelected.price!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()

                  btnTrainerPayableAmount.text=payableAmount
              }
          }
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.mBookFitnessCenterData, this::onHandleLoginSuccessResponse)
        observerViewModel(viewModel.mCheckOutIdGenerateData, this::onHandleCheckOutIdGenerateSuccessResponse)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)

    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error, true)
    }

    private fun onHandleLoginSuccessResponse(bookTrainerCenter: BookFitnessCenterModel?){
        bookTrainerCenter?.let {
            viewModel.onCheckOutIdGenerateForCenter(bookTrainerCenter.id!!.toString(),
                totalPayableAmt,"HyperPay")
            AppSession.centerBookingId=bookTrainerCenter.id.toString()
            AppSession.trainerBookingId=null
            AppSession.paymentType="HyperPay"
        }
    }

    private fun onHandleCheckOutIdGenerateSuccessResponse(checkoutIdGenerateResponse: BookingDetailModel?){
        checkoutIdGenerateResponse?.let {
            val checkoutId=checkoutIdGenerateResponse.id!!
            AppSession.checkoutID=checkoutId
            val paymentBrands = LinkedHashSet<String>()
            paymentBrands.add("VISA")
            paymentBrands.add("MASTER")

            val checkoutSettings = CheckoutSettings(checkoutId, paymentBrands, Connect.ProviderMode.TEST)
            checkoutSettings.shopperResultUrl = "com.htf.diva://result"
            checkoutSettings.skipCVVMode
            val intent = checkoutSettings.createCheckoutActivityIntent(currActivity)
            startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {

        }
    }

}