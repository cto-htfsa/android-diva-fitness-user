package com.htf.diva.utils

import android.util.Base64
import java.io.UnsupportedEncodingException

object MathUtils {
    fun convertToString(value: Any): Any {
        return if (value == 0 || value == 0.0 || value == 0f)
            ""
        else
            value
    }

    fun roundMathValueFromDouble(value: Double): Double {
        var result = 0.0
        result = Math.round(value * 100).toDouble() / 100
        return result
    }

    fun roundMathValueFromString(value: String): Double {
        var result = 0.0
        result = Math.round(value.toDouble() * 100).toDouble() / 100
        return result
    }

    fun enCodeBase64(text: String): String {
        // Sending side
        var data = ByteArray(0)
        try {
            data = text.toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return Base64.encodeToString(data, Base64.DEFAULT).replace("\n", "").replace("\r", "")
    }


    fun convertDoubleToString(doubleValue:Double):String{

        return String.format("%.0f", doubleValue)

    }
}