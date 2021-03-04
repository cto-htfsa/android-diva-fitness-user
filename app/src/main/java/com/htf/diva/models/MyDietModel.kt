package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.htf.diva.models.AppDashBoard.MyScheduled
import java.io.Serializable


class MyDietModel : Serializable {

    @SerializedName("myScheduled")
    @Expose
    var myScheduled: MyScheduled? = null

    @SerializedName("mealTypes")
    @Expose
    var mealTypes: ArrayList<MealType>? = null

}

class MealType :Serializable{
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("userDietPlans")
    @Expose
    var userDietPlans: ArrayList<UserDietPlan>? = null

}

class UserDietPlan :Serializable{

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

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null

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

    @SerializedName("dietConsumed")
    @Expose
    var dietConsumed: Int? = null

}