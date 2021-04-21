package com.htf.diva.dashboard.ui

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.htf.diva.R
import com.htf.diva.auth.ui.LoginActivity
import com.htf.diva.auth.ui.MyProfileActivity
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.base.MyApplication
import com.htf.diva.dashboard.fragments.DietFragment
import com.htf.diva.dashboard.fragments.HomeFragment
import com.htf.diva.dashboard.fragments.MembershipFragment
import com.htf.diva.dashboard.fragments.WorkoutFragment
import com.htf.diva.dashboard.payment.PaymentHistoryActivity
import com.htf.diva.dashboard.viewModel.HomeViewModel
import com.htf.diva.databinding.ActivityHomeBinding
import com.htf.diva.netUtils.Constants
import com.htf.diva.netUtils.Constants.Auth.KEY_TOKEN
import com.htf.diva.utils.AppPreferences
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.observerViewModel
import com.htf.diva.utils.showToast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.ivUser
import kotlinx.android.synthetic.main.drawer_menu.view.*

class HomeActivity : BaseDarkActivity<ActivityHomeBinding,HomeViewModel>(HomeViewModel::class.java),
    View.OnClickListener {
    private var currActivity: Activity = this
    private val ID_HOME = 1
    private val ID_MEMBERSHIP = 2
    private val ID_WORKOUT = 3
    private val ID_DIET = 4
    private var comeFrom: String?=""

    companion object {
        fun open(currActivity: Activity, comeFrom: String?) {
            val intent = Intent(currActivity, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("comeFrom", comeFrom)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_home
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.homeViewModel = viewModel
        setListener()
        viewModelInitialize()
        getExtra()
        if (currUser!=null){
            Glide.with(currActivity).load(Constants.Urls.USER_IMAGE_URL + currUser.user!!.profileImage).centerCrop()
                .placeholder(R.drawable.user).into(ivUser)
        }

        bottomNavigation.onItemSelected = {
          println("selectedIndex->$it")
            when(it){
                0->{
                    changeFragment("",HomeFragment())
                }
                1->{
                    changeFragment("",MembershipFragment())
                }
                2->{
                    changeFragment("",WorkoutFragment())
                }
                3->{
                    changeFragment("",DietFragment())
                }
            }
        }

        bottomNavigation.onItemReselected = {

        }


    }

    private fun getExtra() {
        comeFrom = intent.getStringExtra("comeFrom")
        if(comeFrom=="memberShipTab"){
            bottomNavigation.itemActiveIndex=1
            changeFragment("",MembershipFragment())
         }else{
            changeFragment("",HomeFragment())
        }
    }


    private fun setListener() {
        ivMenu.setOnClickListener(this)
        ivUser.setOnClickListener(this)
        ivNotification.setOnClickListener(this)
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.mLogoutResponse, this::onHandleLogoutResponse)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)
    }


    private fun onHandleLogoutResponse(res: Any?){
        AppSession.userToken=""
        AppPreferences.getInstance(MyApplication.getAppContext()).clearFromPref(KEY_TOKEN)
        AppPreferences.getInstance(MyApplication.getAppContext()).clearUserDetails()
        LoginActivity.open(currActivity)
        finish()
    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String){
        showToast(error, true)
    }

    private fun changeFragment(s:String,fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val ft=fragmentManager.beginTransaction()
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        ft.replace(R.id.container,fragment)
        ft.commit()
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.ivMenu->{
               openDrawer()
            }
            R.id.ivUser->{
                when {
                    currUser!=null -> {
                        MyProfileActivity.open(currActivity)
                    }
                    else -> {
                        LoginActivity.open(currActivity)
                    }
                }
            }

            R.id.ivNotification->{
                NotificationActivity.open(currActivity)
           }
        }
    }


    private fun openDrawer() {
        var builder: Dialog?=null

        builder = if (AppSession.locale=="ar"){
            Dialog(currActivity, R.style.DrawerDialogArabic)
        }else{
            Dialog(currActivity, R.style.DrawerDialog)
        }

        val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()

        val dialogView = currActivity.layoutInflater.inflate(R.layout.drawer_menu, null)
        builder.setContentView(dialogView)
        builder.setCancelable(true)
        val dialog = builder.show()

        dialogView.btnCancel.setOnClickListener {
            builder.dismiss()
        }

        if (currUser!=null){
            dialogView.tvUserName.text= currUser.user!!.name
            Glide.with(currActivity).load(Constants.Urls.USER_IMAGE_URL + currUser.user!!.profileImage).centerCrop()
                .placeholder(R.drawable.user).into(dialogView.ivUser)
        }

        dialogView.llPaymentHistory.setOnClickListener {
            PaymentHistoryActivity.open(currActivity)
            builder.dismiss()
        }

        dialogView.btnLogout.setOnClickListener {
            viewModel.logoutUser()
            builder.dismiss()
        }

        dialogView.llCustomerSupport.setOnClickListener {
            CustomerSupportActivity.open(currActivity)
            builder.dismiss()
        }

        dialogView.llAbout.setOnClickListener {
            AboutUsActivity.open(currActivity)
            builder.dismiss()
        }

        dialogView.llProfileInfo.setOnClickListener {
            MyProfileActivity.open(currActivity)
            builder.dismiss()
        }
       dialogView.llPaymentHistory.setOnClickListener {
           PaymentHistoryActivity.open(currActivity)
            builder.dismiss()
        }


        builder.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        builder.window!!.setGravity(Gravity.FILL)
        builder.window!!.setBackgroundDrawable(ContextCompat.getDrawable(currActivity, R.color.colorPrimaryDark))
        builder.show()

    }

}



