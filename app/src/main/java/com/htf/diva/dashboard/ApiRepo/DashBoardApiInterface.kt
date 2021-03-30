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




    @FormUrlEncoded
    @POST("api/v1/fitness/center/detail")
    fun fitnessCenterDetailForBookingAsync(
        @Field("locale") locale: String?,
        @Field("device_id") device_id: String?,
        @Field("device_type") device_type: String?,
        @Field("current_version") current_version: String?): Deferred<Response<BaseResponse<FitnessCenterDetailForBookModel>>>


    @FormUrlEncoded
    @POST("api/v1/booking/detail")
    fun bookingDetailAsync(
        @Field("locale") locale: String?,
        @Field("device_id") device_id: String?,
        @Field("device_type") device_type: String?,
        @Field("current_version") current_version: String?): Deferred<Response<BaseResponse<BookingDetailModel>>>



    @FormUrlEncoded
    @POST("api/v1/upcoming/bookings")
    fun upComingBookingAsync(@Field("locale") locale: String,
                             @Field("device_id") device_id: String,
                             @Field("device_type") device_type: String,
                             @Field("current_version") current_version: String,
                             @Field("page") page: Int): Deferred<Response<BaseResponse<Listing<UpComingBookingModel>>>>

    @FormUrlEncoded
    @POST("api/v1/completed/bookings")
    fun completedBookingAsync(@Field("locale") locale: String,
                             @Field("device_id") device_id: String,
                             @Field("device_type") device_type: String,
                             @Field("current_version") current_version: String,
                             @Field("page") page: Int): Deferred<Response<BaseResponse<Listing<CompletedBookingModel>>>>


    @FormUrlEncoded
    @POST("api/v1/book/fitness/center")
    fun bookCenterAsync(@Field("locale") locale: String?,
                                 @Field("device_id") device_id: String?,
                                 @Field("device_type") device_type: String?,
                                 @Field("current_version") current_version: String?,
                                 @Field("fitness_center_id") fitness_center_id: String?,
                                 @Field("tenure_id") tenure_id: String?,
                                 @Field("join_date") join_date: String?,
                                 @Field("package_id") package_id: String?,
                                 @Field("number_of_people") number_of_people: String?,
                                 @Field("offer_id") offer_id: String? ,
                                 @Field("base_amount") base_amount: String?,
                                 @Field("total_amount") total_amount: String?,
                                 @Field("discount_amount") discount_amount: String?,
                                 @Field("amount_after_discount") amount_after_discount: String?,
                                 @Field("vat_percentage") vat_percentage: String?,
                                 @Field("vat_amount") vat_amount: String?,
                                 @Field("payable_amount") payable_amount: String?,
                                 @Field("is_auto_renew") is_auto_renew: String?):
            Deferred<Response<BaseResponse<BookFitnessCenterModel>>>

}

