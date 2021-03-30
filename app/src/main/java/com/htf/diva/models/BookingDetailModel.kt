package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class BookingDetailModel :Serializable{

    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("booking_review_id")
    @Expose
    private val bookingReviewId: Any? = null

    @SerializedName("invoice_id")
    @Expose
    private val invoiceId: Int? = null

    @SerializedName("tracking_id")
    @Expose
    private val trackingId: String? = null

    @SerializedName("group_booking_id")
    @Expose
    private val groupBookingId: Int? = null

    @SerializedName("user_id")
    @Expose
    private val userId: Int? = null

    @SerializedName("trainer_id")
    @Expose
    private val trainerId: Any? = null

    @SerializedName("booking_for")
    @Expose
    private val bookingFor: String? = null

    @SerializedName("booking_type")
    @Expose
    private val bookingType: String? = null

    @SerializedName("fitness_center_id")
    @Expose
    private val fitnessCenterId: Int? = null

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

    @SerializedName("package_id")
    @Expose
    private val packageId: Int? = null

    @SerializedName("base_sessions")
    @Expose
    private val baseSessions: Int? = null

    @SerializedName("total_sessions")
    @Expose
    private val totalSessions: Int? = null

    @SerializedName("number_of_people")
    @Expose
    private val numberOfPeople: Int? = null

    @SerializedName("offer_id")
    @Expose
    private val offerId: Int? = null

    @SerializedName("offer_type")
    @Expose
    private val offerType: String? = null

    @SerializedName("offer_discount_value")
    @Expose
    private val offerDiscountValue: String? = null

    @SerializedName("offer_max_discount")
    @Expose
    private val offerMaxDiscount: String? = null

    @SerializedName("base_amount")
    @Expose
    private val baseAmount: String? = null

    @SerializedName("total_amount")
    @Expose
    private val totalAmount: String? = null

    @SerializedName("discount_amount")
    @Expose
    private val discountAmount: String? = null

    @SerializedName("amount_after_discount")
    @Expose
    private val amountAfterDiscount: String? = null

    @SerializedName("vat_percentage")
    @Expose
    private val vatPercentage: String? = null

    @SerializedName("vat_amount")
    @Expose
    private val vatAmount: String? = null

    @SerializedName("payable_amount")
    @Expose
    private val payableAmount: String? = null

    @SerializedName("payment_status")
    @Expose
    private val paymentStatus: String? = null

    @SerializedName("is_auto_renew")
    @Expose
    private val isAutoRenew: String? = null

    @SerializedName("booking_status")
    @Expose
    private val bookingStatus: String? = null

    @SerializedName("created_at")
    @Expose
    private val createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private val updatedAt: String? = null

    @SerializedName("fitness_center_name")
    @Expose
    private val fitnessCenterName: String? = null

    @SerializedName("text_color")
    @Expose
    private val textColor: String? = null

    @SerializedName("fitness_center_image")
    @Expose
    private val fitnessCenterImage: String? = null

    @SerializedName("location")
    @Expose
    private val location: String? = null

    @SerializedName("latitude")
    @Expose
    private val latitude: Double? = null

    @SerializedName("longitude")
    @Expose
    private val longitude: Double? = null

    @SerializedName("tenure_name")
    @Expose
    private val tenureName: String? = null

    @SerializedName("package_name")
    @Expose
    private val packageName: String? = null

    @SerializedName("trainer_name")
    @Expose
    private val trainerName: Any? = null

    @SerializedName("trainer_image")
    @Expose
    private val trainerImage: Any? = null

    @SerializedName("slots")
    @Expose
    private val slots: List<Any>? = null

    @SerializedName("reviews")
    @Expose
    private val reviews: Any? = null

    @SerializedName("invoice")
    @Expose
    private val invoice: Invoice? = null

    @SerializedName("subBookings")
    @Expose
    private val subBookings: SubBookings? = null

}

class SubBookings:Serializable{
    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("booking_review_id")
    @Expose
    private val bookingReviewId: Any? = null

