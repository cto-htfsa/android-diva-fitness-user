package com.htf.diva.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.htf.diva.models.UserData
import com.htf.diva.R
import com.htf.diva.netUtils.Constants.Auth.KEY_USER_JSON_DETAILS


class AppPreferences {

    companion object {

        private var mInstance: AppPreferences? = null
        private var mPreferences: SharedPreferences? = null
        private var mEditor: SharedPreferences.Editor? = null

        fun getInstance(context: Context): AppPreferences {
            if (mInstance == null) {
                mInstance = AppPreferences()
            }
            if (mPreferences == null) {
                mPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                mEditor = mPreferences!!.edit()
            }
            return mInstance as AppPreferences
        }

    }

    fun saveInPreference(key: String, value: String) {
        mEditor!!.putString(key, value)
        mEditor!!.commit()
    }


    fun getFromPreference(key: String): String {
        return mPreferences!!.getString(key, "")!!
    }


    fun clearFromPref(key: String) {
        mEditor!!.putString(key, "")
        mEditor!!.commit()
    }


    // -------------User PREFRENCES Details--------
    fun saveUserDetails(loginApiResponse: UserData) {
        mEditor!!.putString(KEY_USER_JSON_DETAILS, Gson().toJson(loginApiResponse))
        mEditor!!.commit()
    }
    fun getUserDetails(): UserData? {
        val userJson = mPreferences!!.getString(KEY_USER_JSON_DETAILS, "")
        var user: UserData? = null
        if (userJson != null && userJson != "") {
            user = Gson().fromJson(userJson, UserData::class.java)
        }
        return user
    }

    fun clearUserDetails() {
        mEditor!!.putString(KEY_USER_JSON_DETAILS, "")
        mEditor!!.commit()
    }



}