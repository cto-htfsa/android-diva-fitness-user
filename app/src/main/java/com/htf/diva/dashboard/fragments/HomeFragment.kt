package com.htf.diva.dashboard.fragments

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseFragment
import com.htf.diva.dashboard.adapters.BannerAdapter
import com.htf.diva.dashboard.adapters.CenterAdapter
import com.htf.diva.dashboard.adapters.TrainerAdapter
import com.htf.diva.dashboard.bookFitnessCenter.CenterActivity
import com.htf.diva.dashboard.bookFitnessCenter.CenterDetailBookingActivity
import com.htf.diva.dashboard.ui.PersonalTrainersActivity
import com.htf.diva.dashboard.bookTrainer.TrainerDetailActivity
import com.htf.diva.dashboard.viewModel.HomeViewModel
import com.htf.diva.databinding.FragmentHomeBinding
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.Banner
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import com.htf.diva.callBack.IListItemClickListener
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.dialog_buy_membership.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class.java),
    IListItemClickListener<Any>,View.OnClickListener {
    private lateinit var currActivity: Activity
    lateinit var binding: FragmentHomeBinding
    private lateinit var personalTrainerAdapter: TrainerAdapter
    private lateinit var mFitnessCenterAdapter: CenterAdapter
    private var dots = ArrayList<ImageView>()
    private var arrBanner = ArrayList<Banner>()
    private lateinit var mAdapter: BannerAdapter
    var cartExpDialog:AlertDialog?=null

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
            val nameStr=getString(R.string.hey_name)
            binding.root.tvHeyMsg.text=nameStr.replace("[x]", currUser.user!!.name!!)
        }
        return binding.root
    }

    private fun setListener() {
        binding.root.btnViewAll.setOnClickListener(this)
        binding.root.llBuyMembership.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnViewAll -> {
                PersonalTrainersActivity.open(currActivity,Constants.FROM_HOME)
            }
            R.id.llBuyMembership->{
                CenterActivity.open(currActivity)
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
                tvOfferMsg.text=appDashBoard.offers!!.offerName
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

    override fun onItemClickListener(data: Any) {
        if (data is AppDashBoard.TopTrainer)
            if ( AppSession.appDashBoard!!.fitnessCenterSubscription!=null){
                TrainerDetailActivity.open(currActivity,data)
            } else{
                openBuyMemberShipDialog()
            }
        if (data is AppDashBoard.FitnessCenter)
            CenterDetailBookingActivity.open(currActivity,data)
      }


    private fun openBuyMemberShipDialog() {
        val builder = AlertDialog.Builder(currActivity)
        val dialogView = currActivity.layoutInflater.inflate(R.layout.dialog_buy_membership, null)

        builder.setView(dialogView)
        builder.setCancelable(false)
        cartExpDialog = builder.create()
        cartExpDialog!!.show()


        dialogView.ivClose.setOnClickListener {
            cartExpDialog!!.dismiss()
        }


        val window = cartExpDialog!!.window
        window!!.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }


}