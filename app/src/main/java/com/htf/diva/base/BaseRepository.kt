package com.htf.diva.base


import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.htf.diva.netUtils.Constants.Auth_Intent_Actions.BROADCAST_ACTION_BLACKLISTED
import com.htf.diva.utils.AppPreferences
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException


open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<BaseResponse<T>>): T? {
        val userData= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
       /* if (userData!=null){
            val currTime=System.currentTimeMillis()
            val expireTime=userData.userTokenExpireTime!!
            if (currTime>=expireTime){ //triggered when user login but token expired
                var output: T? = null
                val c=APIClient.authApiClient.userRefreshTokenAsync().await()
                if (c.isSuccessful){
                    val res=c.body()?.data
                    userData.expiresIn=res?.expiresIn
                    userData.token=res?.token
                    AppPreferences.getInstance(MyApplication.getAppContext()).saveInPreference(Constants.Auth.KEY_TOKEN,userData.token!!)
                    AppSession.userToken = userData.token!!
                    val currentTime = System.currentTimeMillis()
                    val expTime=currentTime+((userData.expiresIn?.toInt()!!-10)*1000)
                    userData.userTokenExpireTime=expTime
                    userData.userTokenRefreshTime=currentTime
                    AppPreferences.getInstance(MyApplication.getAppContext()).saveUserDetails(userData)

                    val result = sampleApiCall(call, "")
                    output = when (result) {
                        is Output.Success -> {
                            return result.output.data ?: "Success" as T
                        }

                        is Output.Error ->
                            result.exception as T
                    }
                    return output
                }else
                    return output
            }else{    //triggered when user login with token
                var output: T? = null
                val result = sampleApiCall(call, "")
                output = when (result) {
                    is Output.Success -> {
                        result.output.data ?: "Success" as T
                    }
                    is Output.Error ->
                        result.exception as T
                }
                return output
            }
        }else{
            //triggered when user not login
            val result = sampleApiCall(call, "")
            var output: T? = null
            output = when (result) {
                is Output.Success -> {
                    return result.output.data ?: "Success" as T
                }
                is Output.Error ->
                    result.exception as T
            }
            return output
        }*/
        return null
    }


    suspend fun <T : Any> safeRefreshApiCall(call: suspend () -> Response<BaseResponse<T>>): T? {
        val userData=AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
        /*if (userData!=null){
            val currTime=System.currentTimeMillis()
            val expireTime=userData.userTokenExpireTime!!
            if (currTime>=expireTime){ //triggered when user login but token expired
                var output: T? = null

                val result = sampleApiCall(call, "")
                output = when (result) {
                    is Output.Success -> {
                        result.output.data ?: "Success" as T
                    }
                    is Output.Error ->
                        result.exception as T
                }
                return output

            }else{
                //triggered when user login with token
                val result = sampleApiCall(call, "")
                var output: T? = null
                output = when (result) {
                    is Output.Success -> {
                        return result.output.data ?: "Success" as T
                    }

                    is Output.Error ->
                        result.exception as T
                }

                return output
            }
        }else{
            //triggered when user not login
            val result = sampleApiCall(call, "")
            var output: T? = null
            output = when (result) {
                is Output.Success -> {
                    return result.output.data ?: "Success" as T
                }
                is Output.Error ->
                    result.exception as T
            }
            return output
        }*/
        return safeApiCall(call)
    }


    suspend fun <T : Any> safeApiCallLIveData(call: suspend () -> Response<BaseResponse<T>>): MutableLiveData<T>? {
        val resultLiveData = MutableLiveData<T>()
        val result = sampleApiCall(call, "")

        var output: T? = null
        output = when (result) {
            is Output.Success ->
                result.output.data
            is Output.Error ->
                result.exception.message as T
        }
        resultLiveData.postValue(output)
        return resultLiveData
    }

     suspend fun <T : Any> executeApi(call: suspend () -> Response<T>): Output<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            Output.Success(response.body()!!)
        else {
            val message = errorResponse(response,call)
            Output.Error(IOException(message))
        }
    }

    suspend fun <T : Any> safeApiCallAsync(
        call: suspend () -> Response<T>,
        error: String = "Api calling error"
    ): T? {
        val result = sampleApiCall(call, error)
        var output: T? = null
        when (result) {
            is Output.Success -> output = result.output
            is Output.Error -> Log.e("Error", "The $error and the ${result.exception}")
        }
        return output
    }

    private suspend fun <T : Any> sampleApiCall(
        call: suspend () -> Response<T>,
        error: String? = null
    ): Output<T> {
        val response=call.invoke()
        return if (response?.isSuccessful!!)
            Output.Success(response?.body()!!)
        else {
            val message = errorResponse(response!!,call)
            Output.Error(IOException(message))
        }
    }

    private suspend fun <T : Any> sampleRefreshApiCall(
            call: suspend () -> Response<T>,
            error: String? = null
    ): Output<T> {
        val response=call.invoke()
        return if (response?.isSuccessful!!)
            Output.Success(response?.body()!!)
        else {
            val message = errorResponse(response!!,call)
            Output.Error(IOException(message))
        }
    }


    private fun <T : Any> errorResponse(response: Response<T>,call: suspend () -> Response<T>): String? {
        val code = response.code()
        var message: String? = "null"

        //401-Refresh Token
        //403-Back to Login Screen
        //422-Validation Error
        //200 and 201 success
        //rest Error code show server encounter screen

        when(code){
            401->{
                //Refresh Token

            }
            403->{
                //Logout
                message=""
                MyApplication.getAppContext().sendBroadcast(Intent().apply {
                    action =BROADCAST_ACTION_BLACKLISTED
                })
            }

            422->{
                //Validation Errors
                val error = response.errorBody()?.string()
                val jsonObject = JSONObject(error)
                if (jsonObject.has("errors")){
                    val errors = jsonObject.optJSONObject("errors")
                    return if (errors == null) {
                        errors?.optJSONArray(errors?.names().getString(0))
                            ?.getString(0)
                    } else
                        errors.optJSONArray(errors?.names().optString(0))
                            ?.optString(0)
                }
            }
            else->{

                val error = response.errorBody()?.string()
                val jsonObject = JSONObject(error)
                if (jsonObject.has("errors")){
                    val errors = jsonObject.optJSONObject("errors")
                    return if (errors == null) {
                        errors?.optJSONArray(errors?.names().getString(0))
                            ?.getString(0)
                    } else
                        errors.optJSONArray(errors?.names().optString(0))
                            ?.optString(0)
                }
            }
        }

        return message
    }



}

