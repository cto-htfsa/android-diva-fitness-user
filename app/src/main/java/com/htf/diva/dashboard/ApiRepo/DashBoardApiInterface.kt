package com.htf.diva.dashboard.ApiRepo

import com.htf.diva.base.BaseResponse
import com.htf.diva.models.*
import kotlinx.coroutines.Deferred

import retrofit2.Response
import retrofit2.http.*

interface DashBoardApiInterface {

    @FormUrlEncoded
    @POST("api/v1/dashboard")
    fun appDashboardAsync(
        @Field("locale") locale: String?,
        @Field("device_id") device_id: String?,
        @Field("device_type") device_type: String?,
        @Field("current_version") current_version: String?): Deferred<Response<BaseResponse<AppDashBoard>>>

    @FormUrlEncoded
    @POST("api/v1/logout")
    fun userLogoutAsync(
        @Field("locale") locale: String,
        @Field("device_id") device_id: String,
        @Field("device_type") device_type: String,
        @Field("current_version") current_version: String
    ): Deferred<Response<BaseResponse<UserData>>>


    @FormUrlEncoded
    @POST("api/v1/get/profile")
    fun userProfileDetail(
        @Field("locale") locale: String,
        @Field("device_id") device_id: String,
        @Field("device_type") device_type: String,
        @Field("current_version") current_version: String
    ): Deferred<Response<BaseResponse<AboutModel>>>


    @FormUrlEncoded
    @POST("api/v1/notifications")
    fun notification(
        @Field("locale") locale: String,
        @Field("device_id") device_id: String,
        @Field("device_type") device_type: String,
        @Field("current_version") current_version: String,
        @Field("order_by") order_by: String
    ): Deferred<Response<BaseResponse<ArrayList<Notifications>>>>

    @FormUrlEncoded
    @POST("api/v1/diet/weekdays")
    fun dietWeekdays(
        @Field("locale") locale: String,
        @Field("device_id") device_id: String,
        @Field("device_type") device_type: String,
        @Field("current_version") current_version: String
    ): Deferred<Response<BaseResponse<ArrayList<DietWeekdayModel>>>>


    @FormUrlEncoded
    @POST("api/v1/my/diet")
    fun myDietPlan(
        @Field("locale") locale: String,
        @Field("device_id") device_id: String,
        @Field("device_type") device_type: String,
        @Field("current_version") current_version: String,
        @Field("date") date: String
    ): Deferred<Response<BaseResponse<MyDietModel>>>


    @FormUrlEncoded
    @POST("api/v1/trainers")
    fun personalTrainer(@Field("locale") locale: String,
        @Field("device_id") device_id: String,
        @Field("device_type") device_type: String,
        @Field("current_version") current_version: String,
        @Field("fitness_center_id") fitness_center_id: String,
        @Field("query") query: String,
        @Field("page") page: Int): Deferred<Response<BaseResponse<Listing<AppDashBoard.TopTrainer>>>>



        @FormUrlEncoded
        @POST("api/v1/submit/feedback")
        fun customerSupportAsync(
            @Field("locale") locale: String,
            @Field("device_id") device_id: String,
            @Field("device_type") device_type: String,
            @Field("current_version") current_version: String,
            @Field("name")name:String,
            @Field("dial_code")dial_code:String,
            @Field("mobile")mobile:String,
            @Field("message")message:String): Deferred<Response<BaseResponse<UserData>>>


    @FormUrlEncoded
    @POST("api/v1/about-us")
    fun appAboutUsAsync(
        @Field("locale") locale: String?,
        @Field("device_id") device_id: String?,
        @Field("device_type") device_type: String?,
        @Field("current_version") current_version: String?): Deferred<Response<BaseResponse<AboutModel>>>


    @FormUrlEncoded
    @POST("api/v1/trainer/detail")
    fun trainerDetailsAsync(
        @Field("locale") locale: String?,
        @Field("device_id") device_id: String?,
        @Field("device_type") device_type: String?,
        @Field("current_version") current_version: String?,
        @Field("trainer_id") trainer_id: String?): Deferred<Response<BaseResponse<TrainerDetailsModel>>>


}

