package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class AboutModel :Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("profile_image")
    @Expose
    var profileImage: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("dial_code")
    @Expose
    var dialCode: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("is_returner")
    @Expose
    var isReturner: Int? = null

    @SerializedName("age")
    @Expose
    var age: String? = null

    @SerializedName("height")
    @Expose
    var height: String? = null

    @SerializedName("weight")
    @Expose
    var weight: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null


}