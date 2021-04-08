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
        @Field("current_version") current_version: String?,
        @Field("booking_id") booking_id: String?): Deferred<Response<BaseResponse<BookingDetailModel>>>



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


    /* Booking with Center*/
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

    /* Booking with Trainer */

    @FormUrlEncoded
    @POST("api/v1/book/trainer")
    fun bookTrainerAsync(@Field("locale") locale: String?,
                        @Field("device_id") device_id: String?,
                        @Field("device_type") device_type: String?,
                        @Field("current_version") current_version: String?,
                        @Field("trainer_id") trainer_id: String?,
                        @Field("tenure_id") tenure_id: String?,
                        @Field("join_date") join_date: String?,
                        @Field("booking_type") booking_type: String?,
                        @Field("package_id") package_id: String?,
                        @Field("base_sessions") base_sessions: String? ,
                        @Field("number_of_people") number_of_people: String?,
                        @Field("offer_id") offer_id: String?,
                        @Field("base_amount") base_amount: String?,
                        @Field("total_amount") total_amount: String?,
                        @Field("discount_amount") discount_amount: String?,
                        @Field("amount_after_discount") amount_after_discount: String?,
                        @Field("vat_percentage") vat_percentage: String?,
                        @Field("vat_amount") vat_amount: String?,
                        @Field("payable_amount") payable_amount: String?,
                        @Field("is_auto_renew") is_auto_renew: String?,
                         @FieldMap slotsHashMap: HashMap<String, String?>):
            Deferred<Response<BaseResponse<BookFitnessCenterModel>>>


    @FormUrlEncoded
    @POST("api/v1/book/trainer-fitness-center")
    fun bookCenterTrainerAsync(
                        @Field("locale") locale: String?,
                        @Field("device_id") device_id: String?,
                        @Field("device_type") device_type: String?,
                        @Field("current_version") current_version: String?,
                        @Field("fitness_center_id") fitness_center_id: String?,
                        @Field("trainer_id") trainer_id: String?,
                        @Field("is_auto_renew") is_auto_renew: String?,
                        @Field("vat_percentage") vat_percentage: String?,
                        @Field("offer_id") offer_id: String?,
                        @Field("discount_amount") discount_amount: String?,
                        @Field("amount_after_discount") amount_after_discount: String? ,
                        @Field("vat_amount") vat_amount: String?,
                        @Field("payable_amount") payable_amount: String?,
                        @Field("fitness_center_tenure_id") fitness_center_tenure_id: String?,
                        @Field("fitness_center_join_date") fitness_center_join_date: String?,
                        @Field("fitness_center_package_id") fitness_center_package_id: String?,
                        @Field("fitness_center_number_of_people") fitness_center_number_of_people: String?,
                        @Field("fitness_center_base_amount") fitness_center_base_amount: String?,
                        @Field("fitness_center_total_amount") fitness_center_total_amount: String?,
                        @Field("trainer_tenure_id") trainer_tenure_id: String?,
                        @Field("trainer_join_date") trainer_join_date: String?,
                        @Field("trainer_booking_type") trainer_booking_type: String?,
                        @Field("trainer_package_id") trainer_package_id: String?,
                        @Field("trainer_base_sessions") trainer_base_sessions: String?,
                        @Field("trainer_number_of_people") trainer_number_of_people: String?,
                        @Field("trainer_base_amount") trainer_base_amount: String?,
                        @Field("trainer_total_amount") trainer_total_amount: String?,
                        @FieldMap slotsHashMap: HashMap<String, String?>):
            Deferred<Response<BaseResponse<BookCenterTrainerModel>>>


}

