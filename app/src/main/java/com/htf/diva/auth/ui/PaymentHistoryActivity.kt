package com.htf.diva.auth.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.htf.diva.R
import com.htf.diva.auth.viewModel.EditProfileViewModel
import com.htf.diva.auth.viewModel.PaymentHistoryViewModel
import com.htf.diva.base.BaseDarkActivity
import com.htf.diva.databinding.ActivityPaymentHistoryBinding
import com.htf.diva.models.AboutModel
import kotlinx.android.synthetic.main.toolbar.*

class PaymentHistoryActivity : BaseDarkActivity<ActivityPaymentHistoryBinding, PaymentHistoryViewModel>(
    PaymentHistoryViewModel::class.java)  {


    private var currActivity: Activity =this
    private val mEditProfileViewModel by lazy { getViewModel<PaymentHistoryViewModel>() }

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,PaymentHistoryActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override var layout = R.layout.activity_payment_history
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.paymentViewModel = viewModel
        tvTitle.text = getString(R.string.payment_history)

    }

}