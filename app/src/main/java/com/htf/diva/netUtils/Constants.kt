package com.htf.diva.netUtils

object Constants {

    object Urls{
        var BASE_URL = "https://development.htf.sa/diva/"
        var DEBUG_BASE_URL =/* "http://157.175.84.232/"*/BASE_URL
        const val BANNER_IMAGE_URL="https://development.htf.sa/diva/uploads/banners/"
        const val USER_IMAGE_URL="https://development.htf.sa/diva/uploads/users/"
        const val TRAINER_IMAGE_URL="https://development.htf.sa/diva/uploads/trainers/"
        const val MEALTYPE_IMAGE_URL="https://development.htf.sa/diva/uploads/meal_types/"

        const val FITNESS_CENTER_IMAGE_URL="https://development.htf.sa/diva/uploads/fitness_centers/"

        val COUNTRY_IMAGE_URL = "$BASE_URL/uploads/country_flags/"
        val PAYROLL_PDF_URL="$BASE_URL/uploads/salarySlip/"
        val HR_PROFILE_IMAGE_URL="https://d1ytdogfi1jqor.cloudfront.net/assets/uploads/hr/employee"
        val HR_DOCUMENT_IMAGE_URL="https://d1ytdogfi1jqor.cloudfront.net/assets/uploads/hr/document"
        val HR_RESUME_IMAGE_URL="https://d1ytdogfi1jqor.cloudfront.net/assets/uploads/hr/resume"
        val HR_BILL_COPY_URL="https://d1ytdogfi1jqor.cloudfront.net/assets/uploads/hr/bill"
    }

    val KEY_PREF_USER_LANGUAGE="user_lang"
    const val BROADCAST_ACTION ="com.htf.eyenakGuest.utils"

    object Auth{
        val KEY_TOKEN = "token"
        val KEY_USER_JSON_DETAILS = "jsonUserDetails"
        val KEY_APP_CONFIG = "jsonAppConfig"
    }


    object Auth_Intent_Actions{
        val BROADCAST_ACTION_BLACKLISTED="user_black_listed"
    }



}