package com.htf.diva.dashboard.ui

import android.app.Activity
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
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.htf.eyenakhr.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.activity_trainer_details.*


class TrainerDetailActivity : BaseDarkActivity<ActivityTrainerDetailsBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java
), IListItemClickListener<Any>, View.OnClickListener{
    private var packages=ArrayList<Packages>()
    private var currActivity: Activity = this
    private lateinit var specialingInAdapter: SpecialisingInAdapter
    private lateinit var tenureAdapter: TenureAdapter
    private lateinit var packageAdapter: PackagesAdapter


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


        /* this section for book  per session  click*/
        bookPerSession()

        /* here for with my friends packages section*/
        withMyFriendsSection()


    }

    private fun bookPerSession(){
        ivSessionAdd_person.setOnClickListener {
            var count: Int = java.lang.String.valueOf(tvNumberOfItems.text).toInt()
            count++
            tvNumberOfItems.text = "" + count
        }

        ivSessionSubtract.setOnClickListener {
            var count: Int = java.lang.String.valueOf(tvNumberOfItems.text).toInt()
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
            var count: Int = java.lang.String.valueOf(tvNoOfPeople.text).toInt()
            count++
            tvNoOfPeople.text = "" + count
        }

        ivNoOfPeopleMinus.setOnClickListener {
            var count: Int = java.lang.String.valueOf(tvNoOfPeople.text).toInt()
            if (count == 1) {
                tvNoOfPeople.text = "1"
            } else {
                count -= 1
                tvNoOfPeople.text = "" + count
            }
        }

    }

    private fun getExtra() {
        val topTrainer = intent.getSerializableExtra("topTrainer") as AppDashBoard.TopTrainer?
            viewModel.trainerDetails(
                AppSession.locale, AppSession.deviceId,
                AppSession.deviceType, BuildConfig.VERSION_NAME, topTrainer!!.id.toString()
            )
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

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}