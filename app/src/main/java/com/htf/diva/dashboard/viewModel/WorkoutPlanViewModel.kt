package com.htf.diva.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.base.BaseViewModel
import com.htf.diva.models.*
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.eyenakhr.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorkoutPlanViewModel: BaseViewModel() {


    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val myWorkOutPlanData= MutableLiveData<MyWorkoutPlanModel>()
    val mWorkoutWeekDaysResponse= MutableLiveData<ArrayList<WorkoutWeekDaysModel>>()
    val mWorkoutWeekDaysData= MutableLiveData<Any>()


    fun myWorkoutPlan(){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.myWorkOutPlan(AppSession.locale, AppSession.deviceId,
                        AppSession.deviceType, BuildConfig.VERSION_NAME)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is MyWorkoutPlanModel)
                    myWorkOutPlanData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }


    fun workoutWeekdaysList() {
        if (!DialogUtils.isInternetOn()) {
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.workoutWeekdaysAsync(
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
                    mWorkoutWeekDaysResponse.postValue(result as ArrayList<WorkoutWeekDaysModel>)
                else
                    errorResult.postValue(result.toString())
            }
        }
    }


    fun onUpdateWorkoutClick(
        locale: String?,
        deviceId: String?,
        deviceType: String?,
        versionName: String?,
        weekDayId: String?,
        workoutWeekDay: HashMap<String, String?>) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.updateWorkoutWeekDay( locale, deviceId, deviceType,
                    versionName, weekDayId,workoutWeekDay)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is Any)
                    mWorkoutWeekDaysData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }



}