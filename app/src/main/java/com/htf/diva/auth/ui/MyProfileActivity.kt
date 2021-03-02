package com.htf.diva.auth.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.auth.viewModel.ProfileViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.databinding.ActivityMyProfileBinding
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfileActivity : BaseDarkActivity<ActivityMyProfileBinding, ProfileViewModel>(
    ProfileViewModel::class.java),
    View.OnClickListener {
    private var currActivity: Activity =this

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,MyProfileActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_my_profile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_profile)
        setListener()
    }

    private fun setListener() {
        btnEditProfile.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btnEditProfile->{
              //  EditProfileActivity.open(currActivity)
            }
        }
    }
}