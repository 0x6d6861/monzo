package co.heri.monzo.Messaging

import android.app.Notification
import android.app.NotificationChannel
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import co.heri.monzo.MainActivity
import co.heri.monzo.R


class FirebaseMessaging: FirebaseMessagingService() {
    private val TAG = "FCM Service"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage != null) {
            remoteMessage.notification?.let { sendMyNotification(it) }
        }

        Log.d(TAG, "From: " + remoteMessage!!.from);
        Log.d(TAG, "Notification Message Body: " + remoteMessage.notification!!.body);

    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        // TODO: do something with this token
        Log.e("newToken",newToken);
        this.sendRegistrationToServer(newToken);

    }

    private fun sendRegistrationToServer(token: String) {
        // TODO: Add custom implementation, as needed.
    }


    private fun sendMyNotification(remoteMessageNotification: RemoteMessage.Notification) {

        var message = remoteMessageNotification.body.toString()
        var title = remoteMessageNotification.title.toString()

        //On click of notification it redirect to this Activity
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val NOTIFICATION_CHANNEL_ID = "monza_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Your Notifications", NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.description = "Description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // to diaplay notification in DND Mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
            channel.canBypassDnd()
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.twotone_notifications_24px)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        notificationManager.notify(NotificationCompat.PRIORITY_DEFAULT, notificationBuilder.build())
    }


}