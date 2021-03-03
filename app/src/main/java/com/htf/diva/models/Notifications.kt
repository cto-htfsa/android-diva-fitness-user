package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Notifications:Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("is_read")
    @Expose
    var isRead: Int? = null

    @SerializedName("read_at")
    @Expose
    var readAt: String? = null

    @SerializedName("notify_type")
    @Expose
    var notifyType: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("attribute")
    @Expose
    var attribute: Any? = null

    @SerializedName("value")
    @Expose
    var value: Any? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

}