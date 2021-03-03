package com.htf.diva.auth.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.base.BaseViewModel
import com.htf.diva.models.AboutModel
import com.htf.diva.models.AppDashBoard
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.eyenakhr.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class ProfileViewModel : BaseViewModel() {
    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mProfileDetailResponse= MutableLiveData<AboutModel>()

    fun profileDetails(){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.userProfileAsync(
                    AppSession.locale, AppSession.deviceId,
                    AppSession.deviceType, BuildConfig.VERSION_NAME)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is AboutModel)
                    mProfileDetailResponse.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }

    }


    fun onUpdateProfileImage(part: MultipartBody.Part){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                AuthApiRepo.userProfileImageUpdateAsync( AppSession.locale,
                    AppSession.deviceId, AppSession.deviceType,
                    BuildConfig.VERSION_NAME, part)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
                return@launch
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is AboutModel)
                    mProfileDetailResponse.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }


}