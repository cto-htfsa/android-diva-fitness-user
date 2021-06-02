package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class BookSessionSlotModel :Serializable {

    @SerializedName("booking_id")
    @Expose
    var bookingId: Int? = null

    @SerializedName("trainer_id")
    @Expose
    var trainerId: Int? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("start_at")
    @Expose
    var startAt: String? = null

    @SerializedName("end_at")
    @Expose
    var endAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

}