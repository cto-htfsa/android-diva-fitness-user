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
import okio.IOException

class BookingSummaryViewModel : BaseViewModel() {

    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mUpComingBookingResponse= MutableLiveData<Listing<UpComingBookingModel>>()
    val mCompletedBookingResponse= MutableLiveData<Listing<CompletedBookingModel>>()
    val mBookFitnessCenterData= MutableLiveData<BookFitnessCenterModel>()
    val mBookTrainerFitnessCenterData= MutableLiveData<BookCenterTrainerModel>()
    val mCheckOutIdGenerateData= MutableLiveData<BookingDetailModel>()
    val mVerifyAmount= MutableLiveData<Any>()



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



    fun onBookTrainerWithCenterClick(
        locale: String?,
        deviceId: String?,
        deviceType: String?,
        versionName: String?,
        fitness_center_id: String?,
        trainer_id: String?,
        is_auto_renew: String?,
        vat_percentage: String?,
        offer_id: String?,
        discount_amount: String?,
        amount_after_discount: String?,
        vat_amount: String,
        payable_amount: String?,
        fitness_center_tenure_id: String?,
        fitness_center_join_date: String?,
        fitness_center_package_id: String?,
        fitness_center_number_of_people: String?,
        fitness_center_base_amount: String?,
        fitness_center_total_amount: String?,
        trainer_tenure_id: String?,
        trainer_join_date: String?,
        trainer_booking_type: String?,
        trainer_package_id: String?,
        trainer_base_sessions: String?,
        trainer_number_of_people: String?,
        trainer_base_amount: String?,
        trainer_total_amount: String?,
        slots: HashMap<String, String?>
    ) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
            scope.launch {
                val result = try {
                    DashboardApiRepo.bookTrainerWithCenter( locale, deviceId, deviceType,
                    versionName, fitness_center_id, trainer_id,
                    is_auto_renew, vat_percentage,
                    offer_id, discount_amount,
                    amount_after_discount, vat_amount,
                    payable_amount, fitness_center_tenure_id,
                    fitness_center_join_date, fitness_center_package_id,
                    fitness_center_number_of_people, fitness_center_base_amount,
                    fitness_center_total_amount, trainer_tenure_id, trainer_join_date,
                    trainer_booking_type,
                    trainer_package_id, trainer_base_sessions, trainer_number_of_people,
                    trainer_base_amount, trainer_total_amount,slots)
                } catch (e: Exception) {
                    errorResult.postValue(e.localizedMessage)
                    isApiCalling.postValue(false)
                }
                withContext(Dispatchers.Main) {
                    isApiCalling.postValue(false)
                    if (result is BookCenterTrainerModel)
                        mBookTrainerFitnessCenterData.postValue(result)
                    else
                        errorResult.postValue(result.toString())
                }

            }
    }


    fun onCheckOutGenerate(
        centerBookingId: String?,
        trainerBookingId: String?,
        payableAmount: Double?,
        payment_mode: String?) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.checkOutIdGenerate(AppSession.locale,
                    AppSession.deviceId, AppSession.deviceType,
                    BuildConfig.VERSION_NAME,centerBookingId.toString(),
                    trainerBookingId.toString(),payableAmount.toString(),payment_mode.toString())
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is BookingDetailModel)
                    mCheckOutIdGenerateData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }


  fun onCheckOutIdGenerateForCenter(
        centerBookingId: String?,
        payableAmount: Double?,
        payment_mode: String?) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.checkOutIdGenerateForCenter(AppSession.locale,
                    AppSession.deviceId, AppSession.deviceType,
                    BuildConfig.VERSION_NAME,centerBookingId.toString(),payableAmount.toString(),payment_mode.toString())
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is BookingDetailModel)
                    mCheckOutIdGenerateData.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }



    fun onVerifyPayment(
        centerBookingId: String?,
        trainerBookingId: String?,
        checkOutId: String?,
        payment_mode: String?) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.verifyPayment(AppSession.locale,
                    AppSession.deviceId, AppSession.deviceType,
                    BuildConfig.VERSION_NAME,centerBookingId.toString(),
                    trainerBookingId.toString(),checkOutId.toString(),payment_mode.toString())
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is IOException)
                    errorResult.postValue(result.toString())
                else
                    mVerifyAmount.postValue(result.toString())
            }

        }
    }


    fun onVerifyCenterPayment(
        centerBookingId: String?,
        checkOutId: String?,
        payment_mode: String?) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.verifyCenterPayment(AppSession.locale,
                    AppSession.deviceId, AppSession.deviceType,
                    BuildConfig.VERSION_NAME,centerBookingId.toString(), checkOutId.toString(),payment_mode.toString())
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is IOException)
                    errorResult.postValue(result.toString())
                else
                    mVerifyAmount.postValue(result.toString())
            }

        }
    }


}