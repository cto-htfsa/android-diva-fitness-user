package com.htf.diva.dashboard.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.auth.ui.LoginActivity
import com.htf.diva.base.BaseFragment
import com.htf.diva.callBack.IListItemClickListener
import com.htf.diva.dashboard.adapters.BannerAdapter
import com.htf.diva.dashboard.adapters.CenterAdapter
import com.htf.diva.dashboard.adapters.TrainerAdapter
import com.htf.diva.dashboard.bookFitnessCenter.CenterActivity
import com.htf.diva.dashboard.bookFitnessCenter.CenterDetailBookingActivity
import com.htf.diva.dashboard.bookTrainer.TrainerDetailActivity
import com.htf.diva.dashboard.homeWorkoutPlan.CreateWorkoutPlanActivity
import com.htf.diva.dashboard.manageSession.ManageSlotAdapter
import com.htf.diva.dashboard.ui.HomeActivity
import com.htf.diva.dashboard.ui.PersonalTrainersActivity
import com.htf.diva.dashboard.viewModel.HomeViewModel
import com.htf.diva.databinding.FragmentHomeBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.Banner
import com.htf.diva.models.BookingDetailModel
import com.htf.diva.models.MyDietModel
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.*
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.dialog_buy_membership.view.*
import kotlinx.android.synthetic.main.fragment_diet.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.tvBurned
import kotlinx.android.synthetic.main.fragment_home.tvCalsLeft
import kotlinx.android.synthetic.main.fragment_home.tvConsumed
import kotlinx.android.synthetic.main.fragment_home.tvDietConsumedCabs
import kotlinx.android.synthetic.main.fragment_home.tvDietConsumedCalories
import kotlinx.android.synthetic.main.fragment_home.tvDietConsumedFat
import kotlinx.android.synthetic.main.fragment_home.tvDietConsumedProtien
import kotlinx.android.synthetic.main.fragment_home.tvPlanCabs
import kotlinx.android.synthetic.main.fragment_home.tvPlanCalories
import kotlinx.android.synthetic.main.fragment_home.tvPlanFat
import kotlinx.android.synthetic.main.fragment_home.tvPlanProtien
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.lnrMyWorkout
import kotlinx.android.synthetic.main.fragment_home.view.lnrOnRest
import kotlinx.android.synthetic.main.fragment_home.workoutProgress
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class.java),
    IListItemClickListener<Any>,View.OnClickListener {
    private lateinit var currActivity: Activity
    lateinit var binding: FragmentHomeBinding
    private lateinit var personalTrainerAdapter: TrainerAdapter
    private lateinit var mFitnessCenterAdapter: CenterAdapter
    private lateinit var manageSlotAdapter: ManageSlotAdapter
    private lateinit var mAdapter: BannerAdapter
    var noMembershipAvlDialog:AlertDialog?=null

    var myDietPlan: AppDashBoard.DietPlans? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeFragmentViewModel = viewModel

        println("onCreate:->SHUBHAM")
        setListener()
        viewModel.appDashBoard(AppSession.locale, AppSession.deviceId, AppSession.deviceType, BuildConfig.VERSION_NAME)
        viewModelInitialize()
        if(currUser==null){
        } else{
            if(currUser.user!!.name!=null){
                val nameStr=getString(R.string.hey_name)
                binding.root.tvHeyMsg.text=nameStr.replace("[x]", currUser.user!!.name!!)
            }
        }
        return binding.root
    }

    private fun setListener() {
        binding.root.btnViewAll.setOnClickListener(this)
        binding.root.llBuyMembership.setOnClickListener(this)
        binding.root.tvTodaysDiet.setOnClickListener(this)
        binding.root.tvTodaysWorkout.setOnClickListener(this)
        binding.root.btn_create_rest_workout.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnViewAll -> {
                PersonalTrainersActivity.open(currActivity,Constants.FROM_HOME)
            }

            R.id.tvTodaysDiet -> {
                HomeActivity.open(currActivity, "TodayDietPLan")
            }
             R.id.tvTodaysWorkout -> {
                 HomeActivity.open(currActivity, "TodayWorkout")
            }
            R.id.btn_create_rest_workout -> {
                CreateWorkoutPlanActivity.open(currActivity, "comeFromNoWorkout")
            }

            R.id.llBuyMembership->{
                val currUser= AppPreferences.getInstance(currActivity).getUserDetails()
                if (currUser!=null){
                    CenterActivity.open(currActivity)
                }else{
                    LoginActivity.open(currActivity, "fromBuyMembership")
                }

            }
        }
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling,this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult,this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mDashBoardData,this::onHandleAppDashBoardSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow:Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        currActivity.showToast(error,true)
    }

    private fun onHandleAppDashBoardSuccessResponse(appDashBoard: AppDashBoard?){
        appDashBoard?.let {
                llMain.visibility=View.VISIBLE
                setUpBanner(appDashBoard.banners!!)
                setTopPersonalTrainer(appDashBoard.topTrainers)
                setOutFitnessCenter(appDashBoard.fitnessCenters)
                AppSession.appDashBoard=appDashBoard

                if (appDashBoard.bookings!=null){
                    for (booking in appDashBoard.bookings!!){
                        binding.root.rv_manage_slots.visibility=View.VISIBLE
                        setManageSlot(appDashBoard.bookings)
                    }
                }

               // force update
               val currVersion = BuildConfig.VERSION_NAME
               val av = appDashBoard.version
                if (currVersion < av.toString()){
                    currActivity.showForceUpdateHomeDialog(av.toString())
                }

                 // Schedule workout and diet plan data

            if (appDashBoard.isDayRest==1){
                binding.root.lnrMyWorkout.visibility=View.GONE
                binding.root.lnrOnRest.visibility=View.VISIBLE
            }else{
               if (appDashBoard.myScheduled!!.workoutCompleted!=null){
                   binding.root.lnrMyWorkout.visibility=View.VISIBLE
                   setDietPlanWorkout(appDashBoard.myScheduled!!)
                   binding.root.llCheckWork_today_dietPlan.visibility=View.VISIBLE
               }else{
                   binding.root.lnrMyWorkout.visibility=View.GONE
                   binding.root.llCheckWork_today_dietPlan.visibility=View.GONE
               }
            }


                  //Plan Expire Date
            if(appDashBoard.fitnessCenterSubscription!=null){
                val currentTime: String = DateUtilss.serverDefaultDateFormat.format(Calendar.getInstance().time)
                val expiryTime= DateUtilss.convertDateFormat(appDashBoard.fitnessCenterSubscription!!.expiredAt, DateUtilss.serverDefaultDateFormat, DateUtilss.serverDateFormat)
                if(currentTime == expiryTime || currentTime  > currentTime  ) {
                    binding.root.llMembershipExpired.visibility=View.VISIBLE
                    val planExpiryDate= DateUtilss.convertDateFormat(appDashBoard.fitnessCenterSubscription!!.expiredAt, DateUtilss.serverDefaultDateFormat, DateUtilss.targetDateFormat)
                    binding.root.tvPlanExpireDate.text=planExpiryDate
                } else{
                    binding.root.llMembershipExpired.visibility=View.GONE
                }
            }else{
                binding.root.llMembershipExpired.visibility=View.GONE
            }


            when {
                AppSession.appDashBoard!!.fitnessCenterSubscription!=null -> {
                    llBuyMembership.visibility=View.GONE
                }
                else -> {
                    llBuyMembership.visibility=View.VISIBLE
                }
            }
            when {
                appDashBoard.offers!=null -> {
                    llOffer.visibility=View.VISIBLE
                    tvOfferMsg.text=appDashBoard.offers!!.offerName
                }
                else -> {
                    llOffer.visibility=View.GONE
                    llOffer.visibility=View.GONE
                }
            }


        }
    }

    /*  Set Banner for offer in this section*/
    private fun setUpBanner(banners: ArrayList<Banner>) {
        mAdapter = BannerAdapter(currActivity, banners,this)
        binding.root.vpBanner.adapter = mAdapter
        indicator_view.apply {
            setSliderColor(ContextCompat.getColor(currActivity,R.color.colorViewAllBg), ContextCompat.getColor(currActivity,R.color.colorPrimary))
            setSliderWidth(resources.getDimension(R.dimen.dimen_20))
            setSliderHeight(resources.getDimension(R.dimen.dimen_3))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setPageSize(binding.root.vpBanner!!.adapter!!.itemCount)
            notifyDataChanged()
        }

        binding.root.vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                indicator_view.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator_view.onPageSelected(position)
            }
        })
    }



    /* Set here top personal trainer recyclerview*/

    private fun setTopPersonalTrainer(topTrainers: ArrayList<AppDashBoard.TopTrainer>?) {
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
        rvPersonalTrainers.layoutManager = mLayout
        personalTrainerAdapter = TrainerAdapter(currActivity, topTrainers!!,this)
        rvPersonalTrainers.adapter = personalTrainerAdapter
    }


    /* set fitness center recyclerview here*/
    private fun setOutFitnessCenter(fitnessCenters: ArrayList<AppDashBoard.FitnessCenter>?) {
        val mLayout = GridLayoutManager(currActivity, 2)
        rvFitnessCenter.layoutManager = mLayout
        rvFitnessCenter.itemAnimator = null
        mFitnessCenterAdapter = CenterAdapter(currActivity, fitnessCenters!!, this)
        rvFitnessCenter.adapter = mFitnessCenterAdapter
    }



    /* set manage slot recyclerview here*/
    private fun setManageSlot(bookingDetailModel: ArrayList<BookingDetailModel>?) {
        val mLayout = LinearLayoutManager(currActivity, LinearLayoutManager.HORIZONTAL, false)
        rv_manage_slots.layoutManager = mLayout
        manageSlotAdapter = ManageSlotAdapter(currActivity, bookingDetailModel!!)
        rv_manage_slots.adapter = manageSlotAdapter
    }




    override fun onItemClickListener(data: Any) {
        if (data is AppDashBoard.TopTrainer)
            if(AppSession.appDashBoard!!.fitnessCenterSubscription!=null){
                TrainerDetailActivity.open(currActivity,data)
            } else{
                openBuyMemberShipDialog()
            }
        if (data is AppDashBoard.FitnessCenter){
            val currUser= AppPreferences.getInstance(currActivity).getUserDetails()
            if (currUser!=null){
                CenterDetailBookingActivity.open(currActivity,data)
            }else{
                LoginActivity.open(currActivity, "fromBuyMembership")
            }
        }

      }


    private fun openBuyMemberShipDialog() {
        val builder = AlertDialog.Builder(currActivity)
        val dialogView = currActivity.layoutInflater.inflate(R.layout.dialog_buy_membership, null)

        builder.setView(dialogView)
        builder.setCancelable(false)
        noMembershipAvlDialog = builder.create()
        noMembershipAvlDialog!!.show()

        dialogView.btnConfirmMembership.setOnClickListener {
            CenterActivity.open(currActivity)
            noMembershipAvlDialog!!.dismiss()
        }

        dialogView.ivClose.setOnClickListener {
            noMembershipAvlDialog!!.dismiss()
        }


        val window = noMembershipAvlDialog!!.window
        window!!.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    @SuppressLint("SetTextI18n")
    private fun setDietPlanWorkout(mySchedule: AppDashBoard.MyScheduled) {

        if (mySchedule.workouts!!.caloriesBurn!=null){
            tvBurned.text=mySchedule.workouts!!.caloriesBurn.toString()
        }

        if (mySchedule.workoutCompleted!!.caloriesBurn==null){
            /*    val calBurn=myDietPlan.myScheduled!!.workouts!!.caloriesBurn!!.toDouble()- 0
                tvCalsLeft.text=calBurn.toString()*/
        }else{
            val calBurn= mySchedule.workouts!!.caloriesBurn!!.toDouble()- mySchedule.workoutCompleted!!.caloriesBurn!!.toDouble()
            tvCalsLeft.text=calBurn.toString()
        }

        if (mySchedule.dietConsumed!!.calories!=null){
            tvConsumed.text= mySchedule.workoutCompleted!!.caloriesBurn.toString()
        }

        if(mySchedule.dietConsumed!!.calories!=null && mySchedule.dietPlans!!.calories!=null){
            val progress=(mySchedule.dietConsumed!!.calories!!.toDouble())/(mySchedule.dietPlans!!.calories!!.toDouble())
            workoutProgress.progress = progress.toInt()
        }else{
            workoutProgress.progress=0
        }

        /* Diet plan and diet consumed */
        if (mySchedule.dietPlans!!.proteins!=null){
            tvPlanProtien.text=mySchedule.dietPlans!!.proteins+currActivity.getString(R.string.g)
        } else{
            tvPlanProtien.text="0"+currActivity.getString(R.string.g)
        }

        if (mySchedule.dietPlans!!.carbs !=null){
            tvPlanCabs.text= mySchedule.dietPlans!!.carbs +currActivity.getString(R.string.g)
        } else{
            tvPlanCabs.text="0"+currActivity.getString(R.string.g)
        }

        if (mySchedule.dietPlans!!.fats!=null){
            tvPlanFat.text= mySchedule.dietPlans?.fats +currActivity.getString(R.string.g)
        } else{
            tvPlanFat.text="0"+currActivity.getString(R.string.g)
        }

        if (mySchedule.dietPlans?.calories!=null){
            tvPlanCalories.text=mySchedule.dietPlans?.calories+currActivity.getString(R.string.g)
        } else{
            tvPlanCalories.text="0"+currActivity.getString(R.string.g)
        }


        /* diet consumed */
        if (mySchedule.dietConsumed?.proteins!=null){
            tvDietConsumedProtien.text=mySchedule.dietConsumed!!.proteins+currActivity.getString(R.string.g)
        } else{
            tvDietConsumedProtien.text="0"+currActivity.getString(R.string.g)
        }

        if (mySchedule.dietConsumed!!.carbs!=null){
            tvDietConsumedCabs.text=mySchedule.dietConsumed!!.carbs+currActivity.getString(R.string.g)
        } else{
            tvDietConsumedCabs.text="0"+currActivity.getString(R.string.g)
        }

        if (mySchedule.dietConsumed!!.fats!=null){
            tvDietConsumedFat.text=mySchedule.dietConsumed!!.fats+currActivity.getString(R.string.g)
        } else{
            tvDietConsumedFat.text="0"+currActivity.getString(R.string.g)
        }

        if (mySchedule.dietConsumed!!.calories!=null){
            tvDietConsumedCalories.text=mySchedule.dietConsumed!!.calories+currActivity.getString(R.string.g)
        } else{
            tvDietConsumedCalories.text="0"+currActivity.getString(R.string.g)
        }


    }


}