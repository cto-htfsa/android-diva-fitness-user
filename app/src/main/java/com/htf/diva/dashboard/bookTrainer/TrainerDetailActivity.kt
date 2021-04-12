package com.htf.diva.dashboard.bookTrainer

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
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.utils.*
import kotlinx.android.synthetic.main.activity_trainer_details.*
import kotlinx.android.synthetic.main.activity_trainer_details.rbPackage
import kotlinx.android.synthetic.main.activity_trainer_details.rvSelectTenure
import kotlinx.android.synthetic.main.row_personal_trainers.view.*
import java.util.*
import kotlin.collections.ArrayList


class TrainerDetailActivity : BaseDarkActivity<ActivityTrainerDetailsBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener{
    private var packages=ArrayList<Packages>()
    private var bookingSlots=ArrayList<Slot>()
    private var currActivity: Activity = this
    private lateinit var specialingInAdapter: SpecialisingInAdapter
    private lateinit var tenureAdapter: TenureAdapter
    private lateinit var packageAdapter: PackagesAdapter
    private var currentDate:String?=null
    private var packageSelected=Packages()
    private var tenureSelected=Tenure()
    private var trainerDetail=TrainerDetailsModel()

    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private var booking_type:String?=null
    private var gymBookingWith:String?=null
    private var numberOfPeoplePerSession:String?="1"
    private var withMyFriendsGym:String?="1"
    private  var startDate= Date()


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
                AppSession.deviceType, BuildConfig.VERSION_NAME, topTrainer!!.id.toString())

            currentDate= DateUtils.getCurrentDateInServerFormat()
            val showInUiDate=DateUtils.convertDateFormat(currentDate,DateUtils.serverDateFormat,DateUtils.targetDateFormat)
            tvJoiningDate.text= showInUiDate

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
            llTrainerMain.visibility=View.VISIBLE
            trainerDetail=trainerDetailsModel
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
        tenureSelected=selectedTenureItem
        val selectedPackage = packages.filter { it.tenureId== tenureSelected.id }
        setPackageList(selectedPackage)
    }

    fun selectedPackage(selectedPackage: Packages, position: Int) {
           packageSelected=selectedPackage
    }



     private fun setTrainerDetail(trainerDetails: Trainer){
         val perSessionPrice= MathUtils.convertDoubleToString(trainerDetails.perSessionPrice!!.toDouble())
         val str= currActivity.getString(R.string.sar_amount).replace("[X]",perSessionPrice)
         tvPerSession.text=str
         tvLocation.text=trainerDetails.location

         if (trainerDetails.rating=="0.0"){
             llRating.visibility=View.GONE
         }else{
             llRating.visibility=View.VISIBLE
             tvRating.text=trainerDetails.rating
         }

         if (trainerDetails.totalReviews==0){
             tvReview.visibility=View.GONE
         }else{
             tvReview.visibility=View.VISIBLE
             tvReview.text=trainerDetails.totalReviews.toString()
         }

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
                SelectSlotsActivity.open(
                    currActivity, bookingSlots, trainerDetail,
                    tenureSelected, packageSelected, booking_type, currentDate,
                    numberOfPeoplePerSession, withMyFriendsGym, gymBookingWith
                )
            }
            R.id.tvJoiningDate -> {
                datePickerStart()
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
                currentDate=DateUtils.targetDateFormat.format(startDate)
                tvJoiningDate.text = (DateUtils.targetDateFormat.format(startDate))

            },
            currentDate1.get(Calendar.YEAR),
            currentDate1.get(Calendar.MONTH),
            currentDate1.get(Calendar.DATE)
        )
        datePickerDialog.datePicker.minDate=System.currentTimeMillis()
        datePickerDialog.show()
    }


        private fun selectPackages(){
            rbGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbPackage -> {
                        rbPackage.isChecked = true
                        rvPackages.visibility = View.VISIBLE
                        nlrPer_session.visibility = View.GONE
                        booking_type="Package"
                    }
                    R.id.rbPerSession -> {
                        rvPackages.visibility = View.GONE
                        nlrPer_session.visibility = View.VISIBLE
                        booking_type="Session"
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
                        gymBookingWith="onlyMe"

                    }
                    R.id.rbWithMyFriends -> {
                        rbWithMyFriends.isChecked = true
                        lnrWith_my_frnd.visibility = View.VISIBLE
                        gymBookingWith="with_my_friends"
                        withMyFriendsGym="2"
                    }

                }
            }
        }

        private fun bookPerSession(){
            ivSessionAdd_person.setOnClickListener {
                var count: Int =tvNumberOfItems.text.toString().toInt()
                count++
                tvNumberOfItems.text = "" + count
                numberOfPeoplePerSession="" + count

            }

            ivSessionSubtract.setOnClickListener {
                var count: Int = tvNumberOfItems.text.toString().toInt()
                if (count == 1) {
                    tvNumberOfItems.text = "1"
                } else {
                    count -= 1
                    tvNumberOfItems.text = "" + count
                    numberOfPeoplePerSession="" + count
                }
            }
        }

        private fun withMyFriendsSection(){
            ivNoOfPeopleAdd.setOnClickListener {
                var count: Int = tvNoOfPeople.text.toString().toInt()
                count++
                tvNoOfPeople.text = "" + count
                withMyFriendsGym=""+count
            }

            ivNoOfPeopleMinus.setOnClickListener {
                var count: Int = tvNoOfPeople.text.toString().toInt()
                if (count == 2) {
                    tvNoOfPeople.text = "2"
                } else {
                    count -= 1
                    tvNoOfPeople.text = "" + count
                    withMyFriendsGym=""+count
                }
            }

        }




}