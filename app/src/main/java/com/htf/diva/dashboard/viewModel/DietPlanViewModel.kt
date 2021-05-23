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
    val mDietPlanResponseData= MutableLiveData<Any>()
    val mConsumedDietPlanData= MutableLiveData<Any>()


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


    fun onUpdateDietPlanClick(
        locale: String?,
        deviceId: String?,
        deviceType: String?,
        versionName: String?,
        weekDayId: String?,meal_type_id:String?,
        dietPlan: HashMap<String, String?>) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.updateDietPlan( locale, deviceId, deviceType,
                    versionName, weekDayId,meal_type_id,dietPlan)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is Any)
                    mDietPlanResponseData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }

    fun onUpdateConsumedDietClick(
        locale: String?,
        deviceId: String?,
        deviceType: String?,
        versionName: String?,
        mealTypeId: HashMap<String, String?>){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.updateConsumeDietPLan(locale,deviceId,deviceType,
                    versionName,mealTypeId)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is Any)
                    mConsumedDietPlanData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }

}