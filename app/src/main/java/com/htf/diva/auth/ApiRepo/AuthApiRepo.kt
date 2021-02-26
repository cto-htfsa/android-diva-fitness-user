

import com.htf.diva.BuildConfig
import com.htf.diva.base.BaseRepository
import com.htf.diva.netUtils.APIClient
import com.htf.diva.utils.AppSession


object AuthApiRepo: BaseRepository() {
    private val retrofitAuthClient= APIClient.authApiClient

    suspend fun appSetting(deviceId: String, deviceType: String, locale: String, versionName: String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.appSetting(locale,deviceId,deviceType,versionName).await()}
        )
    }

    suspend fun userLoginAsync(locale: String,deviceId: String, deviceType: String,versionName: String ,
                               dialCode:String,mobile:String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.userLoginAsync(
                locale,deviceId,deviceType,versionName,dialCode,mobile).await()}
        )
    }

    suspend fun userVerifyOtpAsync(deviceId: String, deviceType: String, locale: String,
                                   versionName: String,userId:String,hashToken:String,
                                   otp:String,fcm_id:String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.userVerifyOtpAsync(
                deviceId,deviceType,locale,versionName,userId,hashToken,otp,fcm_id
            ).await()}
        )
    }


    suspend fun userResendOtpAsync(deviceId: String, deviceType: String, locale: String,
                                   versionName: String,userId:String,hashToken:String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.userResendOtpAsync(
                    deviceId,
                    deviceType,
                    locale,
                    versionName,
                    userId,
                    hashToken).await()}
        )
    }



}









