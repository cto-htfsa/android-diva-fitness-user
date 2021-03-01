package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Banner:Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("banner_name")
    @Expose
    var bannerName: String? = null

    @SerializedName("banner_file")
    @Expose
    var bannerFile: String? = null


}