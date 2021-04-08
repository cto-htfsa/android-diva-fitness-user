package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BookCenterTrainerModel :Serializable {

    @SerializedName("fitnessCenterBookings")
    @Expose
    var fitnessCenterBookings: BookFitnessCenterModel? = null

    @SerializedName("trainerBookings")
    @Expose
    var trainerBookings: Trainers? = null

}