    @SerializedName("invoice_id")
    @Expose
    private val invoiceId: Int? = null

    @SerializedName("tracking_id")
    @Expose
    private val trackingId: String? = null

    @SerializedName("group_booking_id")
    @Expose
    private val groupBookingId: Int? = null

    @SerializedName("user_id")
    @Expose
    private val userId: Int? = null

    @SerializedName("trainer_id")
    @Expose
    private val trainerId: Int? = null

    @SerializedName("booking_for")
    @Expose
    private val bookingFor: String? = null

    @SerializedName("booking_type")
    @Expose
    private val bookingType: String? = null

    @SerializedName("fitness_center_id")
    @Expose
    private val fitnessCenterId: Int? = null

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

    @SerializedName("package_id")
    @Expose
    private val packageId: Any? = null

    @SerializedName("base_sessions")
    @Expose
    private val baseSessions: Int? = null

    @SerializedName("total_sessions")
    @Expose
    private val totalSessions: Int? = null

    @SerializedName("number_of_people")
    @Expose
    private val numberOfPeople: Int? = null

    @SerializedName("offer_id")
    @Expose
    private val offerId: Int? = null

    @SerializedName("offer_type")
    @Expose
    private val offerType: String? = null

    @SerializedName("offer_discount_value")
    @Expose
    private val offerDiscountValue: String? = null

    @SerializedName("offer_max_discount")
    @Expose
    private val offerMaxDiscount: String? = null

    @SerializedName("base_amount")
    @Expose
    private val baseAmount: String? = null

    @SerializedName("total_amount")
    @Expose
    private val totalAmount: String? = null

    @SerializedName("discount_amount")
    @Expose
    private val discountAmount: String? = null

    @SerializedName("amount_after_discount")
    @Expose
    private val amountAfterDiscount: String? = null

    @SerializedName("vat_percentage")
    @Expose
    private val vatPercentage: String? = null

    @SerializedName("vat_amount")
    @Expose
    private val vatAmount: String? = null

    @SerializedName("payable_amount")
    @Expose
    private val payableAmount: String? = null

    @SerializedName("payment_status")
    @Expose
    private val paymentStatus: String? = null

    @SerializedName("is_auto_renew")
    @Expose
    private val isAutoRenew: String? = null

    @SerializedName("booking_status")
    @Expose
    private val bookingStatus: String? = null

    @SerializedName("created_at")
    @Expose
    private val createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private val updatedAt: String? = null

    @SerializedName("fitness_center_name")
    @Expose
    private val fitnessCenterName: String? = null

    @SerializedName("text_color")
    @Expose
    private val textColor: String? = null

    @SerializedName("fitness_center_image")
    @Expose
    private val fitnessCenterImage: String? = null

    @SerializedName("location")
    @Expose
    private val location: String? = null

    @SerializedName("latitude")
    @Expose
    private val latitude: Double? = null

    @SerializedName("longitude")
    @Expose
    private val longitude: Double? = null

    @SerializedName("tenure_name")
    @Expose
    private val tenureName: String? = null

    @SerializedName("package_name")
    @Expose
    private val packageName: Any? = null

    @SerializedName("trainer_name")
    @Expose
    private val trainerName: String? = null

    @SerializedName("trainer_image")
    @Expose
    private val trainerImage: String? = null

    @SerializedName("slots")
    @Expose
    private val slots: List<Slot>? = null

    @SerializedName("reviews")
    @Expose
    private val reviews: Any? = null

    @SerializedName("invoice")
    @Expose
    private val invoice: Invoice__1? = null

  }

 class Invoice__1:Serializable{
    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("invoice_no")
    @Expose
    private val invoiceNo: Any? = null

    @SerializedName("payable_amount")
    @Expose
    private val payableAmount: String? = null

    @SerializedName("pdf_file")
    @Expose
    private val pdfFile: Any? = null

    @SerializedName("status")
    @Expose
    private val status: String? = null

 }