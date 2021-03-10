package com.htf.diva.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.diva.R
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.viewModel.PersonalTrainerViewModel
import com.htf.diva.databinding.ActivityPersonalTrainersBinding
import com.htf.diva.databinding.ActivityTrainerDetailsBinding
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.toolbar.*

class TrainerDetailActivity : BaseDarkActivity<ActivityTrainerDetailsBinding, PersonalTrainerViewModel>(
    PersonalTrainerViewModel::class.java), View.OnClickListener{
    private var currActivity: Activity = this

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, TrainerDetailActivity::class.java)
            currActivity.startActivity(intent)
        }
    }


    override var layout = R.layout.activity_trainer_details
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.trainerDetailViewModel = viewModel
        setListener()

    }
    private fun setListener() {

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}