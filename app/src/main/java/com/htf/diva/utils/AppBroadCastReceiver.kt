package com.htf.diva.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.htf.diva.base.MyApplication
import com.htf.diva.netUtils.Constants
import com.htf.diva.netUtils.Constants.Auth_Intent_Actions.BROADCAST_ACTION_BLACKLISTED

class AppBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("Broadcast received")

        when(intent?.action){
            BROADCAST_ACTION_BLACKLISTED->{
                AppSession.userToken=""
                AppPreferences.getInstance(context!!).clearUserDetails()
                AppPreferences.getInstance(MyApplication.getAppContext()).clearFromPref(Constants.Auth.KEY_TOKEN)
                /*Intent(context, EnterCompanyNameActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(this)
                }*/
            }

        }
        if (intent?.getStringExtra("TOKEN_EXPIRED") == "expired") {
            println("Token has been expired")
            //refreshToken(context)

        } else {

        }
    }

    /*private fun refreshToken(context: Context?) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = try {
                EyenakGuestRepo.refreshToken()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                AppPreferences.getInstance(context!!)
                    .saveInPreference(Constants.KEY_TOKEN, result.toString())
            }
        }

    }*/
}