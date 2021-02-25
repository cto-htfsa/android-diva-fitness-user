package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class AppSetting:Serializable {
    @SerializedName("version")
    var appVersion:String?=null
}