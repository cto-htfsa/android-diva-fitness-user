package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PaymentHistoryModel :Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("booking_id")
    @Expose
    var bookingId: String? = null

    @SerializedName("amount")
    @Expose
    var amount: String? = null

    @SerializedName("currency")
    @Expose
    var currency: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("payment_mode")
    @Expose
    var paymentMode: String? = null

    @SerializedName("checkout_id")
    @Expose
    var checkoutId: String? = null

    @SerializedName("transaction_id")
    @Expose
    var transactionId: Any? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null


    @SerializedName("bookings")
    @Expose
    var bookings: List<BookingDetailModel>? = null

}