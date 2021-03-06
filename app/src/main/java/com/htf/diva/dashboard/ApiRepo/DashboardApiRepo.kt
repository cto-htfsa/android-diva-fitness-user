package com.htf.eyenakhr.dashboard.ApiRepo


import com.htf.diva.base.BaseRepository
import com.htf.diva.netUtils.APIClient
import com.htf.diva.utils.AppSession


object DashboardApiRepo : BaseRepository() {

    private val retrofitAuthClient= APIClient.dashboardApiClient

    suspend fun appDashBoard(locale: String?,deviceId: String?, deviceType: String?,versionName: String?): Any? {
        return safeApiCall(
                call ={ retrofitAuthClient.appDashboardAsync(
                    locale,deviceId,deviceType,versionName).await()}
        )
    }

    suspend fun userLogoutAsync( locale: String,deviceId: String, deviceType: String, versionName: String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.userLogoutAsync(locale,deviceId,deviceType,versionName).await()}
        )
    }

    suspend fun userProfileAsync( locale: String,deviceId: String, deviceType: String, versionName: String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.userProfileDetail(locale,deviceId,deviceType,versionName).await()}
        )
    }


    suspend fun notificationAsync( locale: String,deviceId: String, deviceType: String, versionName: String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.notification(locale,deviceId,deviceType,versionName,"oldest").await()}
        )
    }
    suspend fun dietWeekdaysAsync(locale: String,deviceId: String, deviceType: String, versionName: String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.dietWeekdays(locale,deviceId,deviceType,versionName).await()}
        )
    }


    suspend fun myDietPlan(locale: String,deviceId: String, deviceType: String, versionName: String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.myDietPlan(locale,deviceId,deviceType,versionName,"2021-02-25").await()}
        )
    }

}









