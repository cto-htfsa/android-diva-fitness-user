package com.htf.diva.dashboard.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.SelectedSlotAdapter
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.databinding.ActivityBookingSummaryBinding
import com.htf.diva.models.Packages
import com.htf.diva.models.Slot
import com.htf.diva.models.Tenure
import com.htf.diva.models.TrainerDetailsModel
import com.htf.diva.netUtils.Constants
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.AppUtils
import kotlinx.android.synthetic.main.activity_booking_summary.*
import kotlinx.android.synthetic.main.activity_booking_summary.tvFitnessCenter
import kotlinx.android.synthetic.main.activity_booking_summary.tvFitnessCenterAddress
import kotlinx.android.synthetic.main.activity_booking_summary.tvJoining_from
import kotlinx.android.synthetic.main.activity_booking_summary.tvPackageTax
import kotlinx.android.synthetic.main.activity_booking_summary.tvPackage_price
import kotlinx.android.synthetic.main.activity_booking_summary.tvPackages
import kotlinx.android.synthetic.main.activity_booking_summary.tvTenure
import kotlinx.android.synthetic.main.activity_fitness_center_booking_summary.*
import kotlinx.android.synthetic.main.toolbar.*

class BookingSummaryActivity : BaseDarkActivity<ActivityBookingSummaryBinding, BookingSummaryViewModel>(
    BookingSummaryViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener {
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
    private var baseAmount:Double?=null
    private var calculatedAmtAfterDiscount:Double?=null

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
            val intent= Intent(currActivity, BookingSummaryActivity::class.java)
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
        val str= currActivity.getString(R.string.package_membership).replace("[X]", packageSelected.tenureName.toString())
        tvPackages.text=str
        tvTenure.text=tenureSelected.name
        tvJoining_from.text=currentDate
        tvTrainerName.text= trainerDetail.trainer!!.name
        Glide.with(currActivity).load(Constants.Urls.TRAINER_IMAGE_URL + trainerDetail.trainer!!.image).centerCrop()
            .placeholder(R.drawable.user).into(ivTrainerImage)


        /* Selected slots rv*/
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.VERTICAL, false)
        rvSelectedSlots.layoutManager = mLayout
        selectedSlotsAdapter = SelectedSlotAdapter(currActivity, arrSelectedSlots, this)
        rvSelectedSlots.adapter = selectedSlotsAdapter

        calculateAmount()

    }

    fun setListener(){
        btnConfirmSlot.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnConfirmSlot->{
                BookingSuccessfullyActivity.open(currActivity)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateAmount(){

          if (booking_type=="Session"){
              /* Booking with session*/
              amount_after_discount=numberOfPeoplePerSession!!.toDouble()*trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()
              if(AppSession.appDashBoard!!.offers !=null) {
                  tvNo_ofPeopleTraining.text = withMyFriendsGym
                  rltTrainerOfferDiscount.visibility=View.VISIBLE
                  llBookingWithPerSession.visibility=View.VISIBLE
                  llBookingWithPackage.visibility=View.GONE

                  discount_amount=AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble()
                  calculatedAmtAfterDiscount=(amount_after_discount!!*discount_amount!!/100)
                  amount_after_discount=amount_after_discount!!-calculatedAmtAfterDiscount!!
                  tvTrainerOfferPercentage.text=getString(R.string.discount) +" ("+""+(AppUtils.roundMathValueFromDouble(AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble().toInt()))+"%"+")"
                  tvTrainerOfferPrice.text=currActivity.getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(calculatedAmtAfterDiscount!!.toInt())
                  tvPersonal_trainer_price.text=currActivity.getString(R.string.sar)+" "+numberOfPeoplePerSession!!.toDouble()*trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()

                  tvTax.text=getString(R.string.vat)+" ("+""+AppUtils.roundMathValueFromDouble(
                      trainerDetail.vatPercentage!!.toDouble().toInt())+"%"+")"
                  afterCalculateTax=(amount_after_discount!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                  tvTaxCharges.text= getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(afterCalculateTax!!.toInt()).toString()


              } else{
                  tvNo_ofPeopleTraining.text = withMyFriendsGym
                  rltTrainerOfferDiscount.visibility=View.GONE
                  llBookingWithPerSession.visibility=View.VISIBLE
                  amount_after_discount=numberOfPeoplePerSession!!.toDouble()*trainerDetail.trainer!!.perSessionPrice!!.toDouble()*withMyFriendsGym!!.toDouble()
                  tvPersonal_trainer_price.text=currActivity.getString(R.string.sar)+" "+amount_after_discount
                  tvTax.text=getString(R.string.vat)+" ("+""+AppUtils.roundMathValueFromDouble(trainerDetail.vatPercentage!!.toDouble().toInt())+"%"+")"
                  afterCalculateTax=(amount_after_discount!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                  tvTaxCharges.text= getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(afterCalculateTax!!.toInt()).toString()
              }

          }else{
              /* Booking with package*/

              if(AppSession.appDashBoard!!.offers !=null) {
                  tvNo_ofPeopleTraining.text = withMyFriendsGym
                  llBookingWithPackage.visibility=View.VISIBLE
                  rltPackageOfferDiscount.visibility=View.VISIBLE
                  llBookingWithPerSession.visibility=View.GONE
                  packagePrice=packageSelected.price!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
                  val strPackageName = currActivity.getString(R.string.package_membership).replace("[X]", packageSelected.tenureName.toString())
                  tvPackageName.text=strPackageName
                  tvPackage_price.text=currActivity.getString(R.string.sar)+" "+packagePrice


                  tvPackageOfferPercentage.text=getString(R.string.discount) +" ("+""+(AppUtils.roundMathValueFromDouble(AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble().toInt()))+"%"+")"
                  discount_amount=AppSession.appDashBoard!!.offers!!.discountValue!!.toDouble()
                  calculatedAmtAfterDiscount=(packagePrice!!*discount_amount!!/100)
                  amount_after_discount=packagePrice!!-calculatedAmtAfterDiscount!!
                  tvPackageOfferPrice.text=currActivity.getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(calculatedAmtAfterDiscount!!.toInt())


                  tvPackageTax.text=getString(R.string.vat)+" ("+""+AppUtils.roundMathValueFromDouble(
                      trainerDetail.vatPercentage!!.toDouble().toInt())+"%"+")"
                  afterCalculateTax=(amount_after_discount!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                  tvPackageTainerTaxCharges.text= getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(afterCalculateTax!!.toInt()).toString()


              }else{
                  tvNo_ofPeopleTraining.text = withMyFriendsGym
                  rltPackageOfferDiscount.visibility=View.GONE
                  llBookingWithPackage.visibility=View.VISIBLE
                  llBookingWithPerSession.visibility=View.GONE
                  packagePrice=packageSelected.price!!.toDouble()*withMyFriendsGym!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
                  val strPackageName = currActivity.getString(R.string.package_membership).replace("[X]", packageSelected.tenureName.toString())
                  tvPackageName.text=strPackageName
                  tvPackage_price.text=currActivity.getString(R.string.sar)+" "+packagePrice
                  tvPackageTax.text=getString(R.string.vat)+" ("+""+AppUtils.roundMathValueFromDouble(
                      trainerDetail.vatPercentage!!.toDouble().toInt())+"%"+")"
                  afterCalculateTax=(packageSelected.price!!.toDouble()*trainerDetail.vatPercentage!!.toDouble())/100
                  tvPackageTainerTaxCharges.text= getString(R.string.sar)+" "+AppUtils.roundMathValueFromDouble(afterCalculateTax!!.toInt()).toString()

              }


          }
    }
}