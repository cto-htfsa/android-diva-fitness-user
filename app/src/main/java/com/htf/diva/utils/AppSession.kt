package com.htf.diva.utils


import android.provider.Settings
import com.htf.diva.base.MyApplication
import com.htf.diva.models.AppDashBoard

import java.net.Socket

object AppSession {

    var isRefreshingToken = false
    var locale = "ar"
    var mSocket: Socket? = null
    var userToken = ""
    var deviceId = Settings.Secure.getString(MyApplication.getAppContext().contentResolver,Settings.Secure.ANDROID_ID)
    var deviceType = "android"
    var currency = "SAR"
    var isLocaleEnglish = false
    var userTokenIsValid: Boolean = true
    var mySelectedTab: Int = 0
    var city: String=""
    var state: String=""
    var country: String=""
    var postalCode: String=""
    var orderID=""
    var tokenExpireTime:Int=0
    var isPendingRequestOpen=false
    var appDashBoard:AppDashBoard?=null

    var checkoutID: String? = ""
    var paymentType: String?=""
    var centerBookingId :String?=""
    var trainerBookingId:String?=""



}