package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class DietWeekdayModel :Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("mealTypes")
    @Expose
    var mealTypes: ArrayList<MealDietType>? = null


}

class MealDietType:Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("dietPlans")
    @Expose
    var dietPlans: ArrayList<DietPlan>? = null


}

class DietPlan :Serializable{
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("meal_id")
    @Expose
    var mealId: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

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

    @SerializedName("userDietPlans")
    @Expose
    var userDietPlans: UserDietPlans? = null

    var quantity:Int?=0

}

class UserDietPlans: Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = 0

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = 0

    @SerializedName("proteins")
    @Expose
    var proteins: String? = "0.0"

    @SerializedName("carbs")
    @Expose
    var carbs: String? = "0.0"

    @SerializedName("fats")
    @Expose
    var fats: String? = "0.0"

    @SerializedName("calories")
    @Expose
    var calories: String? = "0.0"
}