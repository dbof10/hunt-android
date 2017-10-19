package com.ctech.eaty.messaging

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.ctech.eaty.R
import com.ctech.eaty.linking.UniversalLinkActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class AppFirebaseMessagingService : FirebaseMessagingService() {


    private val KEY_URL = "url"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        if (remoteMessage?.notification != null) {
            sendNotification(remoteMessage)
        }

    }

    private fun sendNotification(message: RemoteMessage) {
        val intent = UniversalLinkActivity.newIntent(this, message.data[KEY_URL] ?: "")
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(message.notification.title)
                .setContentText(message.notification.body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())
    }

}