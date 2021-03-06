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
    suspend fun workoutWeekdaysAsync(locale: String,deviceId: String, deviceType: String, versionName: String): Any? {
        return safeApiCall(
                call ={ retrofitAuthClient.workoutWeekdaysAsync(locale,deviceId,deviceType,versionName).await()}
        )
    }


    suspend fun myDietPlan(locale: String, deviceId: String, deviceType: String, versionName: String, selectedDate: String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.myDietPlanAsync(locale,deviceId,deviceType,versionName,selectedDate).await()}
        )
    }

    suspend fun myWorkOutPlan(locale: String, deviceId: String, deviceType: String, versionName: String, selectedDate: String?): Any? {
        return safeApiCall(
                call ={ retrofitAuthClient.myWorkoutPlanAsync(locale,deviceId,deviceType,versionName,selectedDate.toString()).await()}
        )
    }


    suspend fun personalTrainers(locale: String,deviceId: String,
                                 deviceType: String, versionName: String,fitnessId:String , query:String,page:Int): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.personalTrainerAsync(locale,deviceId,deviceType,versionName,fitnessId,query,page).await()}
        )
    }

    suspend fun reviewRatingList(locale: String,deviceId: String,
                                 deviceType: String, versionName: String,trainer_id:String ,page:Int): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.reviewRatingAsync(locale,deviceId,deviceType,versionName,trainer_id,page).await()}
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

    suspend fun privacyPolicy(locale: String?,deviceId: String?, deviceType: String?,versionName: String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.privacyPolicyAsync(locale,deviceId,deviceType,versionName).await()}
        )
    }


    suspend fun fitnessCenterDetail(locale: String?,deviceId: String?, deviceType: String?,versionName: String?,fitness_center_id :String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.fitnessCenterDetailForBookingAsync(
                locale,deviceId,deviceType,versionName,fitness_center_id).await()}
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



    suspend fun updateWorkoutWeekDay(locale: String?,
                                       deviceId: String?,
                                       deviceType: String?,
                                       versionName: String?,
                                       weekDayId: String?,
                                       workouts: HashMap<String, String?>): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.updateWorkoutAsync(locale, deviceId, deviceType,
                versionName, weekDayId,workouts).await()}
        )
    }

    suspend fun updateConsumeDietPLan(locale: String?,
                                     deviceId: String?,
                                     deviceType: String?,
                                     versionName: String?,
                                     mealTypeId: HashMap<String, String?>): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.updateConsumeDietAsync(locale, deviceId, deviceType,
                versionName,mealTypeId).await()}
        )
    }



    suspend fun checkOutIdGenerate(locale: String?,deviceId: String?, deviceType: String?,versionName: String?,
                                   bookingCenterId:String?,bookingTrainerId:String?,
                                   payable_amount: String?,paymentMode:String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.checkOutIdGenerateAsync(locale,deviceId,deviceType,versionName,bookingCenterId,
                bookingTrainerId,payable_amount,paymentMode).await()})
    }



    suspend fun checkOutIdGenerateForCenter(locale: String?,deviceId: String?, deviceType: String?,versionName: String?,
                                   bookingCenterId:String?,
                                   payable_amount: String?,paymentMode:String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.checkOutIdGenerateForCenterAsync(locale,deviceId,deviceType,versionName,bookingCenterId,
                payable_amount,paymentMode).await()})
    }


    suspend fun verifyPayment(locale: String?,deviceId: String?, deviceType: String?,versionName: String?,
                                   bookingCenterId:String?,bookingTrainerId:String?,
                                checkoutId: String?,paymentMode:String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.verifyPaymentStatusAsync(locale,deviceId,deviceType,versionName,bookingCenterId,
                bookingTrainerId,checkoutId,paymentMode).await()})
    }

    suspend fun verifyCenterPayment(locale: String?,deviceId: String?, deviceType: String?,versionName: String?,
                                   bookingCenterId:String?,
                                checkoutId: String?,paymentMode:String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.verifyPaymentStatusCenterAsync(locale,deviceId,deviceType,versionName,bookingCenterId,
                checkoutId,paymentMode).await()})
    }


    suspend fun paymentHistory(locale: String,deviceId: String,
                                 deviceType: String, versionName: String,page:Int): Any? {
        return safeApiCall(
                call ={ retrofitAuthClient.paymentHistory(locale,deviceId,deviceType,versionName,page).await()}
        )
    }



    suspend fun markDayRest(locale: String?,
                                     deviceId: String?,
                                     deviceType: String?,
                                     versionName: String?,
                                     weekDayId: String?): Any? {
        return safeApiCall(
                call ={ retrofitAuthClient.markDayRestAsync(locale, deviceId, deviceType,
                        versionName, weekDayId).await()})
    }

    suspend fun removeDayRest(locale: String?,
                                     deviceId: String?,
                                     deviceType: String?,
                                     versionName: String?,
                                     weekDayId: String?): Any? {
        return safeApiCall(
                call ={ retrofitAuthClient.removeDayRestAsync(locale, deviceId, deviceType,
                        versionName, weekDayId).await()}
        )
    }

    suspend fun updateCompletedWorkoutAsync(locale: String?,
                                     deviceId: String?,
                                     deviceType: String?,
                                     versionName: String?,
                                    weekDayId: HashMap<String, String?>): Any? {
        return safeApiCall(
                call ={ retrofitAuthClient.updateCompletedWorkoutAsync(locale, deviceId, deviceType,
                        versionName, weekDayId).await()}
        )
    }




    suspend fun updateDietPlan(locale: String?,
                               deviceId: String?,
                               deviceType: String?,
                               versionName: String?,
                               weekDayId: String?,meal_type_id:String?,
                               workouts: HashMap<String, String?>): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.updateDietPlanAsync(locale, deviceId, deviceType,
                versionName, weekDayId,meal_type_id,workouts).await()}
        )
    }

    suspend fun bookingReviewAsync(locale: String,deviceId: String, deviceType: String,
                                         versionName: String,booking_id:String, rating:String,title:String,message:String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.bookingReviewAsync(locale,deviceId,deviceType,versionName,booking_id,
                rating,title,message).await()})
    }



    suspend fun manageSlotsAsync(locale: String?, deviceId: String?, deviceType: String?, versionName: String?, trainerId: String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.manageSlotsAsync(locale, deviceId, deviceType,
                versionName, trainerId).await()}
        )
    }

    suspend fun bookManageSessionSlotAsync(locale: String?, deviceId: String?, deviceType: String?, versionName: String?,
                                            trainerId: String?, bookingId: String?,
                                           bookingDate: String?, startDate: String?, endDate: String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.bookSessionSlotAsync(locale, deviceId, deviceType,
                versionName, trainerId,bookingId,bookingDate,startDate,endDate).await()}
        )
    }



    suspend fun deleteBookedSession(locale: String?,
                               deviceId: String?,
                               deviceType: String?,
                               versionName: String?,
                                slot_id: String?): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.deleteBookedSlotAsync(locale, deviceId, deviceType,
                versionName, slot_id).await()}
        )
    }


}









