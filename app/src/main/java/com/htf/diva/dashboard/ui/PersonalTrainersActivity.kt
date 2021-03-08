package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.adapters.NotificationAdapter
import com.htf.diva.dashboard.viewModel.NotificationViewModel
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivityNotificationBinding
import com.htf.diva.databinding.ActivityPersonalTrainersBinding
import kotlinx.android.synthetic.main.toolbar.*

class PersonalTrainersActivity : BaseDarkActivity<ActivityPersonalTrainersBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java), View.OnClickListener {
    private var currActivity: Activity = this


    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, PersonalTrainersActivity::class.java)
            currActivity.startActivity(intent)
        }
    }


    override var layout = R.layout.activity_personal_trainers
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text=getString(R.string.personal_trainers)
        binding.personalTrainerViewModel = viewModel

    }


    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}