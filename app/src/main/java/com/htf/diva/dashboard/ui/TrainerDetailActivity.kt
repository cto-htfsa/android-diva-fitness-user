package com.htf.diva.dashboard.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.PackagesAdapter
import com.htf.diva.dashboard.adapters.SpecialisingInAdapter
import com.htf.diva.dashboard.adapters.TenureAdapter
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivityTrainerDetailsBinding
import com.htf.diva.models.*
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DateUtils
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.activity_slot_book.*
import kotlinx.android.synthetic.main.activity_trainer_details.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TrainerDetailActivity : BaseDarkActivity<ActivityTrainerDetailsBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java
), IListItemClickListener<Any>, View.OnClickListener{
    private var packages=ArrayList<Packages>()
    private var bookingSlots=ArrayList<Slot>()
    private var currActivity: Activity = this
    private lateinit var specialingInAdapter: SpecialisingInAdapter
    private lateinit var tenureAdapter: TenureAdapter
    private lateinit var packageAdapter: PackagesAdapter
    private var currentDate:String?=null

    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0

    companion object{
        fun open(currActivity: Activity, topTrainer: AppDashBoard.TopTrainer){
            val intent= Intent(currActivity, TrainerDetailActivity::class.java)
            intent.putExtra("topTrainer", topTrainer)
            currActivity.startActivity(intent)
        }
    }


    override var layout = R.layout.activity_trainer_details
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.trainerDetailViewModel = viewModel
        setListener()
        getExtra()
        viewModelInitialize()


         /* Select Packages logic*/
        selectPackages()

        selectGroup()

        /* this section for book  per session  click*/
        bookPerSession()

        /* here for with my friends packages section*/
        withMyFriendsSection()

    }



    private fun getExtra() {
        val topTrainer = intent.getSerializableExtra("topTrainer") as AppDashBoard.TopTrainer?
            viewModel.trainerDetails(
                AppSession.locale, AppSession.deviceId,
                AppSession.deviceType, BuildConfig.VERSION_NAME, topTrainer!!.id.toString()
            )
            currentDate=DateUtils.getCurrentDateForDashBoard()
            tvJoiningDate.text= currentDate


    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)
        observerViewModel(
            viewModel.mTrainerDetailResponse,
            this::onHandleTrainerDetailsSuccessResponse
        )
    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error, true)
    }

    private fun onHandleTrainerDetailsSuccessResponse(trainerDetailsModel: TrainerDetailsModel?){
        trainerDetailsModel?.let {
            setTrainerDetail(trainerDetailsModel.trainer!!)
            setSpecialisingList(trainerDetailsModel.specializations!!)
            setTenureList(trainerDetailsModel.tenures!!)
            packages=trainerDetailsModel.packages!!
            bookingSlots=trainerDetailsModel.slots!!
            lnrWith_my_frnd.visibility=View.GONE
            rbOnlyMe.isChecked=true
        }
    }

    fun selectedTenure(selectedTenureItem: Tenure, position: Int) {
        val selectedTenure=selectedTenureItem
        val selectedPackage = packages.filter { it.tenureId== selectedTenure.id }
        setPackageList(selectedPackage)
    }

    fun selectedPackage(selectedPackage: Packages, position: Int) {

    }



     private fun setTrainerDetail(trainerDetails: Trainer){
         tvTrainerName.text=trainerDetails.name
         tvLocation.text=trainerDetails.location
         tvRating.text=trainerDetails.rating
         tvReview.text=trainerDetails.totalReviews.toString()
         tvAboutUs.text=trainerDetails.aboutUs
         Glide.with(currActivity).load(Constants.Urls.TRAINER_IMAGE_URL + trainerDetails.image).
         override(250, 250).placeholder(R.drawable.trainer_placeholder).into(ivTrainerImage)
     }

    /* set Specialising recyclerview here*/
    private fun setSpecialisingList(specialisingList: ArrayList<Specialization>?) {
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
        rvSpecialisation.layoutManager = mLayout
         specialingInAdapter = SpecialisingInAdapter(currActivity, specialisingList!!, this)
        rvSpecialisation.adapter = specialingInAdapter
    }


   /* set TenureAdapter recyclerview here*/
    private fun setTenureList(tenuregList: ArrayList<Tenure>?) {
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
         rvSelectTenure.layoutManager = mLayout
         tenureAdapter = TenureAdapter(currActivity, tenuregList!!, this)
         rvSelectTenure.adapter = tenureAdapter
    }

    /* set package recyclerview here*/
    private fun setPackageList(packageList: List<Packages>) {
        rbPackage.isChecked=true
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
        rvPackages.layoutManager = mLayout
         packageAdapter = PackagesAdapter(currActivity, packageList, this)
        rvPackages.adapter = packageAdapter
    }


    private fun setListener() {
        btnSelectSlots.setOnClickListener(this)
        tvJoiningDate.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnSelectSlots -> {
                SelectSlotsActivity.open(currActivity, bookingSlots)
            }
            R.id.tvJoiningDate -> {
                // Get Current Date

                // Get Current Date
                val c = Calendar.getInstance()
                mYear = c[Calendar.YEAR]
                mMonth = c[Calendar.MONTH]
                mDay = c[Calendar.DAY_OF_MONTH]


                val datePickerDialog = DatePickerDialog(
                    this, { _, year, monthOfYear, dayOfMonth ->
                        val selectedDate = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                        tvJoiningDate.text = selectedDate

                    }, mYear, mMonth, mDay
                )
                datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
                datePickerDialog.show()


            }
        }
    }


        private fun selectPackages(){
            rbGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbPackage -> {
                        rbPackage.isChecked = true
                        rvPackages.visibility = View.VISIBLE
                        nlrPer_session.visibility = View.GONE
                    }
                    R.id.rbPerSession -> {
                        rvPackages.visibility = View.GONE
                        nlrPer_session.visibility = View.VISIBLE
                    }
                }
            }
        }

        private fun selectGroup(){
            rgGroupSelect_for.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbOnlyMe -> {
                        rbOnlyMe.isChecked = true
                        lnrWith_my_frnd.visibility = View.GONE
                    }
                    R.id.rbWithMyFriends -> {
                        rbWithMyFriends.isChecked = true
                        lnrWith_my_frnd.visibility = View.VISIBLE
                    }

                }
            }
        }

        private fun bookPerSession(){
            ivSessionAdd_person.setOnClickListener {
                var count: Int =tvNumberOfItems.text.toString().toInt()
                count++
                tvNumberOfItems.text = "" + count

            }

            ivSessionSubtract.setOnClickListener {
                var count: Int = tvNumberOfItems.text.toString().toInt()
                if (count == 1) {
                    tvNumberOfItems.text = "1"
                } else {
                    count -= 1
                    tvNumberOfItems.text = "" + count
                }
            }
        }

        private fun withMyFriendsSection(){
            ivNoOfPeopleAdd.setOnClickListener {
                var count: Int = tvNoOfPeople.text.toString().toInt()
                count++
                tvNoOfPeople.text = "" + count
            }

            ivNoOfPeopleMinus.setOnClickListener {
                var count: Int = tvNoOfPeople.text.toString().toInt()
                if (count == 2) {
                    tvNoOfPeople.text = "2"
                } else {
                    count -= 1
                    tvNoOfPeople.text = "" + count
                }
            }

        }


    private fun choseTrainingJoinDate(){


    }


}