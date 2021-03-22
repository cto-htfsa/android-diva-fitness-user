package com.htf.diva.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.CountDownTimer
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.utils.DialogUtils.printLog
import java.util.concurrent.TimeUnit

object AppUtils  {
    val isAppDebug: Boolean = BuildConfig.DEBUG

    fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.currentFocus != null && activity.currentFocus?.windowToken != null) {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            try {
                inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            } catch (ignored: NullPointerException) {
                printLog("null", "null")
            }
        }
    }

    fun showSnackBar(activity: Activity, textView: TextView, message: String) {
        val snackbar = Snackbar.make(textView, message, 1000)
        val view = snackbar.view
        view.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorTextRed))
        textView.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite))
        snackbar.show()
    }


    fun countDownTimer(): LiveData<String> {
        val liveData = MutableLiveData<String>()
        object : CountDownTimer(1 * 30000, 1000) { // adjust the milli seconds here

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val stringFormat = String.format(
                    "%d : %d ",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(
                            millisUntilFinished
                        )
                    )
                )
                /// dialogView.tvTimer.text = "" + stringFormat
                liveData.value = stringFormat
            }

            override fun onFinish() {
                //  dialogView.llResendOtp.visibility= View.VISIBLE
                liveData.value = "-1"
            }
        }.start()
        return liveData
    }

    fun getDistanceBetweenLatLong(fromLat:Double,fromLong:Double,toLat:Double,toLong:Double):Double{
        val latLngA = LatLng(fromLat, fromLong)
        val latLngB = LatLng(toLat, toLong)
        val locationA = Location("office")
        locationA.latitude = latLngA.latitude
        locationA.longitude =latLngA.longitude
        val locationB = Location("my Location")
        locationB.latitude = latLngB.latitude
        locationB.longitude = latLngB.longitude
        println("distance->${locationA.distanceTo(locationB).toDouble()}")
        return locationA.distanceTo(locationB).toDouble()
    }

    fun setText(textView: TextView, text: String?, defaultText: String) {
        if (text != null && !text.isEmpty() && !text.equals("null", ignoreCase = true)) {
            textView.text = text
        } else {
            textView.text = defaultText
        }
    }
    fun roundMathValueFromDouble(value: Double): Double {
        var result = 0.0
        result = Math.round(value * 100).toDouble() / 100
        return result
    }

}