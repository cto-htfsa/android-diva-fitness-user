package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class WorkoutWeekDaysModel:Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("isDayRest")
    @Expose
    var isDayRest: Int? = null

    @SerializedName("workouts")
    @Expose
    var workouts: List<Workout>? = null
}

class Workout :Serializable{
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("calories_burn")
    @Expose
    var caloriesBurn: String? = null

    @SerializedName("repetitions")
    @Expose
    var repetitions: Int? = null

    @SerializedName("sets")
    @Expose
    var sets: Int? = null

    @SerializedName("userWorkouts")
    @Expose
    var userWorkouts: UserWorkouts? = null
}
class UserWorkouts:Serializable{
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("calories_burn")
    @Expose
    var caloriesBurn: String? = null

    @SerializedName("repetitions")
    @Expose
    var repetitions: Int? = null

    @SerializedName("sets")
    @Expose
    var sets: Int? = null
}