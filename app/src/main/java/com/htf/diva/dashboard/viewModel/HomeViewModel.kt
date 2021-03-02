package com.htf.diva.dashboard.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.base.BaseViewModel
import com.htf.diva.base.MyApplication
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.UserData
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.eyenakhr.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class HomeViewModel:BaseViewModel() {

    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mDashBoardData=MutableLiveData<AppDashBoard>()
    val mLogoutResponse=MutableLiveData<Any>()


    fun appDashBoard(locale: String?, deviceId: String?, deviceType: String?, versionName: String?){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.appDashBoard(
                    locale,deviceId,deviceType,versionName
                )
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is AppDashBoard)
                    mDashBoardData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }


    fun logoutUser(){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result=try {
                DashboardApiRepo.userLogoutAsync(AppSession.locale, AppSession.deviceId, AppSession.deviceType, BuildConfig.VERSION_NAME)
            }catch (e:Exception){
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }

            withContext(Dispatchers.Main){
                isApiCalling.postValue(false)
                if (result is IOException)
                    errorResult.postValue(result.toString())
                else
                    mLogoutResponse.postValue(result.toString())
            }
        }
    }

}