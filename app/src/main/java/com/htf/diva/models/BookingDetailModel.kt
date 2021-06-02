package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class BookingDetailModel :Serializable{

    @SerializedName("id")
    @Expose
     val id: String? = null

    @SerializedName("booking_review_id")
    @Expose
     val bookingReviewId: String? = null

    @SerializedName("invoice_id")
    @Expose
     val invoiceId: Int? = null

    @SerializedName("tracking_id")
    @Expose
     val trackingId: String? = null

    @SerializedName("group_booking_id")
    @Expose
     val groupBookingId: Int? = null

    @SerializedName("user_id")
    @Expose
     val userId: Int? = null

    @SerializedName("trainer_id")
    @Expose
     val trainerId: String? = null

    @SerializedName("booking_for")
    @Expose
     val bookingFor: String? = null

    @SerializedName("booking_type")
    @Expose
     val bookingType: String? = null

    @SerializedName("fitness_center_id")
    @Expose
     val fitnessCenterId: Int? = null

    @SerializedName("tenure_id")
    @Expose
     val tenureId: Int? = null

    @SerializedName("days")
    @Expose
     val days: Int? = null

    @SerializedName("join_date")
    @Expose
     val joinDate: String? = null

    @SerializedName("expired_at")
    @Expose
     val expiredAt: String? = null

    @SerializedName("package_id")
    @Expose
     val packageId: Int? = null

    @SerializedName("base_sessions")
    @Expose
     val baseSessions: Int? = null

    @SerializedName("total_sessions")
    @Expose
     val totalSessions: Int? = null

    @SerializedName("number_of_people")
    @Expose
     val numberOfPeople: Int? = null

    @SerializedName("offer_id")
    @Expose
     val offerId: Int? = null

    @SerializedName("offer_type")
    @Expose
     val offerType: String? = null

    @SerializedName("offer_discount_value")
    @Expose
     val offerDiscountValue: String? = null

    @SerializedName("offer_max_discount")
    @Expose
     val offerMaxDiscount: String? = null

    @SerializedName("base_amount")
    @Expose
     val baseAmount: String? = null

    @SerializedName("total_amount")
    @Expose
     val totalAmount: String? = null

    @SerializedName("discount_amount")
    @Expose
     val discountAmount: String? = null

    @SerializedName("amount_after_discount")
    @Expose
     val amountAfterDiscount: String? = null

    @SerializedName("vat_percentage")
    @Expose
     val vatPercentage: String? = null

    @SerializedName("vat_amount")
    @Expose
     val vatAmount: String? = null

    @SerializedName("payable_amount")
    @Expose
     val payableAmount: String? = null

    @SerializedName("payment_status")
    @Expose
     val paymentStatus: String? = null

    @SerializedName("is_auto_renew")
    @Expose
     val isAutoRenew: String? = null

    @SerializedName("booking_status")
    @Expose
     val bookingStatus: String? = null

    @SerializedName("created_at")
    @Expose
     val createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
     val updatedAt: String? = null

    @SerializedName("fitness_center_name")
    @Expose
     val fitnessCenterName: String? = null

    @SerializedName("text_color")
    @Expose
     val textColor: String? = null

    @SerializedName("fitness_center_image")
    @Expose
     val fitnessCenterImage: String? = null

    @SerializedName("location")
    @Expose
     val location: String? = null

    @SerializedName("latitude")
    @Expose
     val latitude: Double? = null

    @SerializedName("longitude")
    @Expose
     val longitude: Double? = null

    @SerializedName("tenure_name")
    @Expose
     val tenureName: String? = null

    @SerializedName("package_name")
    @Expose
     val packageName: String? = null

    @SerializedName("trainer_name")
    @Expose
     val trainerName: String? = null

    @SerializedName("trainer_image")
    @Expose
     val trainerImage: String? = null

    @SerializedName("slots")
    @Expose
     val slots: ArrayList<Slot>? = null

    @SerializedName("reviews")
    @Expose
     val reviews: RatingReview? = null

    @SerializedName("invoice")
    @Expose
     val invoice: Invoice? = null

    @SerializedName("subBookings")
    @Expose
     val subBookings: SubBookings? = null

}

  class RatingReview:Serializable{

      @SerializedName("title")
      @Expose
      val title: String? = null

      @SerializedName("feedback")
      @Expose
      val message: String? = null

      @SerializedName("rating")
      @Expose
      val rating: String? = null

    }

  class SubBookings:Serializable{
    @SerializedName("id")
    @Expose
     val id: Int? = null

    @SerializedName("booking_review_id")
    @Expose
     val bookingReviewId: String? = null

    @SerializedName("invoice_id")
    @Expose
     val invoiceId: Int? = null

    @SerializedName("tracking_id")
    @Expose
     val trackingId: String? = null

    @SerializedName("group_booking_id")
    @Expose
     val groupBookingId: Int? = null

    @SerializedName("user_id")
    @Expose
     val userId: Int? = null

    @SerializedName("trainer_id")
    @Expose
     val trainerId: Int? = null

    @SerializedName("booking_for")
    @Expose
     val bookingFor: String? = null

    @SerializedName("booking_type")
    @Expose
     val bookingType: String? = null

    @SerializedName("fitness_center_id")
    @Expose
     val fitnessCenterId: Int? = null

    @SerializedName("tenure_id")
    @Expose
     val tenureId: Int? = null

    @SerializedName("days")
    @Expose
     val days: Int? = null

    @SerializedName("join_date")
    @Expose
     val joinDate: String? = null

    @SerializedName("expired_at")
    @Expose
     val expiredAt: String? = null

    @SerializedName("package_id")
    @Expose
     val packageId: String? = null

    @SerializedName("base_sessions")
    @Expose
     val baseSessions: Int? = null

    @SerializedName("total_sessions")
    @Expose
     val totalSessions: Int? = null

    @SerializedName("number_of_people")
    @Expose
     val numberOfPeople: Int? = null

    @SerializedName("offer_id")
    @Expose
     val offerId: Int? = null

    @SerializedName("offer_type")
    @Expose
     val offerType: String? = null

    @SerializedName("offer_discount_value")
    @Expose
     val offerDiscountValue: String? = null

    @SerializedName("offer_max_discount")
    @Expose
     val offerMaxDiscount: String? = null

    @SerializedName("base_amount")
    @Expose
     val baseAmount: String? = null

    @SerializedName("total_amount")
    @Expose
     val totalAmount: String? = null

    @SerializedName("discount_amount")
    @Expose
     val discountAmount: String? = null

    @SerializedName("amount_after_discount")
    @Expose
     val amountAfterDiscount: String? = null

    @SerializedName("vat_percentage")
    @Expose
     val vatPercentage: String? = null

    @SerializedName("vat_amount")
    @Expose
     val vatAmount: String? = null

    @SerializedName("payable_amount")
    @Expose
     val payableAmount: String? = null

    @SerializedName("payment_status")
    @Expose
     val paymentStatus: String? = null

    @SerializedName("is_auto_renew")
    @Expose
     val isAutoRenew: String? = null

    @SerializedName("booking_status")
    @Expose
     val bookingStatus: String? = null

    @SerializedName("created_at")
    @Expose
     val createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
     val updatedAt: String? = null

    @SerializedName("fitness_center_name")
    @Expose
     val fitnessCenterName: String? = null

    @SerializedName("text_color")
    @Expose
     val textColor: String? = null

    @SerializedName("fitness_center_image")
    @Expose
     val fitnessCenterImage: String? = null

    @SerializedName("location")
    @Expose
     val location: String? = null

    @SerializedName("latitude")
    @Expose
     val latitude: Double? = null

    @SerializedName("longitude")
    @Expose
     val longitude: Double? = null

    @SerializedName("tenure_name")
    @Expose
     val tenureName: String? = null

    @SerializedName("package_name")
    @Expose
     val packageName: String? = null

    @SerializedName("trainer_name")
    @Expose
     val trainerName: String? = null

    @SerializedName("trainer_image")
    @Expose
     val trainerImage: String? = null

    @SerializedName("slots")
    @Expose
     val slots: List<Slot>? = null

    @SerializedName("reviews")
    @Expose
     val reviews: String? = null

    @SerializedName("invoice")
    @Expose
     val invoice: Invoice__1? = null

  }

 class Invoice__1:Serializable{
    @SerializedName("id")
    @Expose
     val id: Int? = null

    @SerializedName("invoice_no")
    @Expose
     val invoiceNo: String? = null

    @SerializedName("payable_amount")
    @Expose
     val payableAmount: String? = null

    @SerializedName("pdf_file")
    @Expose
     val pdfFile: String? = null

    @SerializedName("status")
    @Expose
     val status: String? = null

 }