package com.htf.diva.auth.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.htf.diva.R
import com.htf.diva.auth.viewModel.AboutViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.dashboard.ui.HomeActivity
import com.htf.diva.databinding.ActivityAboutYouBinding
import kotlinx.android.synthetic.main.activity_about_you.*
import kotlinx.android.synthetic.main.toolbar.*

class AboutYouActivity : BaseDarkActivity<ActivityAboutYouBinding,AboutViewModel>(AboutViewModel::class.java) {
    private var currActivity: Activity =this

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,AboutYouActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_about_you
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.aboutViewModel=viewModel
        //setContentView(R.layout.activity_about_you)
        tvTitle.text=getString(R.string.about_you)
        btnContinue.setOnClickListener {
            HomeActivity.open(currActivity)
        }
    }


}