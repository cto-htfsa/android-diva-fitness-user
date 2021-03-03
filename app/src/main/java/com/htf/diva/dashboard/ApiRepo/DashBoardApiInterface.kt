package com.htf.eyenakhr.dashboard.ApiRepo

import com.htf.diva.base.BaseResponse
import com.htf.diva.models.AboutModel
import com.htf.diva.models.AppDashBoard
import com.htf.diva.models.Notifications
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


  @FormUrlEncoded
    @POST("api/v1/get/profile")
    fun userProfileDetail(
        @Field("locale") locale:String,
        @Field("device_id")device_id:String,
        @Field("device_type")device_type:String,
        @Field("current_version")current_version:String
    ):Deferred<Response<BaseResponse<AboutModel>>>


    @FormUrlEncoded
    @POST("api/v1/notifications")
    fun notification(
        @Field("locale") locale:String,
        @Field("device_id")device_id:String,
        @Field("device_type")device_type:String,
        @Field("current_version")current_version:String,
        @Field("order_by")order_by:String):Deferred<Response<BaseResponse<ArrayList<Notifications>>>>



}

