package com.diva.utils

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import java.io.IOException
import java.util.*

object GeoCodingLocation {
    private val TAG = "GeoCodingLocation"

    fun getAddressFromLocation(locationAddress: String, context: Context, handler: Handler) {
        val thread = object : Thread() {
            override fun run() {
                val geocoder = Geocoder(context, Locale.getDefault())
                var result: String? = null
                var latitude = 0.0
                var longitude = 0.0
                try {
                    val addressList = geocoder.getFromLocationName(locationAddress, 1)
                    if (addressList != null && addressList.size > 0) {
                        val address = addressList[0]
                        latitude = address.latitude
                        longitude = address.longitude
                        val sb = StringBuilder()
                        sb.append(address.latitude).append("\n")
                        sb.append(address.longitude).append("\n")
                        result = sb.toString()
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Unable to connect to Geocoder", e)
                } finally {
                    val message = Message.obtain()
                    message.target = handler
                    if (result != null) {
                        message.what = 1
                        val bundle = Bundle()
                        result = "Address_model: " + locationAddress +
                                "\n\nLatitude and Longitude :\n" + result
                        bundle.putString("address", result)
                        bundle.putDouble("latitude", latitude)
                        bundle.putDouble("longitude", longitude)
                        message.data = bundle
                    } else {
                        message.what = 1
                        val bundle = Bundle()
                        result = "Address_model: " + locationAddress +
                                "\n Unable to get Latitude and Longitude for this address location."
                        bundle.putString("address", result)
                        message.data = bundle
                    }
                    message.sendToTarget()
                }
            }
        }
        thread.start()
    }

    fun getAddressFromLatLong(context: Context, latitute: Double, longitute: Double): String {
        var returnAddress = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitute, longitute, 1)
        if (addresses != null && addresses.size > 0) {
            val strAddress = addresses[0].getAddressLine(0)
            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode
            val knownName = addresses[0].featureName
            // returnAddress = "$strAddress, $city, $state, $country, $postalCode"
            returnAddress = strAddress

        }

        return returnAddress
    }

    fun getLatitudeAndLongitudeFromAddress(currContext: Context, address: String): ArrayList<Double> {
        val latLong = ArrayList<Double>()
        val geocoder = Geocoder(currContext, Locale.getDefault())
        try {
            if (address != null && address != "") {
                val addressList = geocoder.getFromLocationName(address, 1)
                if (!addressList.isNullOrEmpty()) {
                    val addressLine = addressList[0]
                    val latitude = addressLine.latitude
                    val longitude = addressLine.longitude
                    latLong.add(latitude)
                    latLong.add(longitude)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return latLong
    }

}