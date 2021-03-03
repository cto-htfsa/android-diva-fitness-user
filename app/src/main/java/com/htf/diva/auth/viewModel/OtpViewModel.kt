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
import java.lang.Exception

class OtpViewModel:BaseViewModel() {

    val errorResult = MutableLiveData<String>()
    val isApiCalling=MutableLiveData<Boolean>()
    val mVerifyOtpData=MutableLiveData<UserData>()
    val mResendOtpData=MutableLiveData<Any>()
    val mHashToken=MutableLiveData<String>()
    val mUserId=MutableLiveData<String>()
    val mFcmId = MutableLiveData<String>("")



    fun verifyOtp(hashToken: String, otp: String, userId: String?){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result=try {
                AuthApiRepo.userVerifyOtpAsync(AppSession.deviceId, AppSession.deviceType,AppSession.locale,
                    BuildConfig.VERSION_NAME,userId!! ,hashToken,otp,  mFcmId.value.toString())
            }catch (e:Exception){
                isApiCalling.postValue(false)
                errorResult.postValue(e.localizedMessage)
            }
            withContext(Dispatchers.Main){
                isApiCalling.postValue(false)
                if (result is UserData)
                    mVerifyOtpData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }
        }
    }

    fun onResendOtpButtonClick(){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result=try {
                AuthApiRepo.userResendOtpAsync(AppSession.deviceId,AppSession.deviceType,AppSession.locale,
                    BuildConfig.VERSION_NAME,mUserId.value.toString(),mHashToken.value.toString())
            }catch (e:Exception){
                isApiCalling.postValue(false)
                errorResult.postValue(e.localizedMessage)
            }
            withContext(Dispatchers.Main){
                isApiCalling.postValue(false)
                if (result is UserData)
                    mResendOtpData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }
        }
    }


}