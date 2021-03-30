package com.htf.diva.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import com.htf.diva.base.BaseViewModel
import com.htf.diva.models.BookingDetailModel
import com.htf.diva.models.FitnessCenterDetailForBookModel
import com.htf.diva.utils.DialogUtils
import com.htf.eyenakhr.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookingDetailsViewModel :BaseViewModel(){

    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mBookingDetailData= MutableLiveData<BookingDetailModel>()

    fun bookingDetail(locale: String?, deviceId: String?, deviceType: String?, versionName: String?){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.bookingDetail(
                    locale,deviceId,deviceType,versionName
                )
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is BookingDetailModel)
                    mBookingDetailData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }



}