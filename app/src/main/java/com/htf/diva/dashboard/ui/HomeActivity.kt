package com.htf.diva.dashboard.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.fragment.app.Fragment
import com.htf.diva.R
import com.htf.diva.auth.ui.MyProfileActivity
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.base.MyApplication
import com.htf.diva.dashboard.fragments.DietFragment
import com.htf.diva.dashboard.fragments.HomeFragment
import com.htf.diva.dashboard.fragments.MembershipFragment
import com.htf.diva.dashboard.fragments.WorkoutFragment
import com.htf.diva.dashboard.viewModel.HomeViewModel
import com.htf.diva.databinding.ActivityHomeBinding
import com.htf.diva.models.Dashboard
import com.htf.diva.utils.AppPreferences
import com.simform.custombottomnavigation.SSCustomBottomNavigation
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_menu.view.*

class HomeActivity : BaseDarkActivity<ActivityHomeBinding,HomeViewModel>(HomeViewModel::class.java),
    View.OnClickListener {
    private var currActivity: Activity = this
    private val ID_HOME = 1
    private val ID_MEMBERSHIP = 2
    private val ID_WORKOUT = 3
    private val ID_DIET = 4


    companion object {
        fun open(currActivity: Activity) {
            val intent = Intent(currActivity, HomeActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_home
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.homeViewModel = viewModel
        setBottomBar()
        setListener()

      //  binding.bottomNavigation.show(ID_HOME,true)
     //   bottomNavigation.show(ID_HOME)

    }

    private fun setListener() {
        ivMenu.setOnClickListener(this)
        ivUser.setOnClickListener(this)

    }


    private fun setBottomBar() {
        binding.bottomNavigation.apply {
            add(
                SSCustomBottomNavigation.Model(
                    ID_HOME,
                    R.drawable.ic_home_deselect,
                    getString(R.string.home)
                )
            )
            add(
                SSCustomBottomNavigation.Model(
                    ID_MEMBERSHIP,
                    R.drawable.membership,
                    getString(R.string.membership)
                )
            )
            add(
                SSCustomBottomNavigation.Model(
                    ID_WORKOUT,
                    R.drawable.workout,
                    getString(R.string.workout)
                )
            )
            add(
                SSCustomBottomNavigation.Model(
                    ID_DIET,
                    R.drawable.diet_unselect,
                    getString(R.string.diet)
                )
            )


            setOnShowListener {
                val name = when (it.id) {
                    ID_HOME -> changeFragment("",HomeFragment())
                    ID_MEMBERSHIP -> getString(R.string.membership)
                    ID_WORKOUT -> getString(R.string.workout)
                    ID_DIET -> getString(R.string.diet)

                    else -> ""

                }

                val bgColor = when (it.id) {
                    ID_HOME -> ContextCompat.getColor(currActivity, R.color.colorText)
                    ID_MEMBERSHIP -> ContextCompat.getColor(currActivity, R.color.colorText)
                    ID_WORKOUT -> ContextCompat.getColor(currActivity, R.color.colorText)
                    ID_DIET -> ContextCompat.getColor(currActivity, R.color.colorText)
                    else -> ContextCompat.getColor(currActivity, R.color.colorText)
                }
            }
         /*   setOnClickMenuListener {
                 *//*   ID_MEMBERSHIP ->  changeFragment("",MembershipFragment())
                    ID_WORKOUT ->  changeFragment("",WorkoutFragment())
                    ID_DIET ->  changeFragment("",DietFragment())*//*
                    //else ->  ""
                }*/
        }

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
                MyProfileActivity.open(currActivity)
            }

        }
    }


    private fun openDrawer() {
        val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
        val builder = AlertDialog.Builder(currActivity, R.style.AppTheme_Transparent)
        val dialogView = currActivity.layoutInflater.inflate(R.layout.drawer_menu, null)
        builder.setView(dialogView)
        builder.setCancelable(true)
        val dialog = builder.show()

        dialogView.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        val window = dialog.window
        window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.FILL)
        window.setBackgroundDrawable(ContextCompat.getDrawable(currActivity, R.color.colorPrimaryDark))


    }

}



