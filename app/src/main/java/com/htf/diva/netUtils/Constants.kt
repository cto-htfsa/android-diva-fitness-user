package com.htf.diva.netUtils

object Constants {

    object Urls{
        var BASE_URL = "https://development.htf.sa/diva/"
        var DEBUG_BASE_URL =/* "http://157.175.84.232/"*/BASE_URL
        const val BANNER_IMAGE_URL="https://development.htf.sa/diva/uploads/banners/"
        const val USER_IMAGE_URL="https://development.htf.sa/diva/uploads/users/"
         val TRAINER_IMAGE_URL="https://development.htf.sa/diva/uploads/trainers/"
        const val MEALTYPE_IMAGE_URL="https://development.htf.sa/diva/uploads/meal_types/"
        const val MEAL_IMAGE_URL="https://development.htf.sa/diva/uploads/meals/"

        const val FITNESS_CENTER_IMAGE_URL="https://development.htf.sa/diva/uploads/fitness_centers/"
        const val SPECIALISING_IN_IMAGE_URL="https://development.htf.sa/diva/uploads/specializations/"
        const val WORKOUT_DAY_IMAGE_URL="https://development.htf.sa/diva/uploads/workouts/"

        val COUNTRY_IMAGE_URL = "$BASE_URL/uploads/country_flags/"
        val PAYROLL_PDF_URL="$BASE_URL/uploads/salarySlip/"

    }

    val KEY_PREF_USER_LANGUAGE="user_lang"
    const val BROADCAST_ACTION ="com.htf.eyenakGuest.utils"

    val COME_FROM="comeFrom"
    val FROM_HOME="fromHome"
    val FROM_CENTER="fromCenter"


    object Auth{
        val KEY_TOKEN = "token"
        val KEY_USER_JSON_DETAILS = "jsonUserDetails"
        val KEY_APP_CONFIG = "jsonAppConfig"
    }


    object Auth_Intent_Actions{
        val BROADCAST_ACTION_BLACKLISTED="user_black_listed"
    }



}