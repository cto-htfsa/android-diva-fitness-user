package com.htf.diva.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.base.BaseViewModel
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.Listing
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.eyenakhr.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonalTrainerViewModel :BaseViewModel() {

    val isApiCalling = MutableLiveData<Boolean>()
    val errorResult = MutableLiveData<String>()
    val mNotificationData= MutableLiveData<Listing<AppDashBoard.TopTrainer>>()

    fun onGetTrainerListing(page:Int,isProgressBar:Boolean) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(isProgressBar)
        scope.launch {
            val result = try {
                DashboardApiRepo.personalTrainers(
                    AppSession.locale, AppSession.deviceId,
                    AppSession.deviceType, BuildConfig.VERSION_NAME,"","",page)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is Listing<*>)
                    mNotificationData.postValue(result as Listing<AppDashBoard.TopTrainer>)
                else
                    errorResult.postValue(result.toString())
            }

        }

    }


}