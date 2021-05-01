package com.htf.diva.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.htf.diva.R
import com.htf.diva.base.MyApplication
import com.htf.diva.netUtils.Constants

class BindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("date")
        fun setDate(view: TextView, strDob: String?) {
            view.text = DateUtilss.convertDateFormat(strDob, DateUtilss.serverDateFormat, DateUtilss.targetDateWithWeekFormat)
        }

        @JvmStatic
        @BindingAdapter("dob")
        fun setDob(view: TextView, strDob: String?) {
            view.text = DateUtilss.convertDateFormat(strDob, DateUtilss.serverDateFormat, DateUtilss.targetDateFormat)
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(view: ImageView, poserPath: String?) {
            val url = Constants.Urls.BANNER_IMAGE_URL + poserPath
            view.picassoImg(url, R.drawable.diva_place_holder)
        }

        @JvmStatic
        @BindingAdapter("trainerImage")
        fun setUserImageUrl(view: ImageView, poserPath: String?) {
            val url = Constants.Urls.TRAINER_IMAGE_URL + poserPath
            view.picassoImg(url, R.drawable.user)
        }

        @JvmStatic
        @BindingAdapter("mealTypeImage")
        fun setMealTypeImageUrl(view: ImageView, poserPath: String?) {
            val url = Constants.Urls.MEALTYPE_IMAGE_URL + poserPath
            view.picassoImg(url, R.drawable.user)
        }


        @JvmStatic
        @BindingAdapter("dietPlan")
        fun setDietPlanImageUrl(view: ImageView, poserPath: String?) {
            val url = Constants.Urls.MEAL_IMAGE_URL + poserPath
            view.picassoImg(url, R.drawable.user)
        }

        @JvmStatic
        @BindingAdapter("fitnessCenterImage")
        fun setFitnessImageUrl(view: ImageView, poserPath: String?) {
            val url = Constants.Urls.FITNESS_CENTER_IMAGE_URL + poserPath
            view.picassoImg(url, R.drawable.diva_place_holder)
        }

        @JvmStatic
        @BindingAdapter("specialisingImage")
        fun setSpecialisingImageUrl(view: ImageView, poserPath: String?) {
            val url = Constants.Urls.SPECIALISING_IN_IMAGE_URL + poserPath
            view.picassoImg(url, R.drawable.diva_place_holder)
        }

        @JvmStatic
        @BindingAdapter("userName")
        fun setUserName(view: TextView, userName: String?){
            when (userName) {
                "0.0" -> {
                    view.visibility=View.GONE
                }
                "0" -> {
                    view.visibility=View.GONE
                }
                else -> {
                    view.text=userName
                }
            }
        }


        @JvmStatic
        @BindingAdapter("sessionPrice")
        fun setSessionPrice(view: TextView, sessionPrice: String?){
                    view.text=sessionPrice
        }


       @JvmStatic
        @BindingAdapter("burnsCalories")
        fun burnsCalories(view: TextView, burnsCalories: String?){
                    view.text=burnsCalories+" "+MyApplication.getAppContext().getString(R.string.burns)
        }

        @JvmStatic
        @BindingAdapter("workoutReps")
        fun workoutReps(view: TextView, workoutReps: String?){
                    view.text=MyApplication.getAppContext().getString(R.string.reps)+": "+ workoutReps
        }

        @JvmStatic
        @BindingAdapter("workoutSets")
        fun workoutSets(view: TextView, workoutSets: String?){
            view.text=MyApplication.getAppContext().getString(R.string.set)+": "+ workoutSets
        }


        @JvmStatic
        @BindingAdapter("forSessionMonth")
        fun setSessionMonth(view: TextView, forSessionMonth: String?){
                    view.text=MyApplication.getAppContext().getString(R.string.sar)+" "+forSessionMonth }

        @JvmStatic
        @BindingAdapter("forPackageSessionMonth")
        fun setPackageSessionMonth(view: TextView, forSessionMonth: String?){
                    view.text=MyApplication.getAppContext().getString(R.string.for_package)+" "+forSessionMonth }

        @JvmStatic
        @BindingAdapter("amount")
        fun setAmount(view: TextView, amount: String?) {
            amount?.let {
                val am=MathUtils.convertDoubleToString(it.toDouble())
                val str=MyApplication.getAppContext().getString(R.string.sar_amount).replace("[X]",am)
                view.text=str
            }
        }



        @JvmStatic
        @BindingAdapter("start_date", "end_date")
        fun setLeaveStartEndDate(view: TextView, start_date: String?, end_date: String) {
            val startDate = DateUtilss.convertDateFormat(start_date, DateUtilss.serverDateFormat, DateUtilss.dayMonthFormat)
            val endDate = DateUtilss.convertDateFormat(end_date, DateUtilss.serverDateFormat, DateUtilss.targetDateFormat)
            view.text = "$startDate-$endDate"
        }




       /* fun setStatus(view: TextView, status: String?) {
            when(status!!){
                PENDING->{
                    view.text= MyApplication.getAppContext().getString(R.string.pending)
                    view.setTextColor(ContextCompat.getColor(MyApplication.getAppContext(),R.color.colorTextPending))
                    view.backgroundTintList=ContextCompat.getColorStateList(MyApplication.getAppContext(),R.color.colorPaidBg)
                }
                APPROVED->{
                    view.text=MyApplication.getAppContext().getString(R.string.approved)
                    view.setTextColor(ContextCompat.getColor(MyApplication.getAppContext(),R.color.colorTextApproved))
                    view.backgroundTintList=ContextCompat.getColorStateList(MyApplication.getAppContext(),R.color.colorApprovedBg)
                }
                REJECTED->{
                    view.text=MyApplication.getAppContext().getString(R.string.rejected)
                    view.setTextColor(ContextCompat.getColor(MyApplication.getAppContext(),R.color.colorTextRejected))
                    view.backgroundTintList=ContextCompat.getColorStateList(MyApplication.getAppContext(),R.color.colorRejectBg)
                }
            }
        }
*/






    }
}