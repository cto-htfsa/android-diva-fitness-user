package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.base.MyApplication
import com.htf.diva.dashboard.adapters.SelectedSlotAdapter
import com.htf.diva.dashboard.viewModel.BookingSummaryViewModel
import com.htf.diva.databinding.ActivityBookingSummaryBinding
import com.htf.diva.models.Packages
import com.htf.diva.models.Slot
import com.htf.diva.models.Tenure
import com.htf.diva.models.TrainerDetailsModel
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.MathUtils
import com.htf.diva.utils.content.DummyContent
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.activity_booking_summary.*
import kotlinx.android.synthetic.main.drawer_menu.view.*
import kotlinx.android.synthetic.main.layout_selected_slots.view.*
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
    private var arrSelectedSlots=ArrayList<Slot>()
    private lateinit var selectedSlotsAdapter: SelectedSlotAdapter


    private var trainerPerSessionPrice:Double?=null
    private var packagePrice:Double?=null


    companion object{
        fun open(currActivity: Activity,
            arrBookingSlots: ArrayList<Slot>,
            trainerDetail: TrainerDetailsModel,
            tenureSelected: Tenure,
            packageSelected: Packages,
            booking_type: String?,
            currentDate: String?,
            numberOfPeoplePerSession: String?,
            withMyFriendsGym: String?){
            val intent= Intent(currActivity, BookingSummaryActivity::class.java)
            intent.putExtra("arrBookingSlots", arrBookingSlots)
            intent.putExtra("trainerDetail", trainerDetail)
            intent.putExtra("tenureSelected", tenureSelected)
            intent.putExtra("packageSelected", packageSelected)
            intent.putExtra("booking_type", booking_type)
            intent.putExtra("currentDate", currentDate)
            intent.putExtra("numberOfPeoplePerSession", numberOfPeoplePerSession)
            intent.putExtra("withMyFriendsGym", withMyFriendsGym)
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


    private fun calculateAmount(){
        trainerPerSessionPrice=trainerDetail.trainer!!.perSessionPrice!!.toDouble()
          if (booking_type=="Session"){
              trainerPerSessionPrice= trainerDetail.trainer!!.perSessionPrice!!.toDouble()*numberOfPeoplePerSession!!.toDouble()
          }else{
              packagePrice=packageSelected.price!!.toDouble()
          }

    }



}