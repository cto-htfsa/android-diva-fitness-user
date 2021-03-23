package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class BookFitnessCenterModel :Serializable {

    @SerializedName("tracking_id")
    @Expose
    var trackingId: String? = null

    @SerializedName("user_id")
    @Expose
    var userId: Int? = null

    @SerializedName("booking_for")
    @Expose
    var bookingFor: String? = null

    @SerializedName("fitness_center_id")
    @Expose
    var fitnessCenterId: String? = null

    @SerializedName("tenure_id")
    @Expose
    var tenureId: Int? = null

    @SerializedName("days")
    @Expose
    var days: Int? = null

    @SerializedName("join_date")
    @Expose
    var joinDate: String? = null

    @SerializedName("expired_at")
    @Expose
    var expiredAt: String? = null

    @SerializedName("package_id")
    @Expose
    var packageId: Int? = null

    @SerializedName("base_sessions")
    @Expose
    var baseSessions: Int? = null

    @SerializedName("number_of_people")
    @Expose
    var numberOfPeople: String? = null

    @SerializedName("total_sessions")
    @Expose
    var totalSessions: Int? = null

    @SerializedName("offer_id")
    @Expose
    var offerId: Int? = null

    @SerializedName("offer_type")
    @Expose
    var offerType: String? = null

    @SerializedName("offer_discount_value")
    @Expose
    var offerDiscountValue: Int? = null

    @SerializedName("offer_max_discount")
    @Expose
    var offerMaxDiscount: Int? = null

    @SerializedName("base_amount")
    @Expose
    var baseAmount: String? = null

    @SerializedName("total_amount")
    @Expose
    var totalAmount: String? = null

    @SerializedName("discount_amount")
    @Expose
    var discountAmount: String? = null

    @SerializedName("amount_after_discount")
    @Expose
    var amountAfterDiscount: String? = null

    @SerializedName("vat_percentage")
    @Expose
    var vatPercentage: String? = null

    @SerializedName("vat_amount")
    @Expose
    var vatAmount: String? = null

    @SerializedName("payable_amount")
    @Expose
    var payableAmount: String? = null

    @SerializedName("is_auto_renew")
    @Expose
    var isAutoRenew: String? = null

    @SerializedName("payment_status")
    @Expose
    var paymentStatus: String? = null

    @SerializedName("booking_status")
    @Expose
    var bookingStatus: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("invoice_id")
    @Expose
    var invoiceId: Int? = null
}