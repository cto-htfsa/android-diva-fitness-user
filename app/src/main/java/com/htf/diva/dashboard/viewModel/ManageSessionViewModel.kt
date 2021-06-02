package com.htf.diva.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.base.BaseViewModel
import com.htf.diva.models.MyWorkoutPlanModel
import com.htf.diva.utils.DialogUtils
import com.htf.eyenakhr.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManageSessionViewModel : BaseViewModel(){

    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mDeleteBookedData= MutableLiveData<Any>()

    fun deleteBookedSlotClick(
        locale: String?,
        deviceId: String?,
        deviceType: String?,
        versionName: String?,
        slot_id: String?) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.deleteBookedSession( locale, deviceId, deviceType,
                    versionName,slot_id)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is Any)
                    mDeleteBookedData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }

}