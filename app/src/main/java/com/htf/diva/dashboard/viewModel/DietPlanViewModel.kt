package com.htf.diva.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.base.BaseViewModel
import com.htf.diva.models.DietWeekdayModel
import com.htf.diva.models.MyDietModel
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.eyenakhr.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DitPlanViewModel : BaseViewModel() {

    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mDietWeekDaysResponse= MutableLiveData<ArrayList<DietWeekdayModel>>()
    val mMyDietData= MutableLiveData<MyDietModel>()

    fun dietWeekdaysList() {
        if (!DialogUtils.isInternetOn()) {
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.dietWeekdaysAsync(
                    AppSession.locale, AppSession.deviceId,
                    AppSession.deviceType, BuildConfig.VERSION_NAME
                )
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is ArrayList<*>)
                    mDietWeekDaysResponse.postValue(result as ArrayList<DietWeekdayModel>)
                else
                    errorResult.postValue(result.toString())
            }
        }
    }


    fun myDietList(selectedDate: String) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.myDietPlan(AppSession.locale, AppSession.deviceId,
                    AppSession.deviceType, BuildConfig.VERSION_NAME,selectedDate)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is MyDietModel)
                    mMyDietData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }





}