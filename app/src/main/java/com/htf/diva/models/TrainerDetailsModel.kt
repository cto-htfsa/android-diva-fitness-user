package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.htf.diva.models.AppDashBoard.Offers
import java.io.Serializable


class TrainerDetailsModel :Serializable {

    @SerializedName("vat_percentage")
    @Expose
    var vatPercentage: String? = null

    @SerializedName("offers")
    @Expose
    var offers: Offers? = null

    @SerializedName("trainer")
    @Expose
    var trainer: Trainer? = null

    @SerializedName("fitnessCenters")
    @Expose
    var fitnessCenters: FitnessCenters? = null

    @SerializedName("specializations")
    @Expose
    var specializations: ArrayList<Specialization>? = null

    @SerializedName("tenures")
    @Expose
    var tenures: ArrayList<Tenure>? = null

    @SerializedName("packages")
    @Expose
    var packages: ArrayList<Packages>? = null

    @SerializedName("leaves")
    @Expose
    var leaves: List<String>? = null

    @SerializedName("slots")
    @Expose
    var slots: ArrayList<Slot>? = null

   }


    class Trainer :Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("fitness_center_id")
    @Expose
    var fitnessCenterId: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("about_us")
    @Expose
    var aboutUs: String? = null

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

    class FitnessCenters :Serializable{
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
    var textColor: String? = null

    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null

    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null
    }

    class Specialization:Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null
    }

    class Tenure:Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("days")
    @Expose
    var days: Int? = null
    }

    class Packages:Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("tenure_id")
    @Expose
    var tenureId: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("sessions")
    @Expose
    var sessions: Int? = null

    @SerializedName("price")
    @Expose
    var price: String? = null

    @SerializedName("tenure_name")
    @Expose
    var tenureName: String? = null

    @SerializedName("description")
    @Expose
    var description : String? = null

    @SerializedName("days")
    @Expose
    var days: Int? = null
    }

    class Slot :Serializable{

    @SerializedName("weekday_id")
    @Expose
    var weekdayId: Int? = null

    @SerializedName("start_at")
    @Expose
    var startAt: String? = null

    @SerializedName("end_at")
    @Expose
    var endAt: String? = null

      var date:String?=null
    }

