package com.htf.diva.auth.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.R
import com.htf.diva.auth.ui.OtpActivity
import com.htf.diva.base.BaseViewModel
import com.htf.diva.base.MyApplication
import com.htf.diva.utils.RegExp

class LoginViewModel:BaseViewModel() {
    val mMobile = MutableLiveData<String>("")
    val errorValidateRes = MutableLiveData<String>()

    private fun checkValidation(): Boolean {
        var isValid = true
        when {
            RegExp.chkEmpty(mMobile.value!!) -> {
                isValid = false
                val msg = MyApplication.getAppContext().getString(R.string.mobile_no_required)
                errorValidateRes.postValue(msg)
            }
            !RegExp.isValidEmail(mMobile.value!!) -> {
                isValid = false
                val msg = MyApplication.getAppContext().getString(R.string.invalid_mobile)
                errorValidateRes.postValue(msg)
            }

        }
        return isValid

    }

    fun onLoginClick(){
        if (checkValidation()){


        }

    }
}