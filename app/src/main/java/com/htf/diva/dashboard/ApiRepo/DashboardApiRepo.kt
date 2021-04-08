package com.htf.eyenakhr.dashboard.ApiRepo


import com.htf.diva.base.BaseRepository
import com.htf.diva.netUtils.APIClient


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

    suspend fun personalTrainers(locale: String,deviceId: String,
                                 deviceType: String, versionName: String,fitnessId:String , query:String,page:Int): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.personalTrainer(locale,deviceId,deviceType,versionName,fitnessId,query,page).await()}
        )
    }

    suspend fun upComingBooking(locale: String,deviceId: String,
                                 deviceType: String, versionName: String,page:Int): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.upComingBookingAsync(locale,deviceId,deviceType,versionName,page).await()}
        )
    }

    suspend fun completedBooking(locale: String,deviceId: String,
                                 deviceType: String, versionName: String,page:Int): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.completedBookingAsync(locale,deviceId,deviceType,versionName,page).await()}
        )
    }


    suspend fun userCustomerSupportAsync(locale: String,deviceId: String, deviceType: String,
                                          versionName: String,name:String, dial_code:String,mobile:String,message:String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.customerSupportAsync(locale,deviceId,deviceType,versionName,name,
                dial_code,mobile,message).await()})
    }


    suspend fun appAboutUs(locale: String?,deviceId: String?, deviceType: String?,versionName: String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.appAboutUsAsync(
                locale,deviceId,deviceType,versionName).await()}
        )
    }



  suspend fun trainerDetails(locale: String?,deviceId: String?, deviceType: String?,versionName: String?,trainerId :String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.trainerDetailsAsync(locale,deviceId,deviceType,versionName,trainerId).await()}
        )
    }


    suspend fun fitnessCenterDetail(locale: String?,deviceId: String?, deviceType: String?,versionName: String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.fitnessCenterDetailForBookingAsync(
                locale,deviceId,deviceType,versionName).await()}
        )
    }


 suspend fun bookingDetail(locale: String?,deviceId: String?, deviceType: String?,versionName: String?,bookingId:String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.bookingDetailAsync(
                locale,deviceId,deviceType,versionName,bookingId).await()}
        )
    }

    suspend fun bookFitnessCenter(locale: String?,deviceId: String?,
                                  deviceType: String?,versionName: String?,
                                  fitness_center_id:String?,
                                  tenure_id:String?,join_date:String?,
                                  package_id:String?,
                                  number_of_people:String?,
                                  offer_id:String?,base_amount:String?,
                                  total_amount:String?,discount_amount:String?,
                                  amount_after_discount:String?,
                                  vat_percentage:String?,vat_amount:String?,
                                  payable_amount:String?,
                                  is_auto_renew:String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.bookCenterAsync(
                locale,deviceId,deviceType,versionName,fitness_center_id,
                tenure_id,join_date,package_id,
                number_of_people,offer_id,base_amount,total_amount,discount_amount,
                amount_after_discount,vat_percentage,vat_amount,payable_amount,is_auto_renew).await()}
        )
    }



    suspend fun bookTrainer(locale: String?, deviceId: String?, deviceType: String?,
        versionName: String?, trainer_id: String?, tenure_id: String?, join_date: String?,
        booking_type: String?, package_id: String?, base_sessions: String?, number_of_people: String?,
        offer_id: String, base_amount: String?, total_amount: String?, discount_amount: String?,
        amount_after_discount: String?, vat_percentage: String?, vat_amount: String?, payable_amount: String?,
        is_auto_renew: String?, slot: HashMap<String, String?>): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.bookTrainerAsync( locale, deviceId, deviceType, versionName,
                trainer_id, tenure_id, join_date, booking_type, package_id, base_sessions, number_of_people,
                offer_id, base_amount, total_amount, discount_amount, amount_after_discount, vat_percentage,
                vat_amount, payable_amount, is_auto_renew, slot).await()}
        )
    }


    suspend fun bookTrainerWithCenter( locale: String?,
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
                                       slots: HashMap<String, String?>): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.bookCenterTrainerAsync( locale, deviceId, deviceType,
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
                trainer_base_amount, trainer_total_amount,slots).await()}
        )
    }

}









