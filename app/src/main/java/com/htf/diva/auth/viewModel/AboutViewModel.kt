package com.htf.diva.auth.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseViewModel
import com.htf.diva.base.MyApplication
import com.htf.diva.dashboard.ui.HomeActivity
import com.htf.diva.models.AboutModel
import com.htf.diva.models.UserData
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.diva.utils.RegExp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AboutViewModel:BaseViewModel() {
    val isApiCalling = MutableLiveData<Boolean>()
    val errorResult = MutableLiveData<String>()
    val errorValidateRes = MutableLiveData<String>()
    val mName = MutableLiveData<String>("")
    val mAge = MutableLiveData<String>("")
    val mHeight = MutableLiveData<String>("")
    val mWeight = MutableLiveData<String>("")
    val mAboutUsData = MutableLiveData<AboutModel>()


    private fun checkValidation(): Boolean {
        var isValid = true
        when {
            RegExp.chkEmpty(mName.value!!) -> {
                isValid = false
                val msg = MyApplication.getAppContext().getString(R.string.enter_your_name)
                errorValidateRes.postValue(msg)
            }

        }
        return isValid

    }

    fun onContinueClick(){
        if (checkValidation()) {
            if (!DialogUtils.isInternetOn()){
                isInternetOn.postValue(false)
                return
            }
            isApiCalling.postValue(true)
            scope.launch {
                val result = try {
                    AuthApiRepo.userAboutUsAsync(
                        AppSession.locale,
                        AppSession.deviceId, AppSession.deviceType,
                        BuildConfig.VERSION_NAME,
                        mName.value.toString(),mAge.value.toString(),mHeight.value.toString(),mWeight.value.toString())
                } catch (e: Exception) {
                    errorResult.postValue(e.localizedMessage)
                    isApiCalling.postValue(false)
                }
                withContext(Dispatchers.Main) {
                    isApiCalling.postValue(false)
                    if (result is AboutModel)
                        mAboutUsData.postValue(result)
                    else
                        errorResult.postValue(result.toString())
                }

            }
        }

    }
}