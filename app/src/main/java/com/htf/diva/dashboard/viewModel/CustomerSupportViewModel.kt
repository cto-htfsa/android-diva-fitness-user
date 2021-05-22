package com.htf.diva.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseViewModel
import com.htf.diva.base.MyApplication
import com.htf.diva.models.AboutModel
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.UserData
import com.htf.diva.utils.AppPreferences
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.diva.utils.RegExp
import com.htf.eyenakhr.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class CustomerSupportViewModel : BaseViewModel() {


    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mCustomerSupport= MutableLiveData<Any>()
    val mName = MutableLiveData<String>("")
    val mDialCode = MutableLiveData<String>("")
    val mNumber = MutableLiveData<String>("")
    val mMessage = MutableLiveData<String>("")
    val errorValidateRes = MutableLiveData<String>()
    val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
    val mAppAboutUs=MutableLiveData<AboutModel>()


    private fun checkValidation(): Boolean {
        var isValid = true
        when {
            RegExp.chkEmpty(mName.value!!) -> {
                isValid = false
                val msg = MyApplication.getAppContext().getString(R.string.enter_your_name)
                errorValidateRes.postValue(msg)
            }

            RegExp.chkEmpty(mNumber.value!!) -> {
                isValid = false
                val msg = MyApplication.getAppContext().getString(R.string.mobile_no_required)
                errorValidateRes.postValue(msg)
            }

            RegExp.chkEmpty(mMessage.value!!) -> {
                isValid = false
                val msg = MyApplication.getAppContext().getString(R.string.type_message)
                errorValidateRes.postValue(msg)
            }
        }
        return isValid

    }


    fun onCustomerSupportClick() {
        if (checkValidation()) {
            if(!DialogUtils.isInternetOn()) {
                isInternetOn.postValue(false)
                return
            }
            isApiCalling.postValue(true)
            scope.launch {
                val result = try {
                    DashboardApiRepo.userCustomerSupportAsync(
                        AppSession.locale,
                        AppSession.deviceId,
                        AppSession.deviceType,
                        BuildConfig.VERSION_NAME,
                        mName.value.toString(),
                        "966",
                        mNumber.value.toString(),
                        mMessage.value.toString()
                    )
                } catch (e: Exception) {
                    errorResult.postValue(e.localizedMessage)
                    isApiCalling.postValue(false)
                }

                withContext(Dispatchers.Main) {
                    isApiCalling.postValue(false)
                    if (result is IOException)
                        errorResult.postValue(result.toString())
                    else
                        mCustomerSupport.postValue(result.toString())
                }
            }
        }
    }


    fun appAboutUs(locale: String?, deviceId: String?, deviceType: String?, versionName: String?){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.appAboutUs(locale,deviceId,deviceType,versionName)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is AboutModel)
                    mAppAboutUs.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }



}