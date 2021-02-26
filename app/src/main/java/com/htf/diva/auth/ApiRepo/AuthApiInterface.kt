package com.htf.diva.auth.ApiRepo

import com.htf.diva.base.BaseResponse
import com.htf.diva.models.AppSetting
import com.htf.diva.models.UserData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface AuthApiInterface {

    @FormUrlEncoded
    @POST("api/v1/app/settings")
    fun appSetting(
            @Field("locale") locale:String,
            @Field("device_id")device_id:String,
            @Field("device_type")device_type:String,
            @Field("current_version")current_version:String):Deferred<Response<BaseResponse<AppSetting>>>


    @GET("api/v1/refresh/token")
    fun userRefreshTokenAsync(
    ):Deferred<Response<BaseResponse<UserData>>>


    @FormUrlEncoded
    @POST("api/v1/login")
    fun userLoginAsync(
        @Field("locale") locale:String,
        @Field("device_id")device_id:String,
        @Field("device_type")device_type:String,
        @Field("current_version")current_version:String,
        @Field("dial_code")dial_code:String,
        @Field("mobile")mobile:String):Deferred<Response<BaseResponse<UserData>>>


    @FormUrlEncoded
    @POST("api/v1/verify/otp")
    fun userVerifyOtpAsync(
        @Field("device_id")device_id:String,
        @Field("device_type")device_type:String,
        @Field("locale") locale:String,
        @Field("current_version")current_version:String,
        @Field("id")id:String,
        @Field("hash_token")hash_token:String,
        @Field("otp")otp:String,
        @Field("fcm_id")fcm_id:String
        ):Deferred<Response<BaseResponse<UserData>>>

     @FormUrlEncoded
    @POST("api/v1/resend/otp")
    fun userResendOtpAsync(
        @Field("device_id")device_id:String,
        @Field("device_type")device_type:String,
        @Field("locale") locale:String,
        @Field("current_version")current_version:String,
        @Field("id")id:String,
        @Field("hash_token")hash_token:String
        ):Deferred<Response<BaseResponse<UserData>>>




}

