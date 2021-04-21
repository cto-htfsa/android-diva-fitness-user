package com.htf.diva.auth.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.base.BaseViewModel
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.Listing
import com.htf.diva.models.PaymentHistoryModel
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.eyenakhr.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentHistoryViewModel: BaseViewModel() {

    val isApiCalling = MutableLiveData<Boolean>()
    val errorResult = MutableLiveData<String>()
    val mPaymentHistoryData= MutableLiveData<Listing<PaymentHistoryModel>>()

    fun paymentHistoryListing(page:Int,isProgressBar:Boolean) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(isProgressBar)
        scope.launch {
            val result = try {
                DashboardApiRepo.paymentHistory(
                        AppSession.locale, AppSession.deviceId,
                        AppSession.deviceType, BuildConfig.VERSION_NAME,page)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is Listing<*>)
                    mPaymentHistoryData.postValue(result as Listing<PaymentHistoryModel>)
                else
                    errorResult.postValue(result.toString())
            }

        }

    }

}