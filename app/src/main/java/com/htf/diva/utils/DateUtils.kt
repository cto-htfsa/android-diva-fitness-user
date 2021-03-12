package com.htf.diva.utils

import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList

object DateUtils {
    val serverDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val serverDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    var serverChatUTCDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val serverTimeFormat = SimpleDateFormat("HH:mm:ss")
    val serverTimeFormatWithoutUTC = SimpleDateFormat("HH:mm:ss")
    val serverCardExpireFormat = SimpleDateFormat("yyyy-MM")
    val serverDefaultDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val serverDefaultDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val serverDefaultTimeFormat = SimpleDateFormat("HH:mm:ss")
    val displayDateTimeFormat = SimpleDateFormat("dd MMM, yyyy hh:mm a")
    val calendarDateFormat = SimpleDateFormat("EEE, dd MMM, yyyy")
    val calendarDateTimeFormat = SimpleDateFormat("EEE, dd MMM, yyyy 'at' hh:mm a")
    val calendarYearFormat = SimpleDateFormat("MMMM, yyyy")
    val dayFormat = SimpleDateFormat("EEEE")
    val targetDateFormat = SimpleDateFormat("dd MMM, yyyy")
    val targetDateWithWeekFormat=SimpleDateFormat("EEEE,dd MMM, yyyy")
    val targetTimeFormat = SimpleDateFormat("hh:mm a")
    val targetDateTimeFormat = SimpleDateFormat("dd MMM, yyyy hh:mm a")
    val cardExpireFormat = SimpleDateFormat("MM/yy")
    val dayMonthFormat=SimpleDateFormat("dd MMM", Locale.US)
    val serverMonthFormat=SimpleDateFormat("yyyy-MM",Locale.US)
    val targetMonthFormat=SimpleDateFormat("MMM,yyyy",Locale.US)
    val weekdaysFormat = SimpleDateFormat("EEEE", Locale.US)

    init {
        serverChatUTCDateTimeFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverDateTimeFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverDateFormat.timeZone = TimeZone.getDefault()
        serverTimeFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverTimeFormatWithoutUTC.timeZone = TimeZone.getDefault()
        serverCardExpireFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverDefaultDateFormat.timeZone = TimeZone.getDefault()
        serverDefaultDateTimeFormat.timeZone = TimeZone.getDefault()
        serverDefaultTimeFormat.timeZone = TimeZone.getDefault()
        displayDateTimeFormat.timeZone = TimeZone.getDefault()
        calendarDateFormat.timeZone = TimeZone.getDefault()
        calendarDateTimeFormat.timeZone = TimeZone.getDefault()
        calendarYearFormat.timeZone = TimeZone.getDefault()
        dayFormat.timeZone = TimeZone.getDefault()
        targetDateFormat.timeZone = TimeZone.getDefault()
        targetTimeFormat.timeZone = TimeZone.getDefault()
        cardExpireFormat.timeZone = TimeZone.getDefault()
        targetDateWithWeekFormat.timeZone= TimeZone.getDefault()
        dayMonthFormat.timeZone= TimeZone.getDefault()
        serverMonthFormat.timeZone= TimeZone.getDefault()
        targetMonthFormat.timeZone= TimeZone.getDefault()
        targetDateTimeFormat.timeZone= TimeZone.getDefault()
        weekdaysFormat.timeZone= TimeZone.getDefault()

    }


    fun convertDateFormat(
            dateTimeString: String?,
            originalFormat: SimpleDateFormat,
            targetFormat: SimpleDateFormat
    ): String {
        if (dateTimeString == null || dateTimeString.equals("null", ignoreCase = true))
            return ""

        var targetDateString = dateTimeString
        try {
            val originalDate = originalFormat.parse(dateTimeString)
            val originalDateString = originalFormat.format(originalDate)
            val targetDate = originalFormat.parse(originalDateString)
            targetDateString = targetFormat.format(targetDate)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return targetDateString!!
        }
    }



    fun  convertDatetimeToTimeStamp(dateString: String):Long{
        var timeStamp=0L
        try {
            val date = serverDateTimeFormat.parse(dateString)
            timeStamp=date.time
            System.out.println(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timeStamp
    }

    fun  convertDatetimeToDate(dateString: String):Date{
        var date:Date?=null
        try {
            date= serverDateTimeFormat.parse(dateString)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date!!
    }


    fun getCurrentTimeInMongoFormat():String{
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        df.timeZone= TimeZone.getTimeZone("UTC")
        return df.format(c)
    }

    fun getCurrentMonthInServerFormat():String{
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM", Locale.US)
        val date = Date()
        println("Month:${dateFormat.format(date)}")
        return dateFormat.format(date)
    }

    fun getCurrentMonthInTargetFormat():String{
        val dateFormat: DateFormat = SimpleDateFormat("MMM,yyyy", Locale.US)
        val date = Date()
        println("Month:${dateFormat.format(date)}")
        return dateFormat.format(date)
    }

    fun getCurrentYear():String{
        val dateFormat: DateFormat = SimpleDateFormat("yyyy", Locale.US)
        val date = Date()
        println("Month:${dateFormat.format(date)}")
        return dateFormat.format(date)
    }

    fun getCurrentWeekDay():String{
        val sdf = SimpleDateFormat("EEEE", Locale.US)
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)
        println("WeekDay->$dayOfTheWeek")
        return dayOfTheWeek
    }

    fun getCurrentDateForDashBoard():String{
        val sdf = SimpleDateFormat("dd MMM,yyyy", Locale.US)
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)
        println("WeekDay->$dayOfTheWeek")
        return dayOfTheWeek
    }

    fun getCurrentDateInServerFormat():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)
        println("CurrentDate->$dayOfTheWeek")
        return dayOfTheWeek
    }

    fun getWeekDay():String{
        val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)
        println("WeekDay->$dayOfTheWeek")
        return dayOfTheWeek
    }


    fun getCurrentDate():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val d=Date()
        val dayOfTheWeek: String = sdf.format(d)
        println("WeekDay->$dayOfTheWeek")
        return dayOfTheWeek
    }

    fun getCurrentDateObj():Date{
        val sdf=SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val d1=sdf.format(Date())
        return sdf.parse(d1)
    }


    fun getCurrentMonth():String{
        val sdf = SimpleDateFormat("MMM", Locale.US)
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)
        println("Month->$dayOfTheWeek")
        return dayOfTheWeek
    }

    fun getYearFromDate(date:String):Int{
        val d= targetDateFormat.parse(date)
        val dateFormat: DateFormat = SimpleDateFormat("yyyy", Locale.US)
        println("Year:${dateFormat.format(d)}")
        return dateFormat.format(d).toInt()
    }

    fun getMonthFromDate(date:String):String{
        val d= serverDateFormat.parse(date)
        val sdf = SimpleDateFormat("MMM", Locale.US)
        val dayOfTheWeek: String = sdf.format(d)
        println("Month->$dayOfTheWeek")
        return dayOfTheWeek
    }



}