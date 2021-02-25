package com.htf.diva.auth.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.base.BaseViewModel
import com.htf.diva.models.AppSetting
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel:BaseViewModel() {
    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult = MutableLiveData<String>()
    val mAppVersionData= MutableLiveData<AppSetting>()


    fun onGetAppVersion(isProgressBar: Boolean){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(isProgressBar)
        scope.launch {
            val result = try {
                AuthApiRepo.appSetting(AppSession.deviceId,AppSession.deviceType,AppSession.locale, BuildConfig.VERSION_NAME)
            } catch (e: Exception) {
                isApiCalling.postValue(false)
                errorResult.postValue(e.localizedMessage)
                return@launch
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is AppSetting)
                    mAppVersionData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }

    }


}