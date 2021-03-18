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

    @SerializedName("toBeExpiredSubscription")
    @Expose
    var toBeExpiredSubscription: List<Any>? = null

    @SerializedName("fitnessCenters")
    @Expose
    var fitnessCenters: ArrayList<FitnessCenter>? = null

    @SerializedName("version")
    @Expose
    var version: String? = null

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
        var caloriesBurn: Any? = null
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
        var proteins: Any? = null

        @SerializedName("carbs")
        @Expose
        var carbs: Any? = null

        @SerializedName("fats")
        @Expose
        var fats: Any? = null

        @SerializedName("calories")
        @Expose
        var calories: Any? = null
    }


}

