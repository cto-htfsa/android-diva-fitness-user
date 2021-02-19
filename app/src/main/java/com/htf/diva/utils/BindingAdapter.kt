package com.htf.diva.utils

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
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
            view.text = DateUtils.convertDateFormat(strDob, DateUtils.serverDateFormat, DateUtils.targetDateWithWeekFormat)
        }

        @JvmStatic
        @BindingAdapter("dob")
        fun setDob(view: TextView, strDob: String?) {
            view.text = DateUtils.convertDateFormat(strDob, DateUtils.serverDateFormat, DateUtils.targetDateFormat)
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(view: ImageView, poserPath: String?) {
            val url = Constants.Urls.USER_IMAGE_URL + poserPath
            view.picassoImg(url, R.drawable.profile)
        }

        @JvmStatic
        @BindingAdapter("start_date", "end_date")
        fun setLeaveStartEndDate(view: TextView, start_date: String?, end_date: String) {
            val startDate = DateUtils.convertDateFormat(start_date, DateUtils.serverDateFormat, DateUtils.dayMonthFormat)
            val endDate = DateUtils.convertDateFormat(end_date, DateUtils.serverDateFormat, DateUtils.targetDateFormat)
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