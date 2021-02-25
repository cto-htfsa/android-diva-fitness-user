package com.htf.diva.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.htf.diva.R
import com.htf.diva.models.AppSetting
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_snackbar.view.*
import kotlinx.android.synthetic.main.toast_view.view.*


import java.io.File
import java.io.Serializable
fun <L : LiveData<T>, T : Any> LifecycleOwner. observerViewModel(liveData: L, body: (T) -> Unit) =
    liveData.observe(
        this,
        Observer(body)
    )

fun ImageView.picassoImg(url: String,placeHolder:Int) = Picasso.get()
    .load(url)
    .placeholder(placeHolder)
    .error(placeHolder)
    .error(placeHolder)
    .into(this)
fun ImageView.countryImg(url: String,placeHolder:Int) = Picasso.get()
    .load(url)
    .resize(100, 100)
    .placeholder(placeHolder)
    .error(placeHolder)
    .centerCrop().error(placeHolder)
    .into(this)
fun ImageView.picassoImgProfile(url: File?,placeHolder:Int) = url?.let {
    Picasso.get()
        .load(it)
        .resize(200, 200)
        .placeholder(placeHolder)
        .error(placeHolder)
        .centerCrop().error(placeHolder)
        .into(this)
}

fun ImageView.picassoImgProfile(url: Uri?,placeHolder:Int) = url?.let {
    Picasso.get()
        .load(it)
        .resize(200, 200)
        .placeholder(placeHolder)
        .error(placeHolder)
        .centerCrop().error(placeHolder)
        .into(this)
}

inline fun <reified T> Activity.startActivity() {
    Intent(this, T::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(this)
    }
}


inline fun <reified T> Activity.startAppActivity(key: String, v: String) {
    Intent(this, T::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra(key, v)
        startActivity(this)
    }
}

inline fun <reified T> Activity.startAppActivity(key: String, v: Any) {
    Intent(this, T::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra(key, v as Serializable)
        startActivity(this)
    }
}

fun Activity.showProgress(): Dialog {
    val overlayDialog = Dialog(this, android.R.style.Theme_Panel)
    overlayDialog.setContentView(R.layout.dialog_progress)
    overlayDialog.setCanceledOnTouchOutside(false)

    if (!this.isFinishing) {
        overlayDialog.show()
    }

    return overlayDialog
}

fun TextView.showSnackBar(message: String) {
    val snackBar = Snackbar.make(this, message, 1000)
    val view = snackBar.view
    view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorTextRed))
    //this.setTextColor(ContextCompat.getColor(this.context, R.color.colorWhite))
    snackBar.show()
}

fun Dialog.hideProgress() {
    if (this.isShowing) {
        this.dismiss()
    }
}

fun View.showProgressBar() {
    this.visibility = View.VISIBLE
}

fun View.hideProgressBar() {
    this.visibility = View.GONE
}

fun Activity?.showSnackBar(view: View, string: String){

    val snackbar: Snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
    val layout: Snackbar.SnackbarLayout = snackbar.getView() as Snackbar.SnackbarLayout
    val textView: TextView = layout.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setVisibility(View.INVISIBLE)
    val snackView: View = this?.layoutInflater?.inflate(R.layout.layout_snackbar, null)!!
    snackView.tvMsg.text=string
    layout.setPadding(0, 0, 0, 0)
    layout.addView(snackView, 0)
    snackbar.show()
}

fun Activity?.showToast(message: String, isError: Boolean) {
    if (this != null) {
        var msg=message
        if (msg.contains("java.io.IOException:"))
            msg=msg.replace("java.io.IOException:","")

        if (msg.trim()!=""){
            val toast = Toast(this)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER.toFloat()
            )

            val view= LayoutInflater.from(this).inflate(R.layout.toast_view,null,false)

            view.tvToast.text = msg
            if (isError)
                view.tvToast.background= ContextCompat.getDrawable(this,R.drawable.bg_toast_error)
            else
                view.tvToast.background= ContextCompat.getDrawable(this,R.drawable.bg_toast_success)

            toast.view = view
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }

    }
}

fun Activity?.buildAlertMessageNoGps(){
    val builder = AlertDialog.Builder(this)
    val dialogLangView = this?.layoutInflater?.inflate(R.layout.dialog_no_gps, null)
    builder.setView(dialogLangView)
    builder.setCancelable(true)
    val dialog= builder.show()

/*    dialogLangView?.tvOkay.setOnClickListener {
        this?.startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        dialog.dismiss()
    }*/

    dialog.window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT
    )
    //dialog.window?.setGravity(Gravity.CENTER)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}



fun Activity?.showForceUpdateDialog(appSetting: AppSetting) {
    val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(
        ContextThemeWrapper(this!!, theme)
    )
    alertDialogBuilder.setTitle(this.getString(R.string.youAreNotUpdatedTitle))
    var str=getString(R.string.youAreNotUpdatedMessage)
    val versionCode="${appSetting.appVersion}"
    str=str.replace("[x]",versionCode)
    alertDialogBuilder.setMessage(str)

    alertDialogBuilder.setCancelable(false)
    alertDialogBuilder.setPositiveButton(
        R.string.update, DialogInterface.OnClickListener { dialog, id ->
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.packageName)
                    )
                )
                finish()
            } catch (e: ActivityNotFoundException) {

            }
            dialog.cancel()
        })
    alertDialogBuilder.show()

}


