package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.htf.diva.models.AppDashBoard.MyScheduled
import java.io.Serializable


class MyWorkoutPlanModel :Serializable{

    @SerializedName("isDayRest")
    @Expose
    var isDayRest: Int? = null

    @SerializedName("myScheduled")
    @Expose
    var myScheduled: MyScheduled? = null

    @SerializedName("userWorkouts")
    @Expose
    var userWorkouts: List<UserWorkout>? = null

}
class UserWorkout:Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("workout_id")
    @Expose
    var workoutId: Int? = null

    @SerializedName("calories_burn")
    @Expose
    var caloriesBurn: String? = null

    @SerializedName("repetitions")
    @Expose
    var repetitions: Int? = null

    @SerializedName("sets")
    @Expose
    var sets: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("workoutCompleted")
    @Expose
    var workoutCompleted: Int? = null
}