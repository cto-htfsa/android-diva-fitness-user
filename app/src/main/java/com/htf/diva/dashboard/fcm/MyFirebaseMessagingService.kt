package com.htf.diva.dashboard.fcm

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.htf.diva.R
import com.htf.diva.auth.ui.SplashActivity
import com.htf.diva.base.MyApplication
import com.htf.diva.models.Notifications
import com.htf.diva.netUtils.Constants
import com.htf.diva.utils.AppPreferences
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private val TAG = "MyFirebaseMsgService"
        val ID_BIG_NOTIFICATION = 234
        val ID_SMALL_NOTIFICATION = 235
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            Log.e(TAG, "Data Payload: " + remoteMessage.data.toString())
            sendPushNotification(remoteMessage.data)
        }
    }

    private fun sendPushNotification(hashMap: Map<String, String>) {
        Log.e(TAG, "Notification JSON $hashMap")
        try {
            val notification = Notifications()

            val enTitle = hashMap["en_title"].toString()
            val title = hashMap["title"].toString()
            val body = hashMap["body"].toString()
            val enBody = hashMap["en_body"].toString()
            val notificationType = hashMap["notify_type"].toString()

            val locale = AppPreferences.getInstance(MyApplication.getAppContext())
                .getFromPreference(Constants.KEY_PREF_USER_LANGUAGE)
            if (locale == "ar") {
                notification.title = title
                notification.description = body
                notification.notifyType = notificationType
            } else {
                notification.title = enTitle
                notification.description = enBody
                notification.notifyType = notificationType
            }
            val intent = Intent(this, SplashActivity::class.java)
            // notification = Gson().fromJson<Notifications>(hashMap["extraData"], Notifications::class.java)
            intent.putExtra("notification_data", notification)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            showSmallNotification(notification.title, notification.title, intent)
        } catch (e: Exception) {
            Log.e(TAG, "Exception:" + e.message)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        val userDetail = AppPreferences.getInstance(baseContext).getUserDetails()
        if (userDetail!=null){
         //   sendFcmToServerServer(token)
        }
    }


    private fun showSmallNotification(title: String?, message: String?, intent: Intent?) {
        val resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val mBuilder = NotificationCompat.Builder(applicationContext)
        val mChannel: NotificationChannel
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val CHANNEL_ID = "channel_1"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            mChannel = NotificationChannel(CHANNEL_ID, getString(R.string.app_name), importance)
            mChannel.description = message
            mChannel.enableLights(true)
            mChannel.lightColor = Color.GREEN
            mChannel.setShowBadge(true)
            mChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager.createNotificationChannel(mChannel)
        }
        val notification: Notification
        notification = mBuilder.setSmallIcon(R.drawable.logo_radius).setTicker(title).setWhen(0)
            .setAutoCancel(true)
            .setColor(resources.getColor(R.color.colorWhite))
            .setContentIntent(resultPendingIntent)
            .setContentTitle(title)
            //.setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentText(message)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.logo_radius)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.logo_radius))
            .setChannelId(CHANNEL_ID)
            .build()
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    fun showBigNotification(title: String, message: String, url: String, intent: Intent) {
        val resultPendingIntent = PendingIntent.getActivity(
            applicationContext,
            ID_BIG_NOTIFICATION,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        bigPictureStyle.setBigContentTitle(title)
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString())
        bigPictureStyle.bigPicture(getBitmapFromURL(url))
        val mBuilder = NotificationCompat.Builder(applicationContext)
        val notification: Notification
        notification = mBuilder.setSmallIcon(R.drawable.logo_radius).setTicker(title).setWhen(0)
            .setAutoCancel(true)
            .setContentIntent(resultPendingIntent)
            .setContentTitle(title)
            .setStyle(bigPictureStyle)
            .setSmallIcon(R.drawable.logo_radius)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.logo_radius))
            .setContentText(message)
            .build()

        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ID_BIG_NOTIFICATION, notification)
    }

    private fun getBitmapFromURL(strURL: String): Bitmap? {
        try {
            val url = URL(strURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

}
