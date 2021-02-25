package com.htf.diva.auth.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.auth.ui.OtpActivity
import com.htf.diva.base.BaseViewModel
import com.htf.diva.base.MyApplication
import com.htf.diva.models.UserData
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.diva.utils.RegExp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel:BaseViewModel() {
    val isApiCalling = MutableLiveData<Boolean>()
    val errorResult = MutableLiveData<String>()
    val errorValidateRes = MutableLiveData<String>()
    val mMobile = MutableLiveData<String>("")
    val mLoginData = MutableLiveData<UserData>()


    private fun checkValidation(): Boolean {
        var isValid = true
        when {
            RegExp.chkEmpty(mMobile.value!!) -> {
                isValid = false
                val msg = MyApplication.getAppContext().getString(R.string.invalid_mobile)
                errorValidateRes.postValue(msg)
            }

        }
        return isValid

    }

    fun onLoginClick(){
        if (checkValidation()) {
            if (!DialogUtils.isInternetOn()){
                isInternetOn.postValue(false)
                return
            }
            isApiCalling.postValue(true)
            scope.launch {
                val result = try {
                    AuthApiRepo.userLoginAsync(AppSession.locale,
                        AppSession.deviceId, AppSession.deviceType,
                        BuildConfig.VERSION_NAME,"966",
                        mMobile.value.toString())
                } catch (e: Exception) {
                    errorResult.postValue(e.localizedMessage)
                    isApiCalling.postValue(false)
                }
                withContext(Dispatchers.Main) {
                    isApiCalling.postValue(false)
                    if (result is UserData)
                        mLoginData.postValue(result)
                    else
                        errorResult.postValue(result.toString())
                }

            }
        }

    }
}