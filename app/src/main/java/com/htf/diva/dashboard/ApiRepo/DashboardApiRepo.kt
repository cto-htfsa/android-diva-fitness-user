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

}









