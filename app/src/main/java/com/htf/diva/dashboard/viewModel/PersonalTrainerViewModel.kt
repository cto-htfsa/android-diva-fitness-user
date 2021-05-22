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

class PersonalTrainerViewModel :BaseViewModel() {

    val isApiCalling = MutableLiveData<Boolean>()
    val errorResult = MutableLiveData<String>()
    val mNotificationData= MutableLiveData<Listing<AppDashBoard.TopTrainer>>()
    val mReviewRatingData= MutableLiveData<Listing<ReviewRatingModel>>()
    val mTrainerDetailResponse= MutableLiveData<TrainerDetailsModel>()
    val mPrivacyPolicyResponse= MutableLiveData<PrivacyPolicyModel>()

    fun onGetTrainerListing(page:Int,isProgressBar:Boolean,fitnessId:String,query:String) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(isProgressBar)
        scope.launch {
            val result = try {
                DashboardApiRepo.personalTrainers(
                    AppSession.locale, AppSession.deviceId,
                    AppSession.deviceType, BuildConfig.VERSION_NAME,fitnessId,query,page)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is Listing<*>)
                    mNotificationData.postValue(result as Listing<AppDashBoard.TopTrainer>)
                else
                    errorResult.postValue(result.toString())
            }

        }

    }

    fun trainerDetails(locale: String?, deviceId: String?, deviceType: String?, versionName: String?,trainerId:String?){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.trainerDetails(locale,deviceId,deviceType,versionName,trainerId)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is TrainerDetailsModel)
                    mTrainerDetailResponse.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }



    fun privacyPolicy(locale: String?, deviceId: String?, deviceType: String?, versionName: String?){
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.privacyPolicy(locale,deviceId,deviceType,versionName)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is PrivacyPolicyModel)
                    mPrivacyPolicyResponse.postValue(result)
                else
                    errorResult.postValue(result.toString())
            }

        }
    }

    fun onGetTrainerReviewRating(page:Int,isProgressBar:Boolean,trainer_id:String) {
        if (!DialogUtils.isInternetOn()){
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(isProgressBar)
        scope.launch {
            val result = try {
                DashboardApiRepo.reviewRatingList(
                    AppSession.locale, AppSession.deviceId,
                    AppSession.deviceType, BuildConfig.VERSION_NAME,trainer_id,page)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is Listing<*>)
                    mReviewRatingData.postValue(result as Listing<ReviewRatingModel>)
                else
                    errorResult.postValue(result.toString())
            }

        }

    }


}