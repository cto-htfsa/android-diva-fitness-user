package com.htf.diva.dashboard.bookTrainerWithCenter

import android.annotation.SuppressLint
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
import com.bumptech.glide.Glide
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.PackagesAdapter
import com.htf.diva.dashboard.adapters.SpecialisingInAdapter
import com.htf.diva.dashboard.adapters.TenureAdapter
import com.htf.diva.dashboard.ui.ReviewRatingActivity
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivityBookTrainerCenterDetailsBinding
import com.htf.diva.models.*
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.*
import kotlinx.android.synthetic.main.activity_trainer_details.*
import kotlinx.android.synthetic.main.activity_trainer_details.rbPackage
import kotlinx.android.synthetic.main.activity_trainer_details.rvSelectTenure
import kotlinx.android.synthetic.main.layout_privacy_policy.view.*
import java.util.*
import kotlin.collections.ArrayList

class BookTrainerCenterDetailActivity : BaseDarkActivity<ActivityBookTrainerCenterDetailsBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java), IListItemClickListener<Any>, View.OnClickListener{
    private var packages=ArrayList<Packages>()
    private var bookingSlots=ArrayList<Slot>()
    private var currActivity: Activity = this
    private lateinit var specialingInAdapter: SpecialisingInAdapter
    private lateinit var tenureAdapter: TenureAdapter
    private lateinit var packageAdapter: PackagesAdapter
    private var currentDate:String?=null
    private var packageSelected= Packages()
    private var tenureSelected= Tenure()
    private var trainerDetail= TrainerDetailsModel()

    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private var booking_type:String?=null
    private var gymBookingWith:String?=null
    private var numberOfPeoplePerSession:String?="1"
    private var withMyFriendsGym:String?="1"


    private var joinCenterWithFriends:String?=null
    private var sessionPriceCalculate:String?=null
    private var vatPercentage:String?=null
    private var packageCenterSelected=Packages()
    private var offers=AppDashBoard.Offers()
    private var tenureCenterSelected=Tenure()
    private var selectedFitnessCenter= AppDashBoard.FitnessCenter()
    private var currentCenterDate:String?=null
    private  var startDate= Date()
    private var privacyPolicyData:String?=""
    private lateinit var dialog: AlertDialog


    companion object{
        fun open(
            currActivity: Activity,
            topTrainer: AppDashBoard.TopTrainer,
            offers: AppDashBoard.Offers,
            selectedFitnessCenter: AppDashBoard.FitnessCenter,
            tenureSelected: Tenure,
            packageSelected: Packages,
            currentDate: String?,
            joinCenterWithFriends: String?,
            sessionPriceCalculate: String?,
            vatPercentage: String?){
            val intent= Intent(currActivity, BookTrainerCenterDetailActivity::class.java)
            intent.putExtra("topTrainer", topTrainer)
            intent.putExtra("offers",offers)
            intent.putExtra("selectedFitnessCenter",selectedFitnessCenter)
            intent.putExtra("tenureSelected",tenureSelected)
            intent.putExtra("packageSelected",packageSelected)
            intent.putExtra("currentDate",currentDate)
            intent.putExtra("joinCenterWithFriends",joinCenterWithFriends)
            intent.putExtra("sessionPriceCalculate",sessionPriceCalculate)
            intent.putExtra("vatPercentage",vatPercentage)
            currActivity.startActivity(intent)
        }
    }


    override var layout = R.layout.activity_book_trainer_center_details
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
        viewModel.trainerDetails(AppSession.locale, AppSession.deviceId,
            AppSession.deviceType, BuildConfig.VERSION_NAME, topTrainer!!.id.toString())

        currentDate= DateUtilss.getCurrentDateInServerFormat()
        val showInUiDate=DateUtilss.convertDateFormat(currentDate,DateUtilss.serverDateFormat,DateUtilss.targetDateFormat)
        tvJoiningDate.text= showInUiDate
        offers=intent.getSerializableExtra("offers") as AppDashBoard.Offers
        selectedFitnessCenter=intent.getSerializableExtra("selectedFitnessCenter") as AppDashBoard.FitnessCenter
        tenureCenterSelected=intent.getSerializableExtra("tenureSelected") as Tenure
        packageCenterSelected=intent.getSerializableExtra("packageSelected") as Packages
        currentCenterDate=intent.getStringExtra("currentDate")
        joinCenterWithFriends=intent.getStringExtra("joinCenterWithFriends")
        sessionPriceCalculate=intent.getStringExtra("sessionPriceCalculate")
        vatPercentage=intent.getStringExtra("vatPercentage")

    }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)
        observerViewModel(
            viewModel.mTrainerDetailResponse,
            this::onHandleTrainerDetailsSuccessResponse
        )
        observerViewModel(viewModel.mPrivacyPolicyResponse, this::privacyPolicyResponse)
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

    private fun privacyPolicyResponse(privacyPolicyModel: PrivacyPolicyModel?){
        privacyPolicyModel?.let {
            privacyPolicyData=privacyPolicyModel.privacyPolicy
            openPrivacyPolicy(privacyPolicyData)
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



    @SuppressLint("SetTextI18n")
    private fun setTrainerDetail(trainerDetails: Trainer){
        val perSessionPrice= MathUtils.convertDoubleToString(trainerDetails.perSessionPrice!!.toDouble())
        val str= currActivity.getString(R.string.sar_amount).replace("[X]",perSessionPrice)
        tvPerSession.text=str
        tvLocation.text=trainerDetails.location
        tvRating.text=trainerDetails.rating
        tvReview.text=trainerDetails.totalReviews.toString()+" "+currActivity.getString(R.string.review)
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
        tvPrivacy_policy.setOnClickListener(this)
        ll_rating_review.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnSelectSlots -> {
                TrainerCenterSlotsActivity.open(currActivity, bookingSlots,trainerDetail,
                    tenureSelected,packageSelected,booking_type,currentDate,
                    numberOfPeoplePerSession,withMyFriendsGym,gymBookingWith,offers,
                    selectedFitnessCenter,tenureCenterSelected,packageCenterSelected,
                    currentCenterDate,joinCenterWithFriends,sessionPriceCalculate,vatPercentage)
            }
            R.id.tvJoiningDate -> {
                datePickerStart()
            }
            R.id.tvPrivacy_policy->{
                viewModel.privacyPolicy(AppSession.locale, AppSession.deviceId, AppSession.deviceType, BuildConfig.VERSION_NAME)

            }
            R.id.ll_rating_review->{
                ReviewRatingActivity.open(currActivity,trainerDetail)

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
                tvJoiningDate.text = (DateUtilss.targetDateFormat.format(startDate))

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