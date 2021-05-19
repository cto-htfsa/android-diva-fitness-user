package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PrivacyPolicyModel :Serializable {

    @SerializedName("privacy_policy")
    @Expose
    var privacyPolicy: String? = null
}