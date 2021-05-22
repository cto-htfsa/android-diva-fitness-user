package com.htf.diva.dashboard.viewModel

import android.media.Rating
import androidx.lifecycle.MutableLiveData
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.BaseViewModel
import com.htf.diva.base.MyApplication
import com.htf.diva.models.BookingDetailModel
import com.htf.diva.models.FitnessCenterDetailForBookModel
import com.htf.diva.utils.AppSession
import com.htf.diva.utils.DialogUtils
import com.htf.diva.utils.RegExp
import com.htf.eyenakhr.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class BookingDetailsViewModel :BaseViewModel(){

    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mBookingDetailData= MutableLiveData<BookingDetailModel>()

    val mTitle = MutableLiveData<String>("")
    val mMessage = MutableLiveData<String>("")
    val errorValidateRes = MutableLiveData<String>()
    val mBookingReview= MutableLiveData<Any>()

    fun bookingDetail(locale: String?, deviceId: String?, deviceType: String?, versionName: String?,bookingId:String?){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.bookingDetail(
                    locale,deviceId,deviceType,versionName,bookingId
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

    fun onBookingReviewClick(bookingId: String?,rating: String?,title:String,message:String?) {
            if(!DialogUtils.isInternetOn()) {
                isInternetOn.postValue(false)
                return
            }
            isApiCalling.postValue(true)
            scope.launch {
                val result = try {
                    DashboardApiRepo.bookingReviewAsync(
                        AppSession.locale,
                        AppSession.deviceId,
                        AppSession.deviceType,
                        BuildConfig.VERSION_NAME,bookingId!!,rating!!,title,message!!)
                } catch (e: Exception) {
                    errorResult.postValue(e.localizedMessage)
                    isApiCalling.postValue(false)
                }

                withContext(Dispatchers.Main) {
                    isApiCalling.postValue(false)
                    if (result is IOException)
                        errorResult.postValue(result.toString())
                    else
                        mBookingReview.postValue(result.toString())
                }
        }
    }
    private fun checkValidation(): Boolean {
        var isValid = true
        when {
            RegExp.chkEmpty(mTitle.value!!) -> {
                isValid = false
                val msg = MyApplication.getAppContext().getString(R.string.title)
                errorValidateRes.postValue(msg)
            }
            RegExp.chkEmpty(mMessage.value!!) -> {
                isValid = false
                val msg = MyApplication.getAppContext().getString(R.string.message)
                errorValidateRes.postValue(msg)
            }
        }
        return isValid

    }


}