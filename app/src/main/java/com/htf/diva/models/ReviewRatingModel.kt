package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ReviewRatingModel :Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("rating")
    @Expose
    var rating: Int? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("feedback")
    @Expose
    var feedback: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}