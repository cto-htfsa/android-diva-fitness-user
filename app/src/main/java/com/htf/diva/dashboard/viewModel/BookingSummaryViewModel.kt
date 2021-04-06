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
    val mBookFitnessCenterData= MutableLiveData<BookFitnessCenterModel>()


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


    fun onBookFitnessCenterClick(
        fitnessCenterId: Int?,
        tenureId: Int?,
        joiningDate: String?,
        packageId: Int?,
        numberOfPeoplePerSession: String?,
        baseAmount: Double?,
        totalAmt: Double?,
        vatPercentage: String?,
        afterCalculateTaxAmt: Double?,
        offerId: Int?,
        discountValue: String?,
        discount_amount: Double?,
        totalPayableAmt1: Double?
    ) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
            scope.launch {
                val result = try {
                    DashboardApiRepo.bookFitnessCenter(AppSession.locale,
                        AppSession.deviceId, AppSession.deviceType,
                        BuildConfig.VERSION_NAME,fitnessCenterId.toString(),tenureId.toString(),joiningDate,
                        packageId.toString(),numberOfPeoplePerSession,offerId.toString(),baseAmount.toString(),
                        totalAmt.toString(),discountValue,discount_amount.toString(),
                        vatPercentage.toString(),afterCalculateTaxAmt.toString(),totalPayableAmt1.toString(),
                        "No")
                } catch (e: Exception) {
                    errorResult.postValue(e.localizedMessage)
                    isApiCalling.postValue(false)
                }
                withContext(Dispatchers.Main) {
                    isApiCalling.postValue(false)
                    if (result is BookFitnessCenterModel)
                        mBookFitnessCenterData.postValue(result)
                    else
                        errorResult.postValue(result.toString())
                }

            }
    }

    fun onBookTrainerClick(
        locale: String?, deviceId: String?,
        deviceType: String?, versionName: String?,
        trainer_id: String?,
        tenure_id: String?, join_date: String?,
        booking_type: String?, package_id: String?,
        base_sessions: String?,
        number_of_people: String?, offer_id: String,
        base_amount: String?, total_amount: String?,
        discount_amount: String?, amount_after_discount: String?,
        vat_percentage: String?, vat_amount: String?, payable_amount: String?,
        is_auto_renew: String?,
        slots: HashMap<String, String?>
    ) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
            scope.launch {
                val result = try {
                    DashboardApiRepo.bookTrainer(locale,deviceId,
                        deviceType,versionName,trainer_id,tenure_id,
                        join_date,booking_type,package_id,base_sessions,
                        number_of_people,offer_id,base_amount,total_amount,
                        discount_amount,amount_after_discount,vat_percentage,
                        vat_amount,payable_amount,is_auto_renew,slots)
                } catch (e: Exception) {
                    errorResult.postValue(e.localizedMessage)
                    isApiCalling.postValue(false)
                }
                withContext(Dispatchers.Main) {
                    isApiCalling.postValue(false)
                    if (result is BookFitnessCenterModel)
                        mBookFitnessCenterData.postValue(result)
                    else
                        errorResult.postValue(result.toString())
                }

            }
    }



}