package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ManageSessionModel :Serializable {

    @SerializedName("trainer")
    @Expose
    var trainer: Trainer? = null

    @SerializedName("leaves")
    @Expose
    var leaves: ArrayList<String>? = null

    @SerializedName("slots")
    @Expose
    var slots: ArrayList<Slot>? = null


}