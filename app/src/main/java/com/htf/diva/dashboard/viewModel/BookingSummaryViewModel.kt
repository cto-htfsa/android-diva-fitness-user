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

class BookingSummaryViewModel : BaseViewModel() {

    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mUpComingBookingResponse= MutableLiveData<Listing<UpComingBookingModel>>()
    val mCompletedBookingResponse= MutableLiveData<Listing<CompletedBookingModel>>()


    fun onUpComingBookingListing(page:Int,isProgressBar:Boolean) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(isProgressBar)
        scope.launch {
            val result = try {
                DashboardApiRepo.upComingBooking(
                    AppSession.locale, AppSession.deviceId,
                    AppSession.deviceType, BuildConfig.VERSION_NAME,page)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is Listing<*>)
                    mUpComingBookingResponse.postValue(result as Listing<UpComingBookingModel>)
                else
                    errorResult.postValue(result.toString())
            }

        }

    }


    fun onCompletedBookingListing(page:Int,isProgressBar:Boolean) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(isProgressBar)
        scope.launch {
            val result = try {
                DashboardApiRepo.completedBooking(
                    AppSession.locale, AppSession.deviceId,
                    AppSession.deviceType, BuildConfig.VERSION_NAME,page)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is Listing<*>)
                    mCompletedBookingResponse.postValue(result as Listing<CompletedBookingModel>)
                else
                    errorResult.postValue(result.toString())
            }

        }

    }


}