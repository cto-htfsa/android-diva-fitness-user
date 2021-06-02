package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class AppDashBoard :Serializable {

    @SerializedName("offers")
    @Expose
    var offers: Offers? = null

    @SerializedName("banners")
    @Expose
    var banners: ArrayList<Banner>? = null

    @SerializedName("topTrainers")
    @Expose
    var topTrainers: ArrayList<TopTrainer>? = null

    @SerializedName("isDayRest")
    @Expose
    var isDayRest: Int? = null

    @SerializedName("myScheduled")
    @Expose
    var myScheduled: MyScheduled? = null

    @SerializedName("fitnessCenterSubscription")
    @Expose
    var fitnessCenterSubscription: FitnessCenterSubscription? = null

    @SerializedName("toBeExpiredSubscription")
    @Expose
    var toBeExpiredSubscription: List<Any>? = null

    @SerializedName("fitnessCenters")
    @Expose
    var fitnessCenters: ArrayList<FitnessCenter>? = null

    @SerializedName("version")
    @Expose
    var version: String? = null

    @SerializedName("bookings")
    @Expose

    var bookings:  ArrayList<BookingDetailModel>? = null



    class Offers:Serializable{

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("offer_name")
        @Expose
        var offerName: String? = null

        @SerializedName("offer_type")
        @Expose
        var offerType: String? = null

        @SerializedName("discount_value")
        @Expose
        var discountValue: String? = null

        @SerializedName("max_discount")
        @Expose
        var maxDiscount: String? = null
    }

    class TopTrainer:Serializable{
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("location")
        @Expose
        var location: String? = null

        @SerializedName("per_session_price")
        @Expose
        var perSessionPrice: String? = null

        @SerializedName("rating")
        @Expose
        var rating: String? = null

        @SerializedName("total_reviews")
        @Expose
        var totalReviews: Int? = null
    }

    class MyScheduled:Serializable{
        @SerializedName("workouts")
        @Expose
        var workouts: Workouts? = null

        @SerializedName("workoutCompleted")
        @Expose
        var workoutCompleted: WorkoutCompleted? = null

        @SerializedName("dietPlans")
        @Expose
        var dietPlans: DietPlans? = null

        @SerializedName("dietConsumed")
        @Expose
        var dietConsumed: DietConsumed? = null

    }

    class FitnessCenter:Serializable{
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("location")
        @Expose
        var location: String? = null

        @SerializedName("text_color")
        @Expose
        var text_color: String? = null

        @SerializedName("background_color")
        @Expose
        var background_color : String? = null

        @SerializedName("latitude")
        @Expose
        var latitude: Double? = null

        @SerializedName("longitude")
        @Expose
        var longitude: Double? = null

        var isSelected=false

    }

    class Workouts:Serializable{

        @SerializedName("calories_burn")
        @Expose
        var caloriesBurn: String? = null
    }

    class WorkoutCompleted:Serializable{

        @SerializedName("calories_burn")
        @Expose
        var caloriesBurn: String? = null
    }

    class DietPlans:Serializable{

        @SerializedName("proteins")
        @Expose
        var proteins: String? = null

        @SerializedName("carbs")
        @Expose
        var carbs: String? = null

        @SerializedName("fats")
        @Expose
        var fats: String? = null

        @SerializedName("calories")
        @Expose
        var calories: String? = null
    }

    class DietConsumed:Serializable{
        @SerializedName("proteins")
        @Expose
        var proteins: String? = null

        @SerializedName("carbs")
        @Expose
        var carbs: String? = null

        @SerializedName("fats")
        @Expose
        var fats: String? = null

        @SerializedName("calories")
        @Expose
        var calories: String? = null
    }

    class FitnessCenterSubscription :Serializable{
        @SerializedName("id")
        @Expose
         val id: Int? = null

        @SerializedName("tracking_id")
        @Expose
         val trackingId: String? = null

        @SerializedName("trainer_id")
        @Expose
         val trainerId: Any? = null

        @SerializedName("booking_for")
        @Expose
         val bookingFor: String? = null

        @SerializedName("booking_type")
        @Expose
         val bookingType: String? = null

        @SerializedName("fitness_center_id")
        @Expose
         val fitnessCenterId: Int? = null

        @SerializedName("tenure_id")
        @Expose
         val tenureId: Int? = null

        @SerializedName("days")
        @Expose
         val days: Int? = null

        @SerializedName("join_date")
        @Expose
         val joinDate: String? = null

        @SerializedName("expired_at")
        @Expose
         val expiredAt: String? = null

        @SerializedName("package_id")
        @Expose
         val packageId: Int? = null

        @SerializedName("base_sessions")
        @Expose
         val baseSessions: Int? = null

        @SerializedName("total_sessions")
        @Expose
         val totalSessions: Int? = null

        @SerializedName("number_of_people")
        @Expose
         val numberOfPeople: Int? = null

        @SerializedName("base_amount")
        @Expose
         val baseAmount: String? = null

        @SerializedName("total_amount")
        @Expose
         val totalAmount: String? = null

        @SerializedName("discount_amount")
        @Expose
         val discountAmount: String? = null

        @SerializedName("amount_after_discount")
        @Expose
         val amountAfterDiscount: String? = null

        @SerializedName("vat_percentage")
        @Expose
         val vatPercentage: String? = null

        @SerializedName("vat_amount")
        @Expose
         val vatAmount: String? = null

        @SerializedName("payable_amount")
        @Expose
         val payableAmount: String? = null

        @SerializedName("created_at")
        @Expose
         val createdAt: String? = null
    }

}

