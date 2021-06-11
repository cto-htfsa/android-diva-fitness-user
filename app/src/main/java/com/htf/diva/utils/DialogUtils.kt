package com.htf.diva.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.htf.diva.BuildConfig
import com.htf.diva.R
import com.htf.diva.base.MyApplication
import kotlinx.android.synthetic.main.dialog_no_internet.view.*
import kotlinx.android.synthetic.main.dialog_progress.*
import kotlinx.android.synthetic.main.layout_snackbar.view.*
import kotlinx.android.synthetic.main.toast_view.view.*

object DialogUtils {
    val isAppDebug: Boolean = BuildConfig.DEBUG
    private var dialog: Dialog? = null


    fun printLog(tag: String, message: String) {
        if (isAppDebug)
            Log.d(tag, message)
    }
    fun showProgress(activity: Activity): Dialog {
        val overlayDialog = Dialog(activity, android.R.style.Theme_Panel)
        val dialogView = activity.layoutInflater.inflate(R.layout.dialog_progress, null)
        overlayDialog.setContentView(dialogView)
        overlayDialog.setCanceledOnTouchOutside(false)
     //   Glide.with(activity).asGif().load(R.drawable.hr_loader_2).into(overlayDialog.iv_loader)
        if (!activity.isFinishing) {
            overlayDialog.show()
        }

        return overlayDialog
    }

    fun hideProgress(overlayDialog: Dialog) {
        if (overlayDialog.isShowing) {
            overlayDialog.dismiss()
        }
    }


    fun showSnackBar(activity: Activity, view: View, string: String){

        val snackbar: Snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
        val layout: Snackbar.SnackbarLayout = snackbar.getView() as Snackbar.SnackbarLayout
        val textView: TextView = layout.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setVisibility(View.INVISIBLE)
        val snackView: View = activity.layoutInflater.inflate(R.layout.layout_snackbar, null)
        snackView.tvMsg.text=string
        layout.setPadding(0, 0, 0, 0)
        layout.addView(snackView, 0)
        snackbar.show()
    }

    fun showToast(activity: Activity?, message: String, isError: Boolean) {
        if (activity != null) {
            val toast = Toast(activity)
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER.toFloat()
            )

            val view= LayoutInflater.from(activity).inflate(R.layout.toast_view, null, false)

            view.tvToast.text = message
            if (isError)
                view.tvToast.background= ContextCompat.getDrawable(activity, R.drawable.bg_toast_error)
            else
                view.tvToast.background= ContextCompat.getDrawable(activity, R.drawable.bg_toast_success)

            toast.view = view
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }
    }




    fun isInternetOn(): Boolean {

        var flag = false
        // get Connectivity Manager object to check connection
        val connec = MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connec.getNetworkInfo(0)?.state == android.net.NetworkInfo.State.CONNECTED ||
            connec.getNetworkInfo(1)?.state == android.net.NetworkInfo.State.CONNECTING ||
            connec.getNetworkInfo(0)?.state == android.net.NetworkInfo.State.CONNECTING ||
            connec.getNetworkInfo(1)?.state == android.net.NetworkInfo.State.CONNECTED
        ) {

            flag = true

        } else if (connec.getNetworkInfo(0)?.state == android.net.NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(
                        1
                )?.state == android.net.NetworkInfo.State.DISCONNECTED
        ) {
            flag = false
        }
        return flag
    }

    fun dialogInternet(activity: Activity, fragment: Fragment?, requestCode: Int) {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()

        val ad = AlertDialog.Builder(activity)
        ad.setTitle(activity.getString(R.string.noConnection))
        ad.setMessage(activity.getString(R.string.turnOnInternet))
        //        ad.setCancelable(false);
        ad.setNegativeButton(activity.getString(R.string.mobileData)) { dialog, which ->
            val i = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
            if (fragment == null) {
                activity.startActivityForResult(i, requestCode)
            } else {
                fragment.startActivityForResult(i, requestCode)
            }
        }
        ad.setPositiveButton(activity.getString(R.string.wifi)) { dialog, which ->
            val i = Intent(Settings.ACTION_WIFI_SETTINGS)
            if (fragment == null) {
                activity.startActivityForResult(i, requestCode)
            } else {
                fragment.startActivityForResult(i, requestCode)
            }
        }
        dialog = ad.show()
    }

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



    fun getDetails():String{
        return  Build.MANUFACTURER+
                " " + Build.MODEL +
                " " + VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name+
                "\nDEVICE " + Build.DEVICE+
                "\nID " + Build.ID+
                "\nBRAND " + Build.BRAND+ " " +
                " \nTYPE " + Build.TYPE+
                "\nUSER " + Build.USER+
                " \nVERSION_CODES.BASE  " + Build.VERSION_CODES.BASE+
                "\nVERSION.INCREMENTAL " + Build.VERSION.INCREMENTAL+
                "\nSDK " + Build.VERSION.SDK+
                "\nBOARD " + Build.BOARD+
                "\nBRAND " + Build.BRAND+
                "\nHOST " + Build.HOST+
                "\nFINGERPRINT " + Build.FINGERPRINT+
                "\nVERSION.RELEASE " + Build.VERSION.RELEASE+
                "\nSERIAL " + Build.SERIAL+
                "\nCPU_ABI " + Build.CPU_ABI+
                "\nHARDWARE " + Build.HARDWARE
    }


    fun showNoInternetDialog(activity: Activity, fragment: Fragment?, requestCode: Int) {
        val builder = AlertDialog.Builder(activity)
        val dialogView = activity.layoutInflater.inflate(R.layout.dialog_no_internet, null)
        builder.setView(dialogView)
        builder.setCancelable(true)
        val dialog = builder.show()

        dialogView?.tvWifi?.setOnClickListener {
            val i = Intent(Settings.ACTION_WIFI_SETTINGS)
            if (fragment == null) {
                activity.startActivityForResult(i, requestCode)
            } else {
                fragment.startActivityForResult(i, requestCode)
            }
            dialog.dismiss()
        }

        dialogView?.tvMobileData?.setOnClickListener {
            val i = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
            if (fragment == null) {
                activity.startActivityForResult(i, requestCode)
            } else {
                fragment.startActivityForResult(i, requestCode)
            }
            dialog.dismiss()
        }

        dialogView?.ivClose?.setOnClickListener {
            dialog.dismiss()
        }

        val window = dialog.window
        window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        )
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
        )
        dialog.show()
    }



}