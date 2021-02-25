package com.htf.diva.auth.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.base.BaseViewModel
import com.htf.diva.models.UserData
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import java.lang.Exception

class OtpViewModel:BaseViewModel() {

    val errorResult = MutableLiveData<String>()
    val isApiCalling= MutableLiveData<Boolean>()
    val mResendOtpData= MutableLiveData<Any>()
    val mHashToken= MutableLiveData<String>()
    val mFcmId = MutableLiveData<String>("")
    val mOtp = MutableLiveData<String>("")

   /* fun verifyOtp(hashToken:String,otp:String){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result=try {
                AuthApiRepo.userVerifyOtpAsync(AppSession.deviceId,AppSession.deviceType,AppSession.locale,
                    BuildConfig.VERSION_NAME,"","","",mFcmId.toString())
            }catch (e: Exception){
                isApiCalling.postValue(false)
                errorResult.postValue(e.localizedMessage)
            }
        }
    }*/
/*
    fun onResendOtpButtonClick(){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result=try {
                AuthApiRepo.userResendOtpAsync(mHashToken.value.toString())
            }catch (e: Exception){
                isApiCalling.postValue(false)
                errorResult.postValue(e.localizedMessage)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is UserData)
                    mLoginData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }
        }
    }*/
}