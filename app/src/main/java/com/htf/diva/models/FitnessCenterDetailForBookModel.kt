package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.htf.diva.models.AppDashBoard.FitnessCenter
import com.htf.diva.models.AppDashBoard.Offers
import java.io.Serializable


class FitnessCenterDetailForBookModel :Serializable {

    @SerializedName("vat_percentage")
    @Expose
    var vatPercentage: String? = null

    @SerializedName("offers")
    @Expose
    var offers: Offers? = null

    @SerializedName("fitnessCenters")
    @Expose
    var fitnessCenters: List<FitnessCenter>? = null

    @SerializedName("tenures")
    @Expose
    var tenures: List<Tenure>? = null

    @SerializedName("packages")
    @Expose
    var packages: List<Packages>? = null
}