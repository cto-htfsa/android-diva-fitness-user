package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserData:Serializable {

    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null

    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null

    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int? = null

    @SerializedName("expires_unit")
    @Expose
    var expiresUnit: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null


    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("data")
    @Expose
    var user: User? = null
    var userTokenExpireTime:Long?=null

    var userTokenRefreshTime:Long?=null




}

class  User :Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("profile_image")
    @Expose
    var profileImage: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: Any? = null

    @SerializedName("dial_code")
    @Expose
    var dialCode: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("is_returner")
    @Expose
    var isReturner: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null



}