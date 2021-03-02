package com.htf.eyenakhr.dashboard.ApiRepo

import com.htf.diva.base.BaseResponse
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.UserData
import kotlinx.coroutines.Deferred

import retrofit2.Response
import retrofit2.http.*

interface DashBoardApiInterface {

    @FormUrlEncoded
    @POST("api/v1/dashboard")
    fun appDashboardAsync(
        @Field("locale") locale:String?,
        @Field("device_id")device_id:String?,
        @Field("device_type")device_type:String?,
        @Field("current_version")current_version:String?
     ):Deferred<Response<BaseResponse<AppDashBoard>>>


    @FormUrlEncoded
    @POST("api/v1/logout")
    fun userLogoutAsync(
        @Field("locale") locale:String,
        @Field("device_id")device_id:String,
        @Field("device_type")device_type:String,
        @Field("current_version")current_version:String
    ):Deferred<Response<BaseResponse<UserData>>>


}

