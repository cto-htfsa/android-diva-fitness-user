package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Trainers:Serializable{

    @SerializedName("tracking_id")
    @Expose
    private val trackingId: String? = null

    @SerializedName("user_id")
    @Expose
    private val userId: Int? = null

    @SerializedName("booking_for")
    @Expose
    private val bookingFor: String? = null

    @SerializedName("trainer_id")
    @Expose
    private val trainerId: Int? = null

    @SerializedName("fitness_center_id")
    @Expose
    private val fitnessCenterId: String? = null

    @SerializedName("group_booking_id")
    @Expose
    private val groupBookingId: Int? = null

    @SerializedName("tenure_id")
    @Expose
    private val tenureId: Int? = null

    @SerializedName("days")
    @Expose
    private val days: Int? = null

    @SerializedName("join_date")
    @Expose
    private val joinDate: String? = null

    @SerializedName("expired_at")
    @Expose
    private val expiredAt: String? = null

    @SerializedName("base_sessions")
    @Expose
    private val baseSessions: String? = null

    @SerializedName("number_of_people")
    @Expose
    private val numberOfPeople: String? = null

    @SerializedName("total_sessions")
    @Expose
    private val totalSessions: Int? = null

    @SerializedName("offer_id")
    @Expose
    private val offerId: Int? = null

    @SerializedName("offer_type")
    @Expose
    private val offerType: String? = null

    @SerializedName("offer_discount_value")
    @Expose
    private val offerDiscountValue: Int? = null

    @SerializedName("offer_max_discount")
    @Expose
    private val offerMaxDiscount: Int? = null

    @SerializedName("base_amount")
    @Expose
    private val baseAmount: String? = null

    @SerializedName("total_amount")
    @Expose
    private val totalAmount: String? = null

    @SerializedName("discount_amount")
    @Expose
    private val discountAmount: Double? = null

    @SerializedName("amount_after_discount")
    @Expose
    private val amountAfterDiscount: Double? = null

    @SerializedName("vat_percentage")
    @Expose
    private val vatPercentage: String? = null

    @SerializedName("vat_amount")
    @Expose
    private val vatAmount: Double? = null

    @SerializedName("payable_amount")
    @Expose
    private val payableAmount: Double? = null

    @SerializedName("is_auto_renew")
    @Expose
    private val isAutoRenew: String? = null

    @SerializedName("booking_type")
    @Expose
    private val bookingType: String? = null

    @SerializedName("payment_status")
    @Expose
    private val paymentStatus: String? = null

    @SerializedName("booking_status")
    @Expose
    private val bookingStatus: String? = null

    @SerializedName("updated_at")
    @Expose
    private val updatedAt: String? = null

    @SerializedName("created_at")
    @Expose
    private val createdAt: String? = null

    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("invoice_id")
    @Expose
    private val invoiceId: Int? = null
}